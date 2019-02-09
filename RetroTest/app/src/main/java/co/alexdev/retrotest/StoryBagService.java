package co.alexdev.retrotest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface StoryBagService {

    @POST("stories")
    Call<StoryResponse> getStory();

}
