package co.alexdev.retrotest;

/**Base class for information about a single Story
 * Serialized name as meta*/
public class StoryInfo {

    private String author;
    private String adaptation;
    private int lecture_time;

    public StoryInfo(String author, String adaptation, int lecture_time) {
        this.author = author;
        this.adaptation = adaptation;
        this.lecture_time = lecture_time;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAdaptation() {
        return adaptation;
    }

    public void setAdaptation(String adaptation) {
        this.adaptation = adaptation;
    }

    public int getLecture_time() {
        return lecture_time;
    }

    public void setLecture_time(int lecture_time) {
        this.lecture_time = lecture_time;
    }

    @Override
    public String toString() {
        return "StoryInfo{" +
                "author='" + author + '\'' +
                ", adaptation='" + adaptation + '\'' +
                ", lecture_time=" + lecture_time +
                '}';
    }
}
