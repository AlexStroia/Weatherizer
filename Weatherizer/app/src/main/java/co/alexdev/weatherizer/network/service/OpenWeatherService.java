package co.alexdev.weatherizer.network.service;

import androidx.lifecycle.LiveData;
import co.alexdev.weatherizer.model.response.CityList;
import co.alexdev.weatherizer.network.resource.ApiResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherService {

    @GET("forecast")
    LiveData<ApiResponse<CityList>> cityData(@Query("q") String cityName);
}
