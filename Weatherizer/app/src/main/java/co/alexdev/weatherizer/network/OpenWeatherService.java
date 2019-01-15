package co.alexdev.weatherizer.network;

import co.alexdev.weatherizer.model.response.CityList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface OpenWeatherService {

    @GET("forecast/{cityName}")
    Call<CityList> cityData(@Path("cityName") String cityName);

}
