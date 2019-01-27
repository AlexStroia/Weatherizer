package co.alexdev.weatherizer.di.component;

import co.alexdev.weatherizer.di.module.ManagerModule;
import co.alexdev.weatherizer.di.module.OpenWeatherServiceModule;
import co.alexdev.weatherizer.di.module.ServiceModule;
import co.alexdev.weatherizer.di.module.UtilsModule;
import co.alexdev.weatherizer.di.module.WeatherizerDatabaseModule;
import co.alexdev.weatherizer.scope.WeatherizerAppScope;
import co.alexdev.weatherizer.ui.activity.WeatherActivity;
import co.alexdev.weatherizer.utils.LocationManager;
import co.alexdev.weatherizer.utils.LocationUtils;
import co.alexdev.weatherizer.utils.NetworkManager;
import co.alexdev.weatherizer.utils.PermissionUtils;
import co.alexdev.weatherizer.utils.PrefUtils;
import co.alexdev.weatherizer.utils.ReminderUtils;
import co.alexdev.weatherizer.utils.StringUtils;
import dagger.Component;

@Component(modules = {
        OpenWeatherServiceModule.class,
        WeatherizerDatabaseModule.class,
        UtilsModule.class,
        ManagerModule.class,
        ServiceModule.class,
})
@WeatherizerAppScope
public interface WeatherizerAppComponent {
    void inject(WeatherActivity weatherActivity);

    PermissionUtils getPermissionUtils();

    LocationUtils getLocationUtils();

    StringUtils getStringUtils();

    PrefUtils getPrefUtils();

    LocationManager getLocationManager();

    NetworkManager getNetworkManager();

    ReminderUtils getReminderUtils();

}
