package co.alexdev.weatherizer.model.weather;

public class Coordonates {

    private String lat;
    private String lon;

    public Coordonates(String lat, String lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    @Override
    public String toString() {
        return "Coordonates{" +
                "lat='" + lat + '\'' +
                ", lon='" + lon + '\'' +
                '}';
    }
}
