package co.alexdev.weatherizer.application;

import android.app.Application;

import timber.log.Timber;

public class WeatherizerApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());
    }
}
