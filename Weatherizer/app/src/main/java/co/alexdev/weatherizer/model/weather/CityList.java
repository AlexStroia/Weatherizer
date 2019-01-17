package co.alexdev.weatherizer.model.weather;

public class CityList {

    private Main main;
    private Weather weather;
    private Wind wind;

    public CityList(Main main, Weather weather, Wind wind) {
        this.main = main;
        this.weather = weather;
        this.wind = wind;
    }

    public Main getMain() {
        return main;
    }

    public Weather getWeather() {
        return weather;
    }

    public Wind getWind() {
        return wind;
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
