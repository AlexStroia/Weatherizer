package co.alexdev.weatherizer.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import co.alexdev.weatherizer.model.weather.City;
import co.alexdev.weatherizer.model.weather.Coordonates;
import co.alexdev.weatherizer.model.weather.History;
import co.alexdev.weatherizer.model.weather.Main;
import co.alexdev.weatherizer.model.weather.Weather;

@Database(entities = {City.class, Coordonates.class, Main.class, Weather.class, History.class}, version = 10, exportSchema = false)
public abstract class WeatherDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "CITY_DATABASE";

    public abstract WeatherDatabaseDao getWeatherDatabaseDao();
}
