package it.unimib.greenway.data.repository.user;

import androidx.lifecycle.MutableLiveData;

import it.unimib.greenway.model.Result;
import it.unimib.greenway.model.User;

public interface IUserRepository {
    MutableLiveData<Result> getGoogleUser(String idToken);
    void signInWithGoogle(String token);
    MutableLiveData<Result> registerUser(String nome, String cognome, String email, String password);
    void signUp(String nome, String cognome, String email, String password);
    MutableLiveData<Result> loginUser(String email, String password);
    void login(String email, String password);

    MutableLiveData<Result> getUserData(String email, String password);
    void getUserCredential(String email, String password);
    MutableLiveData<Result> getUser(String idToken);
     User getLoggedUser();

     MutableLiveData<Result> updateCo2Saved(String idToken, String transportType, double co2Saved, double kmTravel);
}
