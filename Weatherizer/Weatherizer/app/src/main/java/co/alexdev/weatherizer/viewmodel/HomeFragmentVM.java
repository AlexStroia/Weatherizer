package co.alexdev.weatherizer.viewmodel;

import androidx.lifecycle.ViewModel;
import co.alexdev.weatherizer.repo.AppRepository;

public class HomeFragmentVM extends ViewModel {

    private AppRepository mRepository;

    public HomeFragmentVM(AppRepository repository) {
        this.mRepository = repository;
    }
}
