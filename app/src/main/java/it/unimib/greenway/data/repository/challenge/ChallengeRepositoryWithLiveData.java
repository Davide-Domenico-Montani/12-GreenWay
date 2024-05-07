package it.unimib.greenway.data.repository.challenge;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import it.unimib.greenway.data.source.challenge.BaseChallengeRemoteDataSource;
import it.unimib.greenway.data.source.challenge.ChallengeCallBack;
import it.unimib.greenway.model.AirQualityResponse;
import it.unimib.greenway.model.Challenge;
import it.unimib.greenway.model.ChallengeResponse;
import it.unimib.greenway.model.Result;

public class ChallengeRepositoryWithLiveData implements IChallengeRepositoryWithLiveData, ChallengeCallBack {
    private BaseChallengeRemoteDataSource challengeRemoteDataSource;
    private final MutableLiveData<Result> challengeListLiveData;

    public ChallengeRepositoryWithLiveData(BaseChallengeRemoteDataSource challengeRemoteDataSource){
        challengeListLiveData = new MutableLiveData<>();
        this.challengeRemoteDataSource = challengeRemoteDataSource;
        this.challengeRemoteDataSource.setChallengeCallBack(this);
    }

    @Override
    public MutableLiveData<Result> getChallenge() {
        challengeRemoteDataSource.getChallenge();
        return challengeListLiveData;
    }

    @Override
    public void onSuccessFromRemote(List<Challenge> response) {
        Result.ChallengeResponseSuccess result = new Result.ChallengeResponseSuccess(new ChallengeResponse(response));
        challengeListLiveData.postValue(result);
    }

    @Override
    public void onFailureFromRemote(String message) {
        Result.Error result = new Result.Error(message);
        challengeListLiveData.postValue(result);
    }
}
