package co.alexdev.weatherizer.repo;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import co.alexdev.weatherizer.model.AppExecutor;
import co.alexdev.weatherizer.api.CityResponse;
import co.alexdev.weatherizer.api.ApiResponse;
import co.alexdev.weatherizer.network.resource.NetworkBoundsResource;
import co.alexdev.weatherizer.network.resource.Resource;
import co.alexdev.weatherizer.network.service.OpenWeatherService;
import co.alexdev.weatherizer.scope.WeatherizerAppScope;
import co.alexdev.weatherizer.utils.RateLimiter;
import timber.log.Timber;

@WeatherizerAppScope
public class Repository {

    final AppExecutor mExecutor;
    final OpenWeatherService mService;

    private RateLimiter<String> repoCityRateLimiter = new RateLimiter<>(10, TimeUnit.MINUTES);

    @Inject
    public Repository(AppExecutor mExecutor, OpenWeatherService service) {
        this.mExecutor = mExecutor;
        this.mService = service;
    }

    public LiveData<Resource<CityResponse>> loadDataForCity(String cityName) {
        return new NetworkBoundsResource<CityResponse, CityResponse>(mExecutor) {

            @Override
            protected void saveCallResult(@NonNull CityResponse item) {
                Timber.d("saveCallResult called");
                //TODO INSERT TO DATABASE WHEN IT WILL BE READY
            }

            @Override
            protected boolean shouldFetch(@NonNull CityResponse data) {
                Timber.d("shouldFetch called");
                return data == null || repoCityRateLimiter.shouldFetch(cityName);
            }

            @NonNull
            @Override
            protected LiveData<CityResponse> loadFromDatabase() {
                Timber.d("loadFromDatabase called");
                // TODO LOAD FROM DB WHEN IT WILL BE READY
                return null;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<CityResponse>> createCall() {
                Timber.d("createCall called");
                return mService.cityData(cityName);
            }

            @Override
            protected void onFetchFailed() {
                super.onFetchFailed();
                Timber.d("onFetchFailed called");
                repoCityRateLimiter.reset(cityName);
            }
        }.asLiveData();
    }
}
