package co.alexdev.weatherizer.viewmodel;

import com.google.android.gms.common.api.GoogleApiClient;

import java.util.List;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import co.alexdev.weatherizer.model.weather.City;
import co.alexdev.weatherizer.network.resource.Resource;
import co.alexdev.weatherizer.repo.AppRepository;
import timber.log.Timber;

public class HomeViewModel extends ViewModel {

    private AppRepository mRepository;

    public HomeViewModel(AppRepository mRepository) {
        this.mRepository = mRepository;
    }

    public LiveData<Resource<List<City>>> loadDataForCity(String cityName) {
        Timber.d("Called: " + (mRepository == null));
        return mRepository.loadDataForCity(cityName);
    }

    public boolean isGoogleClinetConnected(GoogleApiClient googleApiClient) {
        return googleApiClient.isConnected();
    }
}
