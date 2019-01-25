package co.alexdev.weatherizer.job;

import android.content.Context;


import co.alexdev.weatherizer.utils.NotificationUtils;
import co.alexdev.weatherizer.utils.PrefJobUtils;

public class ReminderWeatherTask {


    public static void executeTask(Context context) {
        fetchData(context);
    }

    private static void fetchData(Context context) {
        /*Show the notification with the minimum temp for the day */
        NotificationUtils.setNotifData( PrefJobUtils.getWeatherIcon(context));
        NotificationUtils.remindUserNotificationWeather(context);
    }
}
