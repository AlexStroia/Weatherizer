package co.alexdev.weatherizer.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import co.alexdev.weatherizer.model.weather.City;
import co.alexdev.weatherizer.model.weather.CityList;

public class CityResponse {

    @SerializedName("list")
    private List<CityList> cityCityList;
    private City city;

    public List<CityList> getCityCityList() {
        return cityCityList;
    }

    public void setCityCityList(List<CityList> cityCityList) {
        this.cityCityList = cityCityList;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "CityResponse{" +
                "cityCityList=" + cityCityList +
                ", city=" + city +
                '}';
    }
}
