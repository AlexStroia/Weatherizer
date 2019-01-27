package co.alexdev.weatherizer.viewmodel;

import com.google.android.gms.common.api.GoogleApiClient;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import co.alexdev.weatherizer.model.weather.City;
import co.alexdev.weatherizer.model.weather.Coordonates;
import co.alexdev.weatherizer.model.weather.History;
import co.alexdev.weatherizer.model.weather.Main;
import co.alexdev.weatherizer.model.weather.Weather;
import co.alexdev.weatherizer.network.resource.Resource;
import co.alexdev.weatherizer.repo.AppRepository;
import timber.log.Timber;

public class SharedViewModel extends ViewModel {

    private AppRepository mRepository;
    /*Used to store the coordonates that we send via Bundle */
    private double[] coordonatesArray;
    private MediatorLiveData<Integer> mCityID = new MediatorLiveData<>();
    private MediatorLiveData<Boolean> areCoordinatesAvailable = new MediatorLiveData<>();


    public SharedViewModel(AppRepository mRepository) {
        this.mRepository = mRepository;
        /*Initially there are no cooronates*/
        areCoordinatesAvailable.setValue(false);
    }

    public LiveData<Resource<List<City>>> loadAllCities(String cityName) {
        return mRepository.loadAllCities(cityName);
    }

    public boolean isGoogleClientConnected(GoogleApiClient googleApiClient) {
        return googleApiClient.isConnected();
    }

    public LiveData<City> getCity(String locality) {
        return mRepository.getCity(locality);
    }

    public LiveData<Coordonates> getCoordinate() {
        return Transformations.switchMap(mCityID, coordonatesID -> mRepository.getCoordinate(coordonatesID));
    }

    public LiveData<List<Main>> getWeatherForFiveDays() {
        return Transformations.switchMap(mCityID, cityID -> mRepository.getFiveDaysWeather(cityID));
    }

    public LiveData<List<History>> getHistoryList() {
        return mRepository.getHistoryList();
    }

    public void setCityId(int cityId) {
        mCityID.setValue(cityId);
    }

    public void setCoordonates(double lat, double lon) {
        Timber.d("LAT: " + lat + "LON " + lon);
        coordonatesArray = new double[2];
        coordonatesArray[0] = lat;
        coordonatesArray[1] = lon;
        areCoordinatesAvailable.setValue(true);
    }

    public double[] getCoordonatesArray() {
        return coordonatesArray;
    }

    public MediatorLiveData<Boolean> areCoordinatesSet() {
        return areCoordinatesAvailable;
    }
}
