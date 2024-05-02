package it.unimib.greenway.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import it.unimib.greenway.data.repository.user.IUserRepository;
import it.unimib.greenway.model.Result;
import it.unimib.greenway.model.User;

public class UserViewModel extends ViewModel {
    private static final String TAG = UserViewModel.class.getSimpleName();
    private final IUserRepository userRepository;

    private MutableLiveData<Result> userMutableLiveData;

    public UserViewModel(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public MutableLiveData<Result> getGoogleUserMutableLiveData(String token) {
        if (userMutableLiveData == null) {
            getGoogleUserData(token);
        }
        return userMutableLiveData;
    }

    private void getGoogleUserData(String token) {
        userMutableLiveData = userRepository.getGoogleUser(token);
    }

    public MutableLiveData<Result> registerUserMutableLiveData(String nome, String cognome, String email, String password) {
        if (userMutableLiveData == null) {
            registerUser(nome, cognome, email, password);
        }
        return userMutableLiveData;
    }

    public void registerUser(String nome, String cognome, String email, String password) {
        userMutableLiveData = userRepository.registerUser(nome, cognome, email, password);
    }

    public MutableLiveData<Result> loginUserMutableLiveData(String email, String password) {
        if (userMutableLiveData == null) {
            loginUser(email, password);
        }
        return userMutableLiveData;
    }

    public void loginUser(String email, String password) {
        userMutableLiveData = userRepository.loginUser(email, password);
    }

    public MutableLiveData<Result> getUserMutableLiveData(String email, String password){
        getUserData(email, password);
        return userMutableLiveData;
    }

    private void getUserData(String email, String password) {
        userMutableLiveData = userRepository.getUserData(email, password);
    }

    public MutableLiveData<Result> getUserDataMutableLiveData(
            String idToken) {
        if (userMutableLiveData == null) {
            getUser(idToken);
        }
        return userMutableLiveData;
    }

    private void getUser(String idToken) {
        userMutableLiveData = userRepository.getUser(idToken);
    }

    public User getLoggedUser() {
        return userRepository.getLoggedUser();
    }

    public MutableLiveData<Result> updateCo2SavedMutableLiveData(String idToken, String transportType, double co2Saved, double kmTravel){
        updateCo2Saved(idToken, transportType, co2Saved, kmTravel);
        return userMutableLiveData;
    }

    public void updateCo2Saved(String idToken, String transportType, double co2Saved, double kmTravel){
        userMutableLiveData = userRepository.updateCo2Saved(idToken, transportType, co2Saved, kmTravel);
    }
}
