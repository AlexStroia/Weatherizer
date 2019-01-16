package co.alexdev.weatherizer.module;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import co.alexdev.weatherizer.model.response.CityList;
import co.alexdev.weatherizer.network.resource.ApiResponse;
import co.alexdev.weatherizer.network.resource.NetworkBoundsResource;
import co.alexdev.weatherizer.network.resource.Resource;
import co.alexdev.weatherizer.network.service.OpenWeatherService;

public class Repository {

    private final AppExecutor mExecutor;
    private final OpenWeatherService mService;

    @Inject
    public Repository(AppExecutor mExecutor, OpenWeatherService service) {
        this.mExecutor = mExecutor;
        this.mService = service;
    }

    public LiveData<Resource<CityList>> loadDataForCity(String cityName) {
        return new NetworkBoundsResource<CityList, CityList>(mExecutor) {

            @Override
            protected void saveCallResult(@NonNull CityList item) {
                //TODO INSERT TO DATABASE WHEN IT WILL BE READY
            }

            @Override
            protected boolean shouldFetch(@NonNull CityList data) {
                return data == null;
            }

            @NonNull
            @Override
            protected LiveData<CityList> loadFromDatabase() {
                // TODO LOAD FROM DB WHEN IT WILL BE READY
                return null;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<CityList>> createCall() {
                return mService.cityData(cityName);
            }
        }.asLiveData();
    }
}
