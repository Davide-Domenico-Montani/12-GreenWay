package it.unimib.greenway.ui.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import it.unimib.greenway.data.repository.airQuality.IAirQualityRepositoryWithLiveData;

public class AirQualityViewModelFactory implements ViewModelProvider.Factory {
    private final IAirQualityRepositoryWithLiveData iAirQualityRepositoryWithLiveData;

    public AirQualityViewModelFactory(IAirQualityRepositoryWithLiveData iAirQualityRepositoryWithLiveData) {
        this.iAirQualityRepositoryWithLiveData = iAirQualityRepositoryWithLiveData;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AirQualityViewModel(iAirQualityRepositoryWithLiveData);
    }
}
