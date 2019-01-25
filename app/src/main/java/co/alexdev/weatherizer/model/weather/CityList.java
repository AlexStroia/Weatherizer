package co.alexdev.weatherizer.model.weather;


import java.util.List;

public final class CityList {

    private String dt_txt;
    private Main main;
    private List<Weather> weather;
    private Wind wind;

    public String getDt_txt() {
        return dt_txt;
    }

    public void setDt_txt(String dt_txt) {
        this.dt_txt = dt_txt;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
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
                "main=" + main +
                ", weather=" + weather +
                ", wind=" + wind +
                '}';
    }
}
