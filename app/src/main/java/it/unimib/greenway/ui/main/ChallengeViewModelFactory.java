package it.unimib.greenway.ui.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import it.unimib.greenway.data.repository.challenge.IChallengeRepositoryWithLiveData;

public class ChallengeViewModelFactory implements ViewModelProvider.Factory{
    private final IChallengeRepositoryWithLiveData iChallengeRepositoryWithLiveData;

    public ChallengeViewModelFactory(IChallengeRepositoryWithLiveData iChallengeRepositoryWithLiveData) {
        this.iChallengeRepositoryWithLiveData = iChallengeRepositoryWithLiveData;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ChallengeViewModel(iChallengeRepositoryWithLiveData);
    }
}
