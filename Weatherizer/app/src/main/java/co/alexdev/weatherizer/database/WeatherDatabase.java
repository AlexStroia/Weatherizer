package co.alexdev.weatherizer.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import co.alexdev.weatherizer.model.weather.City;

@Database(entities = {City.class}, version = 1, exportSchema = false)
public abstract class WeatherDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "CITY_DATABASE";

    public abstract WeatherDatabaseDao getWeatherDatabaseDao();
}
