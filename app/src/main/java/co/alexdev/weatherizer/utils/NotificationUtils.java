package co.alexdev.weatherizer.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import javax.inject.Inject;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import co.alexdev.weatherizer.R;
import co.alexdev.weatherizer.scope.WeatherizerAppScope;
import co.alexdev.weatherizer.ui.activity.WeatherActivity;

public class NotificationUtils {

    private static String notifData;
    private static String iconWeatherId;
    private static int iconID;

    public static void remindUserNotificationWeather(Context context) {
        NotificationManager mNotification = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        /*If App version SDK is later or equal withi API level 26, create a notification channel */
        /*A channel can have multiple notifications */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    Constants.NOTIFICATIONS_KEYS.NOTIFICATION_CHANNEL_ID,
                    Constants.NOTIFICATIONS_KEYS.NOTIFICATION_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH);

            /*Create the notification channel*/
            mNotification.createNotificationChannel(notificationChannel);
        }

        /*Design the notification*/
        NotificationCompat.Builder notification = new NotificationCompat.Builder(context, Constants.NOTIFICATIONS_KEYS.NOTIFICATION_CHANNEL_ID)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.ic_warning)
                .setLargeIcon(setImage(context))
                .setContentTitle(context.getString(R.string.notificationTitle))
                .setContentText(context.getString(R.string.your_weather))
                .setContentIntent(launchWeatherActivityIntent(context))
                .setStyle(new NotificationCompat.BigTextStyle())
                .setAutoCancel(true);

        /*To set the importante also to HIGH on devices later than Jelly bean but smaller than Oreo*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            notification.setPriority(NotificationManager.IMPORTANCE_HIGH);
        }

        /*Triggeer the notification*/
        mNotification.notify(Constants.NOTIFICATIONS_KEYS.NOTIFICATION_UNIQUE_ID, notification.build());
    }

    /*Launch the application when the notification is clicked*/
    private static PendingIntent launchWeatherActivityIntent(Context context) {
        Intent intent = new Intent(context, WeatherActivity.class);
        return PendingIntent.getActivity(context,
                Constants.INTENT_KEYS.LAUNCH_WEATHER_ACTIVITY_PENDING_INTENT,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    /*Set the notification image*/
    private static Bitmap setImage(Context context) {

        Resources resources = context.getResources();

        Bitmap icon = BitmapFactory.decodeResource(resources, getIconId());
        return icon;
    }

    public static int getIconId() {
        if (iconWeatherId.equals(Constants.ICONS.IC_01d)) {
            iconID = R.drawable.ic_01d;
        } else if (iconWeatherId.equals(Constants.ICONS.IC_01n)) {
            iconID = R.drawable.ic_01n;
        } else if (iconWeatherId.equals(Constants.ICONS.IC_02d)) {
            iconID = R.drawable.ic_02d;
        } else if (iconWeatherId.equals(Constants.ICONS.IC_03d)) {
            iconID = R.drawable.ic_03d;
        } else if (iconWeatherId.equals(Constants.ICONS.IC_03n)) {
            iconID = R.drawable.ic_03n;
        } else if (iconWeatherId.equals(Constants.ICONS.IC_04d)) {
            iconID = R.drawable.ic_04d;
        } else if (iconWeatherId.equals(Constants.ICONS.IC_04n)) {
            iconID = R.drawable.ic_04n;
        } else if (iconWeatherId.equals(Constants.ICONS.IC_09n)) {
            iconID = R.drawable.ic_09n;
        } else if (iconWeatherId.equals(Constants.ICONS.IC_09d)) {
            iconID = R.drawable.ic_09d;
        } else if (iconWeatherId.equals(Constants.ICONS.IC_11n)) {
            iconID = R.drawable.ic_11n;
        } else if (iconWeatherId.equals(Constants.ICONS.IC_11d)) {
            iconID = R.drawable.ic_11d;
        } else if (iconWeatherId.equals(Constants.ICONS.IC_10d)) {
            iconID = R.drawable.ic_10d;
        } else if (iconWeatherId.equals(Constants.ICONS.IC_10n)) {
            iconID = R.drawable.ic_10n;
        } else if (iconWeatherId.equals(Constants.ICONS.IC_13d)) {
            iconID = R.drawable.ic_13d;
        } else if (iconWeatherId.equals(Constants.ICONS.IC_13n)) {
            iconID = R.drawable.ic_13n;
        } else if (iconWeatherId.equals(Constants.ICONS.IC_50d)) {
            iconID = R.drawable.ic_50d;
        } else if (iconWeatherId.equals(Constants.ICONS.IC_50n)) {
            iconID = R.drawable.ic_50n;
        } else {
            iconID = R.drawable.ic_02n;
        }
        return iconID;

    }

  /*  public static void setNotifData(String weatherType, String iconID) {
        notifData = weatherType;
        iconWeatherId = iconID;
    }*/

    public static void setNotifData(String iconID) {
        iconWeatherId = iconID;
    }
}
