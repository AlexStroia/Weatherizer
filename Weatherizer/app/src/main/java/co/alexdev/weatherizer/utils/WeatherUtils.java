package co.alexdev.weatherizer.utils;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import co.alexdev.weatherizer.model.weather.City;
import co.alexdev.weatherizer.model.weather.CityList;
import co.alexdev.weatherizer.model.weather.Coordonates;
import co.alexdev.weatherizer.model.weather.Main;
import co.alexdev.weatherizer.model.weather.Weather;
import co.alexdev.weatherizer.scope.WeatherizerAppScope;
import timber.log.Timber;

@WeatherizerAppScope
public class WeatherUtils {

    private StringUtils mStringUtils;

    @Inject
    public WeatherUtils(StringUtils mStringUtils) {
        this.mStringUtils = mStringUtils;
    }

    public City formatData(@Nullable List<CityList> list, @Nullable City city) {

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

    public List<Main> formatMain(@Nullable City city, @Nullable List<CityList> cityList) {
        List<Main> mainList = new ArrayList<>();
        int id = city.getId();

        for (CityList data :
                cityList) {
            for (Weather weather : data.getWeather()) {
                Main main = data.getMain();
                main.setName(city.getName());
                main.setCity_id(id);
                main.setDate_txt(mStringUtils.formatDate(data.getDt_txt()));
                main.setIcon_id(mStringUtils.buildIconURL(weather.getIcon()));
                Timber.d("Main icon url is: " + main.getIcon_id());
                mainList.add(main);
            }
        }

        return mainList;
    }


    /*Formating data for the database so we can perform queries later */
    public Coordonates formatCoordonates(@Nullable City city, @Nullable Coordonates coordonates) {
        coordonates.setCity_id(city.getId());
        return coordonates;
    }

    public List<Weather> formatWeather(@Nullable List<CityList> cityList, City city) {
        List<Weather> weatherList = new ArrayList<>();
        cityList.forEach(cityList1 -> cityList1.getWeather().forEach(weather -> {
            weather.setCity_id(city.getId());
            weather.setDate_txt(cityList1.getDt_txt());
            weatherList.add(weather);
            Timber.d("Weather: " + weather.toString());
        }));
        return weatherList;
    }
}
