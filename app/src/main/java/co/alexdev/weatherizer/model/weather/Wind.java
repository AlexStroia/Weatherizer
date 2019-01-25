package co.alexdev.weatherizer.model.weather;

public final class Wind {

    private float speed;

    public Wind(float speed) {
        this.speed = speed;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        return "Wind{" +
                "speed=" + speed +
                '}';
    }
}
