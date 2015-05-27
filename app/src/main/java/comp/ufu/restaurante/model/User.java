package comp.ufu.restaurante.model;

/**
 * Created by bocato on 5/26/15.
 */
public class User {
    private String name;
    private String table;

    public User(String username, String table) {
        this.name = username;
        this.table = table;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }
}
