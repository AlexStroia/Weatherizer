package co.alexdev.weatherizer.database;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import co.alexdev.weatherizer.model.weather.City;
import co.alexdev.weatherizer.model.weather.Coordonates;
import co.alexdev.weatherizer.model.weather.History;
import co.alexdev.weatherizer.model.weather.Main;
import co.alexdev.weatherizer.model.weather.Weather;

@Dao
public interface WeatherDatabaseDao {

    @Query("SELECT * FROM City")
    LiveData<List<City>> getAllCities();

    @Query("SELECT * FROM City where name = :locality")
    City getCityFromDB(String locality);

    @Query("SELECT * FROM City where name = :locality")
    LiveData<City> getCity(String locality);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(City... cities);

    /*Coordonates */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Coordonates coordonates);

    @Nullable
    @Query("SELECT * FROM Coordonates where city_id = :id")
    LiveData<Coordonates> getCoordinate(int id);

    /*City main */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Main> cityMainList);

    @Query("SELECT * FROM MAIN where city_id = :id")
    LiveData<List<Main>> getFiveDaysWeather(int id);


    /*Weather*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertWeather(List<Weather> weatherList);

    /*History*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(History history);

    @Query("SELECT * FROM History")
    LiveData<List<History>> loadHistory();
}
