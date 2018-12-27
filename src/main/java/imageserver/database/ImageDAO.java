package imageserver.database;

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

    public void insertImageMetadata(String fullImagePath, String thumbNailPath, String checksum, Date uploadDate, UUID uuid) {
        Statement stmt = null;
        ResultSet rs = null;
        try ( Connection con = source.getConnection()){

            // TODO: Dynamically Insert Table Name from Params
            String query = "INSERT INTO imagemetadata VALUES (" + fullImagePath + ", " + thumbNailPath + ", " + checksum + ", " + uploadDate.toString() + ", " + uuid.toString() + ");";
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
        } catch(SQLException e) {
            // log error
            log.warn(e.toString());
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {log.warn(e.toString());};
            try { if (stmt != null) stmt.close(); } catch (Exception e) {log.warn(e.toString());};
        }
    }

    public void removeImageMetadata(UUID uuid) {
        Statement stmt = null;
        ResultSet rs = null;
        try ( Connection con = source.getConnection()){

            // TODO: Dynamically Insert Table Name from Params
            String query = "DELETE FROM imagemetadata WHERE uuid=" + uuid.toString() + ";";
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
        } catch(SQLException e) {
            // log error
            log.warn(e.toString());
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {log.warn(e.toString());};
            try { if (stmt != null) stmt.close(); } catch (Exception e) {log.warn(e.toString());};
        }
    }

    public void executeQuery(String query) {
        Statement stmt = null;
        ResultSet rs = null;
        try (Connection con = source.getConnection()){
            // use connection
            log.info("Executing query " + query);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                log.info(rs.toString());
            }
            rs.close();
        } catch(SQLException e) {
            // log error
            log.warn(e.toString());
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {log.warn(e.toString());};
            try { if (stmt != null) stmt.close(); } catch (Exception e) {log.warn(e.toString());};
        }
    }

    public Integer getImageCounter() {
        Statement stmt = null;
        ResultSet rs = null;
        String query = "UPDATE counter SET id = id + 1 RETURNING id;";
        try (Connection con = source.getConnection()){
            // use connection
            log.info("Executing query " + query);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                log.info(rs.toString());
            }
            rs.close();
        } catch(SQLException e) {
            // log error
            log.warn(e.toString());
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {log.warn(e.toString());};
            try { if (stmt != null) stmt.close(); } catch (Exception e) {log.warn(e.toString());};
        }

        return 0;
    }

    public Jdbc3PoolingDataSource getSource() {
        return source;
    }

}
