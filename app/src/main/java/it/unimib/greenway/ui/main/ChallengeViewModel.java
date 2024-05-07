package it.unimib.greenway.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import it.unimib.greenway.data.repository.challenge.IChallengeRepositoryWithLiveData;
import it.unimib.greenway.model.Result;

public class ChallengeViewModel extends ViewModel {
    private static final String TAG = ChallengeViewModel.class.getSimpleName();

    private final IChallengeRepositoryWithLiveData challengeRepositoryWithLiveData;
    private MutableLiveData<Result> challengeMutableLiveData;


    public ChallengeViewModel(IChallengeRepositoryWithLiveData iChallengeRepositoryWithLiveData) {
        this.challengeRepositoryWithLiveData = iChallengeRepositoryWithLiveData;
    }

    public MutableLiveData<Result> getChallengeMutableLiveData() {

        getChallenge();

        return challengeMutableLiveData;
    }

    public void getChallenge() {
        challengeMutableLiveData = challengeRepositoryWithLiveData.getChallenge();
    }
}
