package imageserver.database;

import org.apache.log4j.Logger;
import org.postgresql.jdbc3.Jdbc3PoolingDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ImageDAO {
    private static Logger log = Logger.getLogger(ImageDAO.class);

    private static Jdbc3PoolingDataSource source;

    ImageDAO() {
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

    /**
     *
     * @param id
     */
    public void fetchImageMetadata(Integer id) {
        try ( Connection con = source.getConnection()){
            // use connection
        } catch(SQLException e) {
            // log error
        }
    }

    public void insertImageMetadata(Integer id, String path) {
        try ( Connection con = source.getConnection()){
            // use connection
        } catch(SQLException e) {
            // log error
        }
    }

    public Jdbc3PoolingDataSource getSource() {
        return source;
    }

    public void executeQuery(String query) {
        try ( Connection con = source.getConnection()){
            // use connection
            log.info("Executing query " + query);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                log.info(rs.toString());
            }
        } catch(SQLException e) {
            // log error
            log.warn(e.toString());
        }
    }

}
