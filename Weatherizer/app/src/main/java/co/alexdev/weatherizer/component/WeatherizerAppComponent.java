package co.alexdev.weatherizer.component;

import co.alexdev.weatherizer.module.OpenWeatherServiceModule;
import co.alexdev.weatherizer.network.service.OpenWeatherService;
import co.alexdev.weatherizer.scope.WeatherizerAppScope;
import co.alexdev.weatherizer.ui.activity.WeatherActivity;
import dagger.Component;

@Component(modules = OpenWeatherServiceModule.class)
@WeatherizerAppScope
public interface WeatherizerAppComponent {
    void inject(WeatherActivity weatherActivity);
}
