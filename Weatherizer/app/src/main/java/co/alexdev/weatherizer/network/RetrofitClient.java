package co.alexdev.weatherizer.network;

import retrofit2.Retrofit;

public class RetrofitClient {

    static final String URL_SCHEME = "https";
    static final String BASE_URL = "maps.openweathermap.org";
    static final String DATA_PATH = "data";
    static final String DATA_VERSION = "2.5";

    private Retrofit retrofit;

    private RetrofitClient() {

    }




}
