package co.alexdev.weatherizer.utils;

import java.util.List;

import androidx.annotation.Nullable;
import co.alexdev.weatherizer.model.weather.City;
import co.alexdev.weatherizer.model.weather.CityList;

public class AppUtils {

   public static City formatData(@Nullable List<CityList> list, City city) {

        for(CityList cityList: list) {
            city.setTemp_max(cityList.getMain().getTemp_max());
            city.setTemp_min(cityList.getMain().getTemp_min());
        }
        return city;
    }
}
