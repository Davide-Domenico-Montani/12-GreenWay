package it.unimib.greenway.data.repository.user;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import it.unimib.greenway.data.source.airQuality.AirQualityCallBack;
import it.unimib.greenway.data.source.airQuality.BaseAirQualityLocalDataSource;
import it.unimib.greenway.data.source.user.BaseUserAuthenticationRemoteDataSource;
import it.unimib.greenway.data.source.user.BaseUserDataRemoteDataSource;
import it.unimib.greenway.model.AirQuality;
import it.unimib.greenway.model.Result;
import it.unimib.greenway.model.StatusChallenge;
import it.unimib.greenway.model.User;

public class UserRepository implements IUserRepository, UserResponseCallback, AirQualityCallBack {


    private final MutableLiveData<Result> userMutableLiveData;
    private final BaseUserAuthenticationRemoteDataSource userRemoteDataSource;
    private final BaseUserDataRemoteDataSource userDataRemoteDataSource;
    private final BaseAirQualityLocalDataSource airQualityLocalDataSource;


    public UserRepository(BaseUserAuthenticationRemoteDataSource userRemoteDataSource, BaseUserDataRemoteDataSource userDataRemoteDataSource
                            , BaseAirQualityLocalDataSource airQualityLocalDataSource) {
        this.userRemoteDataSource = userRemoteDataSource;
        this.airQualityLocalDataSource = airQualityLocalDataSource;
        this.userDataRemoteDataSource = userDataRemoteDataSource;
        this.userRemoteDataSource.setUserResponseCallback(this);
        this.userDataRemoteDataSource.setUserResponseCallback(this);
        this.airQualityLocalDataSource.setAirQualityCallBack(this);
        userMutableLiveData = new MutableLiveData<>();
    }


    @Override
    public MutableLiveData<Result> getGoogleUser(String idToken) {
        signInWithGoogle(idToken);
        return userMutableLiveData;
    }

    @Override
    public void signInWithGoogle(String token) {
        userRemoteDataSource.signInWithGoogle(token);
    }

    @Override
    public MutableLiveData<Result> registerUser(String nome, String cognome, String email, String password, List<StatusChallenge> statusChallengeList) {
        signUp(nome, cognome, email, password, statusChallengeList);
        return userMutableLiveData;
    }

    @Override
    public void signUp(String nome, String cognome, String email, String password, List<StatusChallenge> statusChallengeList) {
        userRemoteDataSource.signUp(nome, cognome, email, password, statusChallengeList);
    }

    @Override
    public MutableLiveData<Result> loginUser(String email, String password) {
        login(email, password);
        return userMutableLiveData;
    }

    @Override
    public void login(String email, String password) {
        userRemoteDataSource.login(email, password);
    }

    @Override
    public MutableLiveData<Result> getUserData(String email, String password) {
        getUserCredential(email, password);
        return userMutableLiveData;
    }


    @Override
    public void getUserCredential(String email, String password) {
        userRemoteDataSource.getUserCredential(email, password);
    }

    @Override
    public MutableLiveData<Result> getUser(String idToken) {
        userDataRemoteDataSource.getUserInfo(idToken);
        return userMutableLiveData;
    }



    @Override
    public void onSuccessFromAuthentication(User user) {
        if(user != null){
            userDataRemoteDataSource.saveUserData(user);
        }
    }

    @Override
    public void onFailureFromAuthentication(String message) {
        Result.Error result = new Result.Error(message);
        userMutableLiveData.postValue(result);
    }

    @Override
    public void onSuccessFromRemoteDatabase(User user) {
        Result.UserResponseSuccess result = new Result.UserResponseSuccess(user);
        userMutableLiveData.postValue(result);
    }

    @Override
    public void onFailureFromRemoteDatabase(String message) {
        Result.Error result = new Result.Error(message);
        userMutableLiveData.postValue(result);
    }

    @Override
    public void onSuccessGettingFriendsFromRemoteDatabase(List<User> friends) {
        Result.FriendResponseSuccess result = new Result.FriendResponseSuccess(friends);
        userMutableLiveData.postValue(result);
    }

    @Override
    public void onFailureGettingFriendsFromRemoteDatabase(String message) {
        Result.Error result = new Result.Error(message);
        userMutableLiveData.postValue(result);
    }

    @Override
    public void onSuccessGettingAllFriendsFromRemoteDatabase(List<User> users) {
        Result.AllUserResponseSuccess result = new Result.AllUserResponseSuccess(users);
        userMutableLiveData.postValue(result);
    }

    @Override
    public void onFailureGettingAllFriendsFromRemoteDatabase(String message) {
        Result.Error result = new Result.Error(message);
        userMutableLiveData.postValue(result);
    }

    @Override
    public void onSuccessLogout() {
        Result.UserResponseSuccess result = new Result.UserResponseSuccess(new User("", "", "", "", "", "", 0,0,0,0,0,0,0,0,0, null, null, 0));
        userMutableLiveData.postValue(result);
    }

    @Override
    public User getLoggedUser() {
        return userRemoteDataSource.getLoggedUser();
    }

    @Override
    public MutableLiveData<Result> updateCo2Saved(String idToken, String transportType, double co2Saved, double kmTravel) {
        userDataRemoteDataSource.updateCo2Saved(idToken, transportType, co2Saved, kmTravel);
        return userMutableLiveData;
    }

    @Override
    public MutableLiveData<Result> updateCo2Car(String idToken, double co2Car) {
        userDataRemoteDataSource.updateCo2Car(idToken, co2Car);
        return userMutableLiveData;
    }

    @Override
    public MutableLiveData<Result> logout() {
        userRemoteDataSource.logout();
        return userMutableLiveData;
    }

    @Override
    public MutableLiveData<Result> changePassword(String token, String newPw, String oldPw) {
        userDataRemoteDataSource.changePassword(token, newPw, oldPw);
        return userMutableLiveData;
    }

    @Override
    public MutableLiveData<Result> changePhoto(String token, String imageBitmap) {
        userDataRemoteDataSource.changePhoto(token, imageBitmap);
        return userMutableLiveData;
    }

    @Override
    public MutableLiveData<Result> getFriends(String idToken) {
        userDataRemoteDataSource.getFriends(idToken);
        return userMutableLiveData;
    }

    @Override
    public MutableLiveData<Result> addFriend(String idToken, String friendId) {
        userDataRemoteDataSource.addFriend(idToken, friendId);
        return userMutableLiveData;
    }

    @Override
    public MutableLiveData<Result> removeFriend(String idToken, String friendId) {
        userDataRemoteDataSource.removeFriend(idToken, friendId);
        return userMutableLiveData;
    }

    @Override
    public MutableLiveData<Result> getAllUsers(String idToken) {
        userDataRemoteDataSource.getAllUsers(idToken);
        return userMutableLiveData;
    }


    @Override
    public void onSuccessFromLocal(List<AirQuality> Response) {

    }

    @Override
    public void onFailureFromLocal(Exception exception) {

    }

    @Override
    public void onSuccessFromRemote(List<AirQuality> Response) {

    }

    @Override
    public void onFailureFromRemote(Exception exception) {

    }
}
