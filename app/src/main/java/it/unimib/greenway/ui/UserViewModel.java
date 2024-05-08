package it.unimib.greenway.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import it.unimib.greenway.data.repository.user.IUserRepository;
import it.unimib.greenway.model.Result;
import it.unimib.greenway.model.StatusChallenge;
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

    public MutableLiveData<Result> registerUserMutableLiveData(String nome, String cognome, String email, String password, List<StatusChallenge> statusChallengeList) {
        if (userMutableLiveData == null) {
            registerUser(nome, cognome, email, password, statusChallengeList);
        }
        return userMutableLiveData;
    }

    public void registerUser(String nome, String cognome, String email, String password, List<StatusChallenge> statusChallengeList) {
        userMutableLiveData = userRepository.registerUser(nome, cognome, email, password, statusChallengeList);
    }

    public MutableLiveData<Result> loginUserMutableLiveData(String email, String password) {
            loginUser(email, password);
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
            getUser(idToken);
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

    public MutableLiveData<Result> logout() {
        if (userMutableLiveData == null) {
            userMutableLiveData = userRepository.logout();
        } else {
            userRepository.logout();
        }

        return userMutableLiveData;
    }

    public MutableLiveData<Result> changePasswordMutableLiveData(String token, String newPw, String oldPw){
             changePassword(token, newPw, oldPw);

        return userMutableLiveData;
    }

    private void changePassword(String token, String newPw, String oldPw){
        userMutableLiveData = userRepository.changePassword(token, newPw, oldPw);
    }

    public MutableLiveData<Result> changePhotoMutableLiveData(String token, String imageBitmap){
        changePhoto(token, imageBitmap);

        return userMutableLiveData;
    }

    private void changePhoto(String token, String imageBitmap){
        userMutableLiveData = userRepository.changePhoto(token, imageBitmap);
    }

    //update co2Car
    public MutableLiveData<Result> updateCo2CarMutableLiveData(String idToken, double co2Car){
        updateCo2Car(idToken, co2Car);
        return userMutableLiveData;
    }

    public void updateCo2Car(String idToken, double co2Car){
        userMutableLiveData = userRepository.updateCo2Car(idToken, co2Car);
    }


}
