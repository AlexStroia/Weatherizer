package co.alexdev.weatherizer.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Inject;

import co.alexdev.weatherizer.scope.WeatherizerAppScope;

@WeatherizerAppScope
public class PrefUtils {

    private static SharedPreferences mSharedPref;

    @Inject
    public PrefUtils(Context context) {
        mSharedPref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setCoordinate(double lat, double lon) {
        mSharedPref.edit().putFloat(Constants.PREF_KEYS.START_APP_LAT_COORDINATE, (float) lat).commit();
        mSharedPref.edit().putFloat(Constants.PREF_KEYS.START_APP_LON_COORDINATE, (float) lon).commit();
    }

    /*Default values, if there are no values than we set the user to 0.0 */
    public float getLatCoordinate() {
        return mSharedPref.getFloat(Constants.PREF_KEYS.START_APP_LAT_COORDINATE, 0);
    }

    public float getLonCoordinate() {
        return mSharedPref.getFloat(Constants.PREF_KEYS.START_APP_LON_COORDINATE, 0);
    }

    public void setWeatherIcon(String icon) {
        mSharedPref.edit().putString(Constants.PREF_KEYS.CITY_WEATHER_IC, icon);
    }

    public boolean isAllowNotificationON() {
        return mSharedPref.getBoolean(Constants.PREF_KEYS.PREF_ALLOW_NOTIFICATION, false);
    }
}
