package miage.istic.com.asianmarketfinder.database.tag;

/**
 * Created by Rom on 11/21/2016.
 */
public class Tag {
    private String id;
    private String name;
    private String creator;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Tag(long id, String name) {
        this.id = Long.toString(id);
        this.name = name;
    }

    public Tag(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Tag(String id, String name, String creator) {
        this.id = id;
        this.name = name;
        this.creator = creator;
    }

    public Tag(String name) {
        this.name = name;
    }

    public Tag() {
    }
}
