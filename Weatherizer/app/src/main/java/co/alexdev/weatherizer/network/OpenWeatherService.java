package co.alexdev.weatherizer.network;

import co.alexdev.weatherizer.model.response.CityList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherService {

    @GET("forecast")
    Call<CityList> cityData(@Query("q") String cityName);
}
