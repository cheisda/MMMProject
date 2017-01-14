package miage.istic.com.asianmarketfinder.database.store;

/**
 * Created by Rom on 11/21/2016.
 */
public class Store {
    private String id;
    private String name;
    private double lon;
    private double lat;
    private String opening;


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

    public double getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public double getLat() {
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

    public Store(String id, String name, double lon, double lat, String opening) {
        this.id = id;
        this.name = name;
        this.lon = lon;
        this.lat = lat;
        this.opening = opening;
    }

    public Store(String name, double lon, double lat, String opening) {
        this.name = name;
        this.lon = lon;
        this.lat = lat;
        this.opening = opening;
    }

    public Store() {
    }
}
