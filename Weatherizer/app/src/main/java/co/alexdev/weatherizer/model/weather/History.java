package co.alexdev.weatherizer.model.weather;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public final class History {

    @PrimaryKey
    private int id;

    private String city;

    public History(int id, String city) {
        this.id = id;
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "History{" +
                "id=" + id +
                ", city='" + city + '\'' +
                '}';
    }
}
