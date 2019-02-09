package co.alexdev.retrotest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StoryResponse {

    @Expose
    private int num_items;

    private int plm;

    @SerializedName("items")
    private List<Story> stories;

    public int getNum_items() {
        return num_items;
    }

    public List<Story> getStories() {
        return stories;
    }

    @Override
    public String toString() {
        return "StoryResponse{" +
                "num_items=" + num_items +
                ", plm=" + plm +
                ", stories=" + stories +
                '}';
    }
}
