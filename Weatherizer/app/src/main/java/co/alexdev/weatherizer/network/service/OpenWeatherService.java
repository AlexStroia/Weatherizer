package co.alexdev.weatherizer.network.service;

import androidx.lifecycle.LiveData;
import co.alexdev.weatherizer.api.CityResponse;
import co.alexdev.weatherizer.api.ApiResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherService {

    @GET("forecast")
    LiveData<ApiResponse<CityResponse>> cityData(@Query("q") String cityName);
}
