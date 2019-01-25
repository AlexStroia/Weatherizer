package co.alexdev.weatherizer.di.module;

import android.content.Context;

import co.alexdev.weatherizer.utils.LocationUtils;
import co.alexdev.weatherizer.utils.NotificationUtils;
import co.alexdev.weatherizer.utils.PermissionUtils;
import co.alexdev.weatherizer.utils.PrefUtils;
import co.alexdev.weatherizer.utils.ReminderUtils;
import co.alexdev.weatherizer.utils.StringUtils;
import co.alexdev.weatherizer.utils.WeatherUtils;
import dagger.Module;
import dagger.Provides;

@Module(includes = {ContextModule.class})
public class UtilsModule {

    @Provides
    public PermissionUtils providePermissionUtils(Context context) {
        return new PermissionUtils(context);
    }

    @Provides
    public LocationUtils provideLocationUtils() {
        return new LocationUtils();
    }

    @Provides
    public WeatherUtils provideWeatherUtils(StringUtils stringUtils) {
        return new WeatherUtils(stringUtils);
    }

    @Provides
    public StringUtils provideStringUtils() {
        return new StringUtils();
    }

    @Provides
    public PrefUtils providePrefUtils(Context context) {
        return new PrefUtils(context);
    }

    @Provides
    public NotificationUtils provideNotificationUtils(Context context) {
        return new NotificationUtils();
    }

    @Provides
    public ReminderUtils provideReminderUtils(Context context) {
        return new ReminderUtils(context);
    }
}
