package co.alexdev.weatherizer.model.weather;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public final class Main {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int city_id;
    private String name;
    private String temp;
    private double temp_min;
    private double temp_max;
    private String pressure;
    private String sea_level;
    private int humidity;
    private double temp_kf;
    private String date_txt;
    private String icon_id;

    public Main(int id, int city_id, String name, String temp, double temp_min, double temp_max, String pressure, String sea_level, int humidity, double temp_kf, String date_txt, String icon_id) {
        this.id = id;
        this.city_id = city_id;
        this.name = name;
        this.temp = temp;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
        this.pressure = pressure;
        this.sea_level = sea_level;
        this.humidity = humidity;
        this.temp_kf = temp_kf;
        this.date_txt = date_txt;
        this.icon_id = icon_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public double getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(double temp_min) {
        this.temp_min = temp_min;
    }

    public double getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(double temp_max) {
        this.temp_max = temp_max;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getSea_level() {
        return sea_level;
    }

    public void setSea_level(String sea_level) {
        this.sea_level = sea_level;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getTemp_kf() {
        return temp_kf;
    }

    public void setTemp_kf(double temp_kf) {
        this.temp_kf = temp_kf;
    }

    public String getDate_txt() {
        return date_txt;
    }

    public void setDate_txt(String date_txt) {
        this.date_txt = date_txt;
    }

    public String getIcon_id() {
        return icon_id;
    }

    public void setIcon_id(String icon_id) {
        this.icon_id = icon_id;
    }

    @Override
    public String toString() {
        return "Main{" +
                "id=" + id +
                ", city_id=" + city_id +
                ", name='" + name + '\'' +
                ", temp='" + temp + '\'' +
                ", temp_min=" + temp_min +
                ", temp_max=" + temp_max +
                ", pressure='" + pressure + '\'' +
                ", sea_level='" + sea_level + '\'' +
                ", humidity=" + humidity +
                ", temp_kf=" + temp_kf +
                ", date_txt='" + date_txt + '\'' +
                ", icon_id='" + icon_id + '\'' +
                '}';
    }
}
