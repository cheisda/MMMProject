package miage.istic.com.asianmarketfinder.database.sto_tag;

/**
 * Created by Rom on 12/1/2016.
 */
public class Sto_tag {
    private long id;
    private long sto_id;
    private long tag_id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSto_id() {
        return sto_id;
    }

    public void setSto_id(long sto_id) {
        this.sto_id = sto_id;
    }

    public long getTag_id() {
        return tag_id;
    }

    public void setTag_id(long tag_id) {
        this.tag_id = tag_id;
    }

    public Sto_tag(long id, long sto_id, long tag_id) {
        this.id = id;
        this.sto_id = sto_id;
        this.tag_id = tag_id;
    }
}
