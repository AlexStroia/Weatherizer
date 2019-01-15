package co.alexdev.weatherizer.model.response;

import co.alexdev.weatherizer.model.weather.Main;
import co.alexdev.weatherizer.model.weather.Wind;

public class CityList {

    private String dt;
    private Main main;
    private Wind wind;

    public CityList(String dt, Main main, Wind wind) {
        this.dt = dt;
        this.main = main;
        this.wind = wind;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    @Override
    public String toString() {
        return "CityList{" +
                "dt='" + dt + '\'' +
                ", main=" + main +
                ", wind=" + wind +
                '}';
    }
}
