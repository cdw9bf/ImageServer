package imageserver.database;

import imageserver.exceptions.InsertFailedException;
import org.apache.log4j.Logger;
import org.postgresql.jdbc3.Jdbc3PoolingDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.UUID;

public class ImageDAO {
    private static Logger log = Logger.getLogger(ImageDAO.class);

    private static Jdbc3PoolingDataSource source;

    public ImageDAO() {
        // Only Initialize first time the class is created
        if (source == null) {
            log.info("Creating new Source");
            source = new Jdbc3PoolingDataSource();
            source.setDataSourceName("A Data Source");
            source.setServerName("imagedb");
            source.setDatabaseName("mydb");
            source.setUser("postgres");
            source.setPassword("password");
            source.setMaxConnections(10);
        }
    }

    ImageDAO(Jdbc3PoolingDataSource pool) {
        source = pool;
    }

    public String fetchImageByUuid(String uuid, String catalog) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        String query = "SELECT * FROM " + catalog + " WHERE uuid='" + uuid + "';";
        String path = null;
        try (Connection con = source.getConnection()){
            // use connection
            log.info("Executing query " + query);
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(query);
            rs.first();
            path = rs.getString("path");
        } catch(SQLException e) {
            // log error
            log.warn(e.toString());
            throw e;
        } finally {
            try { if (stmt != null) stmt.close(); } catch (Exception e) {log.warn(e.toString());};
        }
        return path;
    }

    public void insertImageMetadata(String fullImagePath, String thumbNailPath, String checksum, Date uploadDate, UUID uuid, String name, String type) throws InsertFailedException {
        String catalogQuery = "INSERT INTO catalog VALUES ('" + uploadDate.toString() + "', '" + uuid.toString() +"', '" + name + "');";
        String fullImageQuery = "INSERT INTO fullSizeMetadata VALUES ('" + fullImagePath + "', '" + checksum + "', '" + uuid.toString() + "', '" + name + "', '" + type + "');";
        String thumbnailImageQuery = "INSERT INTO thumbnailMetadata VALUES ('" + thumbNailPath + "', '" + checksum + "', '" + uuid.toString() + "', '" + name + "', '" + type + "');";

        log.debug("Catalog Insert Query: " + catalogQuery);
        log.debug("Full Image Insert Query: " + fullImageQuery);
        log.debug("Thumbnail Image Insert Query: " + thumbnailImageQuery);

        this.executeUpdate(catalogQuery);
        this.executeUpdate(fullImageQuery);
        this.executeUpdate(thumbnailImageQuery);
    }

    public void removeImageMetadata(UUID uuid) {
        String catalogDeleteQuery = "DELETE FROM catalog WHERE uuid=" + uuid.toString() + ";";
        String fullSizeDeleteQuery = "DELETE FROM fullSizeMetadata WHERE uuid=" + uuid.toString() + ";";
        String thumbnailDeleteQuery = "DELETE FROM thumbnailMetadata WHERE uuid=" + uuid.toString() + ";";

        log.debug("Catalog Delete Query: " + catalogDeleteQuery);
        log.debug("Full Image Delete Query: " + fullSizeDeleteQuery);
        log.debug("Thumbnail Image Delete Query: " + thumbnailDeleteQuery);

        this.executeUpdate(catalogDeleteQuery);
        this.executeUpdate(fullSizeDeleteQuery);
        this.executeUpdate(thumbnailDeleteQuery);
    }

    public int executeUpdate(String query) {
        Statement stmt = null;
        // Assume failed state for initialization
        int rs = 1;
        try (Connection con = source.getConnection()){
            // use connection
            log.info("Executing query " + query);
            stmt = con.createStatement();
            rs = stmt.executeUpdate(query);
        } catch(SQLException e) {
            // log error
            // TODO: handle Error better
            log.warn(e.toString());
        } finally {
            try { if (stmt != null) stmt.close(); } catch (Exception e) {log.warn(e.toString());};
        }
        return rs;
    }

    public Jdbc3PoolingDataSource getSource() {
        return source;
    }

}
