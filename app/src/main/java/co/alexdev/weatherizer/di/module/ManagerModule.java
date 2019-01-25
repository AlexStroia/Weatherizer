package co.alexdev.weatherizer.di.module;

import android.content.Context;

import co.alexdev.weatherizer.utils.LocationManager;
import co.alexdev.weatherizer.utils.NetworkManager;
import dagger.Module;
import dagger.Provides;

@Module(includes = ContextModule.class)
public class ManagerModule {

    @Provides
    public LocationManager provideLocationManager(Context context) {
        return new LocationManager(context);
    }
}
