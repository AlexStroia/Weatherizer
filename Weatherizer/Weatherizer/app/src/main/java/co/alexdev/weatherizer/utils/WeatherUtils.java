package co.alexdev.weatherizer.utils;

import java.util.List;

import androidx.annotation.Nullable;
import co.alexdev.weatherizer.model.weather.City;
import co.alexdev.weatherizer.model.weather.CityList;
import timber.log.Timber;

public class WeatherUtils {

    public static City formatData(@Nullable List<CityList> list, City city) {

        if (list != null && !list.isEmpty()) {
            for (CityList cityList : list) {
                Timber.d("TempMax: " + cityList.getMain().getTemp_max());
                Timber.d("TempMin: " + cityList.getMain().getTemp_min());
                city.setTemp_max(cityList.getMain().getTemp_max());
                city.setTemp_min(cityList.getMain().getTemp_min());
                city.setDate(cityList.getDt_txt());
            }
        }
        return city;
    }
}
