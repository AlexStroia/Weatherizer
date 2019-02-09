package co.alexdev.retrotest;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Call<StoryResponse> call = RetrofitClient.shared().getMovieApi().getStory();

        call.enqueue(new Callback<StoryResponse>() {
            @Override
            public void onResponse(Call<StoryResponse> call, Response<StoryResponse> response) {
                Timber.d(call.toString());
            }

            @Override
            public void onFailure(Call<StoryResponse> call, Throwable t) {
                Timber.d(t.toString());
            }
        });
    }
}
