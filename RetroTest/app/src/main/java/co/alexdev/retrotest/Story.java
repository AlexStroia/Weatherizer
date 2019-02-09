package co.alexdev.retrotest;

import com.google.gson.annotations.SerializedName;

/**Serialized Name as Story
 * Base class for the method /stories */

public class Story {

    private int id;
    private String last_update;
    private String cover_img;
    private String name;
    private String content;

    @SerializedName("meta")
    private StoryInfo storyInfo;

    public int getId() {
        return id;
    }

    public String getLast_update() {
        return last_update;
    }

    public String getCover_img() {
        return cover_img;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public StoryInfo getStoryInfo() {
        return storyInfo;
    }

    @Override
    public String toString() {
        return "Story{" +
                "id=" + id +
                ", last_update='" + last_update + '\'' +
                ", cover_img='" + cover_img + '\'' +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", storyInfo=" + storyInfo +
                '}';
    }
}
