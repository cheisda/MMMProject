package miage.istic.com.asianmarketfinder.database.favoris;

/**
 * Created by Rom on 12/1/2016.
 */
public class Favoris {
    private long id;
    private long usr_id;
    private long sto_id;

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

    public long getUsr_id() {
        return usr_id;
    }

    public void setUsr_id(long usr_id) {
        this.usr_id = usr_id;
    }

    public Favoris(long id, long usr_id, long sto_id) {
        this.id = id;
        this.usr_id = usr_id;
        this.sto_id = sto_id;
    }
}
