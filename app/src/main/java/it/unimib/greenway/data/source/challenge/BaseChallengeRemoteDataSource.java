package it.unimib.greenway.data.source.challenge;

public abstract class BaseChallengeRemoteDataSource {
    protected ChallengeCallBack challengeCallBack;

    public void setChallengeCallBack(ChallengeCallBack challengeCallBack){
        this.challengeCallBack = challengeCallBack;
    }

    public abstract void getChallenge();
}
