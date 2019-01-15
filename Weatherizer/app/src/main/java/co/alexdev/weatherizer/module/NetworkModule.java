package co.alexdev.weatherizer.module;

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

    @Provides
    public HttpLoggingInterceptor loggingInterceptor() {
        return new HttpLoggingInterceptor(message -> Timber.i(message));
    }

    @Provides
    public Interceptor interceptor() {
        return chain -> {
            Request originalRequest = chain.request();
            HttpUrl originalHttpUrl = originalRequest.url();

            HttpUrl newUrlWithApiKey = originalHttpUrl.newBuilder().addQueryParameter("API_KEY", "API_KEY_HERE").build();

            Request.Builder requestBuilder = originalRequest.newBuilder().url(newUrlWithApiKey);

            Request request = requestBuilder.build();

            return chain.proceed(request);
        };
    }

    @Provides
    public OkHttpClient okHttpClient(Interceptor apiKeyInterceptor, HttpLoggingInterceptor loggingInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(apiKeyInterceptor)
                .addInterceptor(loggingInterceptor).build();
    }
}
