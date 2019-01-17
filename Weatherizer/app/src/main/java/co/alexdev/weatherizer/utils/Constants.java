package co.alexdev.weatherizer.utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;
import androidx.annotation.StringDef;
import co.alexdev.weatherizer.BuildConfig;

public class Constants {

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

        public static final String APP_KEY_ID = "appid=";
        public static final String API_KEY = BuildConfig.MY_API_KEY;

        @Retention(RetentionPolicy.SOURCE)
        @StringDef({APP_KEY_ID, API_KEY})
        public @interface api {
        }
    }
}
