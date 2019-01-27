package co.alexdev.weatherizer.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PrefJobUtils {

    public static String getWeatherIcon(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(Constants.PREF_KEYS.CITY_WEATHER_IC, "ic_01d");
    }
}
