package miage.istic.com.asianmarketfinder.database.tag;

/**
 * Created by Rom on 11/21/2016.
 */
public class Tag {
    private long id;
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Tag(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
