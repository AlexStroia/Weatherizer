package co.alexdev.weatherizer.application;

import android.app.Application;

import timber.log.Timber;

public class WeatherizerApplication extends Application {

    public static final String INTENT_FILTER_STRING = "android.net.conn.CONNECTIVITY_CHANGE";

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());
    }
}
