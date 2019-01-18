package co.alexdev.weatherizer.model.weather;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class City {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String date;
    private String name;
    private String temp_min;
    private String temp_max;

    @Ignore
    private Coordonates coord;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(String temp_min) {
        this.temp_min = temp_min;
    }

    public String getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(String temp_max) {
        this.temp_max = temp_max;
    }

    public Coordonates getCoord() {
        return coord;
    }

    public void setCoord(Coordonates coord) {
        this.coord = coord;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", temp_min='" + temp_min + '\'' +
                ", temp_max='" + temp_max + '\'' +
                ", coord=" + coord +
                '}';
    }
}
