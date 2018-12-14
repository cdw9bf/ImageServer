package imageserver.database;

import org.postgresql.jdbc3.Jdbc3PoolingDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ImageDAO {
    private Jdbc3PoolingDataSource source;

    ImageDAO() {
        source = new Jdbc3PoolingDataSource();
        source.setDataSourceName("A Data Source");
        source.setServerName("localhost");
        source.setDatabaseName("test");
        source.setUser("testuser");
        source.setPassword("testpassword");
        source.setMaxConnections(10);
    }

    ImageDAO(Jdbc3PoolingDataSource source) {
        this.source = source;
    }

    /**
     *
     * @param id
     */
    public void fetchImageMetadata(Integer id) {
        Connection con = null;
        try {
            con = source.getConnection();
            // use connection
        } catch(SQLException e) {
            // log error
        } finally {
            if(con != null) {
                try {con.close();}catch(SQLException e) {}
            }
        }
    }



}
