package it.unimib.greenway.data.repository.challenge;

import androidx.lifecycle.MutableLiveData;

import it.unimib.greenway.model.Challenge;
import it.unimib.greenway.model.Result;

public interface IChallengeRepositoryWithLiveData {
    MutableLiveData<Result> getChallenge();
}
