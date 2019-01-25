package co.alexdev.weatherizer.di;

import android.net.Uri;

import co.alexdev.weatherizer.model.livedata.LiveDataCallAdapterFactory;
import co.alexdev.weatherizer.network.service.OpenWeatherService;
import co.alexdev.weatherizer.scope.WeatherizerAppScope;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = NetworkModule.class)
public class OpenWeatherServiceModule {

    private static final String URL_SCHEME = "https";
    private static final String BASE_URL = "api.openweathermap.org";
    private static final String DATA_PATH = "data/2.5/";

    @Provides
    @WeatherizerAppScope
    public Retrofit retrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(getEndpoint().toString())
                .client(okHttpClient)
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
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
    @WeatherizerAppScope
    public OpenWeatherService weatherService(Retrofit retrofit) {
        return retrofit.create(OpenWeatherService.class);
    }
}
