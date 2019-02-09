package co.alexdev.retrotest;



import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {


    final Retrofit retrofit;
    static RetrofitClient mInstance;

    /*Using an OkHttpClient and on top of it we are adding an interceptor used to intercept requests so we can add the API KEY*/
    private RetrofitClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        /*Get the original requests
                         * From the requests we get the original URL
                         * After that we are creating a new url where using the original requests and adding a new query parameter
                         * After that we are adding it to the original requests
                         * After we are building the request
                         * And after we pass it to the request
                         * and pass it to the chain*/
                        Request originalRequest = chain.request();

                        return chain.proceed(originalRequest);
                    }
                })
                .addInterceptor(interceptor)
                .build();


        retrofit = new Retrofit.Builder().baseUrl("https://api.tolbacupovesti.ro/ro/api/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /*Singleton instance*/
    public static synchronized RetrofitClient shared() {
        if (mInstance == null) {
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }

    /*Create the Api Endpoints*/
    public StoryBagService getMovieApi() {
        return retrofit.create(StoryBagService.class);
    }
}