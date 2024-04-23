package it.unimib.greenway.ui.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import it.unimib.greenway.data.repository.airQuality.IAirQualityRepositoryWithLiveData;
import it.unimib.greenway.data.repository.routes.IRoutesRepositoryWithLiveData;

public class RoutesViewModelFactory implements ViewModelProvider.Factory{
    private final IRoutesRepositoryWithLiveData iRoutesRepositoryWithLiveData;

    public RoutesViewModelFactory(IRoutesRepositoryWithLiveData iRoutesRepositoryWithLiveData) {
        this.iRoutesRepositoryWithLiveData = iRoutesRepositoryWithLiveData;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new RoutesViewModel(iRoutesRepositoryWithLiveData);
    }

}
