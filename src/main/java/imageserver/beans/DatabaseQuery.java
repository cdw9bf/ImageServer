package imageserver.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DatabaseQuery implements Serializable {
    @SerializedName("query")
    @Expose
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
