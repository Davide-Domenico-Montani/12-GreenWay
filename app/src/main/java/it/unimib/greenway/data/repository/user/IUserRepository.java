package it.unimib.greenway.data.repository.user;

import androidx.lifecycle.MutableLiveData;

import it.unimib.greenway.model.Result;

public interface IUserRepository {
    MutableLiveData<Result> getGoogleUser(String idToken);
    void signInWithGoogle(String token);
}
