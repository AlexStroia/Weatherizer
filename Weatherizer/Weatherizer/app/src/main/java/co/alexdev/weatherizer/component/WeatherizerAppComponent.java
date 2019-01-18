package co.alexdev.weatherizer.component;

import co.alexdev.weatherizer.di.OpenWeatherServiceModule;
import co.alexdev.weatherizer.di.WeatherizerDatabaseModule;
import co.alexdev.weatherizer.scope.WeatherizerAppScope;
import co.alexdev.weatherizer.ui.activity.WeatherActivity;
import dagger.Component;

@Component(modules =  {
        OpenWeatherServiceModule.class,
        WeatherizerDatabaseModule.class
})
@WeatherizerAppScope
public interface WeatherizerAppComponent {
    void inject(WeatherActivity weatherActivity);
}
