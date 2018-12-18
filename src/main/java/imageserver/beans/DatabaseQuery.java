package imageserver.beans;


import java.io.Serializable;

public class DatabaseQuery implements Serializable {
    private String query;


    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }


    @Override
    public String toString() {
        return "DatabaseQuery{" +
                "query='" + query + '\'' +
                '}';
    }
}
