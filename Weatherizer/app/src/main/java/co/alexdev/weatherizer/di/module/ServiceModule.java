package co.alexdev.weatherizer.di.module;

import co.alexdev.weatherizer.job.ReminderWeatherTask;
import co.alexdev.weatherizer.job.WeatherReminderFirebaseJobService;
import dagger.Module;
import dagger.Provides;

@Module
public class ServiceModule {

    @Provides
    public WeatherReminderFirebaseJobService provideWeatherReminderFirebaseJobService(ReminderWeatherTask reminderWeatherTask) {
        return new WeatherReminderFirebaseJobService(reminderWeatherTask);
    }
}
