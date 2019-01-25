package co.alexdev.weatherizer.viewmodel.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import co.alexdev.weatherizer.repo.AppRepository;
import co.alexdev.weatherizer.viewmodel.SharedViewModel;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private AppRepository mRepository;

    public ViewModelFactory(AppRepository repository) {
        mRepository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SharedViewModel.class)) {
            return (T) new SharedViewModel(mRepository);
        }
        throw new IllegalArgumentException("Unknown View Model class" + modelClass.getSimpleName());
    }
}
