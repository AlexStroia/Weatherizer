package co.alexdev.weatherizer.repo;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import co.alexdev.weatherizer.database.WeatherDatabaseDao;
import co.alexdev.weatherizer.model.AppExecutor;
import co.alexdev.weatherizer.api.CityResponse;
import co.alexdev.weatherizer.api.ApiResponse;
import co.alexdev.weatherizer.model.weather.City;
import co.alexdev.weatherizer.model.weather.CityList;
import co.alexdev.weatherizer.model.weather.Coordonates;
import co.alexdev.weatherizer.model.weather.History;
import co.alexdev.weatherizer.model.weather.Main;
import co.alexdev.weatherizer.model.weather.Weather;
import co.alexdev.weatherizer.network.resource.NetworkBoundsResource;
import co.alexdev.weatherizer.network.resource.Resource;
import co.alexdev.weatherizer.network.service.OpenWeatherService;
import co.alexdev.weatherizer.scope.WeatherizerAppScope;
import co.alexdev.weatherizer.utils.Constants;
import co.alexdev.weatherizer.utils.WeatherUtils;
import co.alexdev.weatherizer.utils.RateLimiter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

@WeatherizerAppScope
public class AppRepository {

    private final AppExecutor mExecutor;
    private final OpenWeatherService mService;
    private final WeatherDatabaseDao mDao;
    private final WeatherUtils mWeatherUtils;

    private RateLimiter<String> repoCityRateLimiter = new RateLimiter<>(10, TimeUnit.MINUTES);

    @Inject
    public AppRepository(AppExecutor mExecutor, OpenWeatherService mService, WeatherDatabaseDao mDao, WeatherUtils weatherUtils) {
        this.mExecutor = mExecutor;
        this.mService = mService;
        this.mDao = mDao;
        this.mWeatherUtils = weatherUtils;
    }

    /*We pass a param because the algorithms first makes a request to get data for a specific city and after is retrieving all the cities*/
    public LiveData<Resource<List<City>>> loadAllCities(String cityName) {
        return new NetworkBoundsResource<List<City>, CityResponse>(mExecutor) {

            @Override
            protected void saveCallResult(@NonNull CityResponse item) {
                List<CityList> cityList = item.getCityCityList();
                City city = mWeatherUtils.formatData(cityList, item.getCity());
                Coordonates coordonates = mWeatherUtils.formatCoordonates(city, city.getCoord());
                List<Main> cityMain = mWeatherUtils.formatMain(city, cityList);
                List<Weather> weatherList = mWeatherUtils.formatWeather(cityList, city);
                History history = new History(city.getId(), city.getName());

                mDao.insert(history);
                mDao.insert(coordonates);
                mDao.insert(city);
                mDao.insert(cityMain);
                mDao.insertWeather(weatherList);

                Timber.d("Inserting.. ");
            }

            @Override
            protected boolean shouldFetch(@NonNull List<City> data) {
                return data == null || data.isEmpty() || repoCityRateLimiter.shouldFetch(cityName);
            }

            @NonNull
            @Override
            protected LiveData<List<City>> loadFromDatabase() {
                return mDao.getAllCities();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<CityResponse>> createCall() {
                return mService.cityData(cityName);
            }

            @Override
            protected void onFetchFailed() {
                super.onFetchFailed();
                repoCityRateLimiter.reset(cityName);
            }
        }.asLiveData();
    }

    public LiveData<City> getCity(String locality) {
        return mDao.getCity(locality);
    }

    public LiveData<Coordonates> getCoordinate(int id) {
        return mDao.getCoordinate(id);
    }

    public LiveData<List<Main>> getFiveDaysWeather(int id) {
        return mDao.getFiveDaysWeather(id);
    }

    public LiveData<List<History>> getHistoryList() {
        return mDao.loadHistory();
    }
}
