package co.alexdev.weatherizer.model.weather;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public final class Coordonates {

    @PrimaryKey
    private int city_id;
    private double lat;
    private double lon;

    public Coordonates(int city_id, double lat, double lon) {
        this.city_id = city_id;
        this.lat = lat;
        this.lon = lon;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    @Override
    public String toString() {
        return "Coordonates{" +
                "city_id=" + city_id +
                ", lat=" + lat +
                ", lon=" + lon +
                '}';
    }
}
