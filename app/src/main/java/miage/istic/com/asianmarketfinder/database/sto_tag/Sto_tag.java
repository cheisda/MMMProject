package miage.istic.com.asianmarketfinder.database.sto_tag;

/**
 * Created by Rom on 12/1/2016.
 */
public class Sto_tag {
    private String id;
    private String sto_id;
    private String tag_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSto_id() {
        return sto_id;
    }

    public void setSto_id(String sto_id) {
        this.sto_id = sto_id;
    }

    public String getTag_id() {
        return tag_id;
    }

    public void setTag_id(String tag_id) {
        this.tag_id = tag_id;
    }

    public Sto_tag(String id, String sto_id, String tag_id) {
        this.id = id;
        this.sto_id = sto_id;
        this.tag_id = tag_id;
    }

    public Sto_tag() {
    }
}
