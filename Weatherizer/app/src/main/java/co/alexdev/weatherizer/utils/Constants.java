package co.alexdev.weatherizer.utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

public class Constants {

    public static class NetworkingStatus {

        public static final int RESPONSE_ERROR = 0;
        public static final int RESPONSE_SUCCESS = 1;
        public static final int RESPONSE_LOADING = 2;

        @Retention(RetentionPolicy.SOURCE)
        @IntDef({RESPONSE_ERROR,RESPONSE_SUCCESS,RESPONSE_LOADING})
        public @interface NetworkStatus { }
    }
}
