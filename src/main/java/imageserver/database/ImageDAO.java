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

    public void fetchImageMetadata(Integer id) {
        try ( Connection con = source.getConnection()){
            // use connection
        } catch(SQLException e) {
            // log error
            log.warn(e.toString());
        }
    }

    public void insertImageMetadata(String fullImagePath, String thumbNailPath, String checksum, Date uploadDate, UUID uuid) throws InsertFailedException {
        String catalogQuery = "INSERT INTO catalog VALUES ('" + uploadDate.toString() + "', '" + uuid.toString() +"', '" + "name"+ "');";
        String fullImageQuery = "INSERT INTO fullSizeMetadata VALUES ('" + fullImagePath + "', '" + checksum + "', '" + uuid.toString() + "', '" + "name" + "', '" + "jpg" + "');";
        this.executeUpdate(catalogQuery);
        this.executeUpdate(fullImageQuery);
    }

    public void removeImageMetadata(UUID uuid) {

        String catalogQuery = "DELETE FROM catalog WHERE uuid=" + uuid.toString() + ";";
        String fullSizeQuery = "DELETE FROM fullSizeMetadata WHERE uuid=" + uuid.toString() + ";";
        this.executeUpdate(catalogQuery);
        this.executeUpdate(fullSizeQuery);
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
