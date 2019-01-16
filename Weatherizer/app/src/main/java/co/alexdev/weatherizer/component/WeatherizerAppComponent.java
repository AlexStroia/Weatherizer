package co.alexdev.weatherizer.component;

import co.alexdev.weatherizer.module.OpenWeatherServiceModule;
import co.alexdev.weatherizer.network.service.OpenWeatherService;
import dagger.Component;

@Component(modules = OpenWeatherServiceModule.class)
public interface WeatherizerAppComponent {
    OpenWeatherService getOpenWeatherService();
}
