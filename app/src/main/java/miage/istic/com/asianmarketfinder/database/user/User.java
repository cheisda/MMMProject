package miage.istic.com.asianmarketfinder.database.user;

/**
 * Created by Rom on 11/21/2016.
 */
public class User {
    private long id;
    private String username;
    private String password;
    private String name;
    private String firstname;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public User(long id, String username, String password, String name, String firstname) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.firstname = firstname;
    }
}
