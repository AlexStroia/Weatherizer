package co.alexdev.weatherizer.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import co.alexdev.weatherizer.repo.AppRepository;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private AppRepository mRepository;

    public ViewModelFactory(AppRepository repository) {
        mRepository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(BaseViewModel.class)) {
            return (T) new BaseViewModel(mRepository);
        }
        throw new IllegalArgumentException("Unknwon View Model class" + modelClass.getSimpleName());
    }
}
