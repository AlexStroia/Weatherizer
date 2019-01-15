package co.alexdev.weatherizer.module;

import android.net.Uri;

import co.alexdev.weatherizer.network.OpenWeatherService;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = NetworkModule.class)
public class OpenWeatherServiceModule {

    static final String URL_SCHEME = "https";
    static final String BASE_URL = "api.openweathermap.org";
    static final String DATA_PATH = "data/2.5/";

    @Provides
    public Retrofit retrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(getEndpoint().toString())
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private Uri getEndpoint() {
            return new Uri.Builder()
                    .scheme(URL_SCHEME)
                    .authority(BASE_URL)
                    .path(DATA_PATH)
                    .build();
    }

    @Provides
    public OpenWeatherService weatherService(Retrofit retrofit) {
        return retrofit.create(OpenWeatherService.class);
    }
}
