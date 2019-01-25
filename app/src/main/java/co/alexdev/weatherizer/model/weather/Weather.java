package co.alexdev.weatherizer.model.weather;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public final class Weather {

    @PrimaryKey(autoGenerate = true)
    private int room_id;
    private int city_id;
    private int id;
    private String main;
    private String description;
    private String icon;
    private String date_txt;

    public Weather(int room_id, int city_id, int id, String main, String description, String icon, String date_txt) {
        this.room_id = room_id;
        this.city_id = city_id;
        this.id = id;
        this.main = main;
        this.description = description;
        this.icon = icon;
        this.date_txt = date_txt;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDate_txt() {
        return date_txt;
    }

    public void setDate_txt(String date_txt) {
        this.date_txt = date_txt;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "room_id=" + room_id +
                ", city_id=" + city_id +
                ", id=" + id +
                ", main='" + main + '\'' +
                ", description='" + description + '\'' +
                ", icon='" + icon + '\'' +
                ", date_txt='" + date_txt + '\'' +
                '}';
    }
}
