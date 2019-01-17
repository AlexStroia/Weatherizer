package co.alexdev.weatherizer.repo;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import co.alexdev.weatherizer.database.WeatherDatabaseDao;
import co.alexdev.weatherizer.model.AppExecutor;
import co.alexdev.weatherizer.api.CityResponse;
import co.alexdev.weatherizer.api.ApiResponse;
import co.alexdev.weatherizer.model.weather.City;
import co.alexdev.weatherizer.network.resource.NetworkBoundsResource;
import co.alexdev.weatherizer.network.resource.Resource;
import co.alexdev.weatherizer.network.service.OpenWeatherService;
import co.alexdev.weatherizer.scope.WeatherizerAppScope;
import co.alexdev.weatherizer.utils.AppUtils;
import co.alexdev.weatherizer.utils.RateLimiter;

@WeatherizerAppScope
public class Repository {

    final AppExecutor mExecutor;
    final OpenWeatherService mService;
    final WeatherDatabaseDao mDao;

    private RateLimiter<String> repoCityRateLimiter = new RateLimiter<>(10, TimeUnit.MINUTES);

    @Inject
    public Repository(AppExecutor mExecutor, OpenWeatherService mService, WeatherDatabaseDao mDao) {
        this.mExecutor = mExecutor;
        this.mService = mService;
        this.mDao = mDao;
    }

    public LiveData<Resource<List<City>>> loadDataForCity(String cityName) {
        return new NetworkBoundsResource<List<City>, CityResponse>(mExecutor) {

            @Override
            protected void saveCallResult(@NonNull CityResponse item) {
                City city = item.getCity();
                city = AppUtils.formatData(item.getCityCityList(), city);
                mDao.insert(city);
            }

            @Override
            protected boolean shouldFetch(@NonNull List<City> data) {
                return data == null || data.size() == 0 || repoCityRateLimiter.shouldFetch(cityName);
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
        }.asLiveData();
    }
}
