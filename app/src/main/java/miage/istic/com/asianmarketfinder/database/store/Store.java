package miage.istic.com.asianmarketfinder.database.store;

/**
 * Created by Rom on 11/21/2016.
 */
public class Store {
    private long id;
    private String name;
    private float lon;
    private float lat;
    private String opening;

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

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public String getOpening() {
        return opening;
    }

    public void setOpening(String opening) {
        this.opening = opening;
    }

    public Store(long id, String name, float lon, float lat, String opening) {
        this.id = id;
        this.name = name;
        this.lon = lon;
        this.lat = lat;
        this.opening = opening;
    }
}
