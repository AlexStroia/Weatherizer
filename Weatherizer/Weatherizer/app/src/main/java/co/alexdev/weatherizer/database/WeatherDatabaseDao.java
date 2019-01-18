package co.alexdev.weatherizer.database;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import co.alexdev.weatherizer.model.weather.City;

@Dao
public interface WeatherDatabaseDao {

    @Query("SELECT * from City")
    LiveData<List<City>> getAllCities();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(City...cities);
}
