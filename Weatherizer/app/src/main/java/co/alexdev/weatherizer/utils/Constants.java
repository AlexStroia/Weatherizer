package co.alexdev.weatherizer.utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;
import androidx.annotation.StringDef;
import co.alexdev.weatherizer.BuildConfig;

public class Constants {

    public static class URL {

        public static final String URL_SCHEME = "http";
        public static final String BASE_IMAGE_URL_PATH = "openweathermap.org";
        public static final String IMAGE_PATH = "img/w/";
        public static final String IMAGE_FORMAT = ".png";
    }

    public static class NetworkingStatus {

        public static final int RESPONSE_ERROR = 0;
        public static final int RESPONSE_SUCCESS = 1;
        public static final int RESPONSE_LOADING = 2;

        @Retention(RetentionPolicy.SOURCE)
        @IntDef({RESPONSE_ERROR, RESPONSE_SUCCESS, RESPONSE_LOADING})
        public @interface NetworkStatus {
        }
    }

    public static class API_CONSTANTS {

        public static final String APP_KEY_ID = "appid";
        public static final String API_KEY = BuildConfig.MY_API_KEY;

        @Retention(RetentionPolicy.SOURCE)
        @StringDef({APP_KEY_ID, API_KEY})
        public @interface api {
        }
    }

    public static class GOOGLE_MAP_VIEW {

        public static final String BUNDLE_KEY = "MapViewBundleKey";
    }

    public static class PERMISSION_KEYS {

        public static final int PERMISSION_ACCES_FINE_LOCATION = 1;

    }

    public static class BUNDLE_KEYS {

        public static final String LAT_LNG_KEY = "LAT_LNG";
    }

    public static class PREF_KEYS {

        public static final String START_APP_LAT_COORDINATE = "START_APP_LAT_COORDINATE";
        public static final String START_APP_LON_COORDINATE = "START_APP_LON_COORDINATE";
        public static final String PREF_ALLOW_NOTIFICATION = "allow_notification";
        public static final String CITY_WEATHER = "CITY_WEATHER";
        public static final String CITY_WEATHER_IC = "CITY_WEATHER_IC";

    }

    public static class LOCATION_KEYS {
        public static final int REQUEST_LOCATION_DIALOG = 1;
    }

    public static class INTENT_KEYS {

        public static final int LAUNCH_WEATHER_ACTIVITY_PENDING_INTENT = 0;
    }

    public static class NOTIFICATIONS_KEYS {

        public static final String NOTIFICATION_CHANNEL_ID = "1337";
        public static final String NOTIFICATION_CHANNEL_NAME = "Weather_Channel";

        public static final int NOTIFICATION_UNIQUE_ID = 1;
    }

    public static class ICONS {

        public static final String IC_01d = "01d";
        public static final String IC_01n = "01n";
        public static final String IC_02d = "02d";
        public static final String IC_03d = "03d";
        public static final String IC_03n = "03n";
        public static final String IC_04d = "04d";
        public static final String IC_04n = "04n";
        public static final String IC_09d = "09d";
        public static final String IC_09n = "09n";
        public static final String IC_10d = "10d";
        public static final String IC_10n = "10n";
        public static final String IC_11d = "11d";
        public static final String IC_11n = "11n";
        public static final String IC_13d = "13d";
        public static final String IC_13n = "13n";
        public static final String IC_50d = "50d";
        public static final String IC_50n = "50n";
    }
}
