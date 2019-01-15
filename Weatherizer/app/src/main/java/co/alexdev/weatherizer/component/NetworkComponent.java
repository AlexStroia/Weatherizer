package co.alexdev.weatherizer.component;

import co.alexdev.weatherizer.module.OpenWeatherApiModule;
import dagger.Component;

@Component(modules = OpenWeatherApiModule.class)
public interface NetworkComponent {


}
