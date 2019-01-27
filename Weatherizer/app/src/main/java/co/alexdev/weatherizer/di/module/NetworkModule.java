package co.alexdev.weatherizer.di.module;

import co.alexdev.weatherizer.scope.WeatherizerAppScope;
import co.alexdev.weatherizer.utils.Constants;
import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;

@Module
public class NetworkModule {

    @Constants.API_CONSTANTS.api
    private static String APP_ID = Constants.API_CONSTANTS.APP_KEY_ID;

    @Constants.API_CONSTANTS.api
    private static String APP_KEY_ID = Constants.API_CONSTANTS.API_KEY;

    @Provides
    @WeatherizerAppScope
    public HttpLoggingInterceptor loggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> Timber.i(message));
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return loggingInterceptor;
    }

    @Provides
    @WeatherizerAppScope
    public Interceptor interceptor() {
        return chain -> {
            Request originalRequest = chain.request();
            HttpUrl originalHttpUrl = originalRequest.url();

            HttpUrl newUrlWithApiKey = originalHttpUrl.newBuilder().addQueryParameter(APP_ID, APP_KEY_ID).build();

            Request.Builder requestBuilder = originalRequest.newBuilder().url(newUrlWithApiKey);

            Request request = requestBuilder.build();

            return chain.proceed(request);
        };
    }

    @Provides
    @WeatherizerAppScope
    public OkHttpClient okHttpClient(Interceptor apiKeyInterceptor, HttpLoggingInterceptor loggingInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(apiKeyInterceptor)
                .addInterceptor(loggingInterceptor).build();
    }
}
