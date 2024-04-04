package it.unimib.greenway.data.repository.user;

import androidx.lifecycle.MutableLiveData;

import it.unimib.greenway.data.source.user.BaseUserAuthenticationRemoteDataSource;
import it.unimib.greenway.data.source.user.BaseUserDataRemoteDataSource;
import it.unimib.greenway.model.Result;
import it.unimib.greenway.model.User;

public class UserRepository implements IUserRepository, UserResponseCallback{


    private final MutableLiveData<Result> userMutableLiveData;
    private final BaseUserAuthenticationRemoteDataSource userRemoteDataSource;
    private final BaseUserDataRemoteDataSource userDataRemoteDataSource;

    public UserRepository(BaseUserAuthenticationRemoteDataSource userRemoteDataSource, BaseUserDataRemoteDataSource userDataRemoteDataSource) {
        this.userRemoteDataSource = userRemoteDataSource;
        this.userDataRemoteDataSource = userDataRemoteDataSource;
        this.userRemoteDataSource.setUserResponseCallback(this);
        this.userDataRemoteDataSource.setUserResponseCallback(this);
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
    public MutableLiveData<Result> registerUser(String nome, String cognome, String email, String password) {
        signUp(nome, cognome, email, password);
        return userMutableLiveData;
    }

    @Override
    public void signUp(String nome, String cognome, String email, String password) {
        userRemoteDataSource.signUp(nome, cognome, email, password);
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
    public void onSuccessFromAuthentication(User user) {
        if(user != null){
            userDataRemoteDataSource.saveUserData(user);
        }
    }

    @Override
    public void onFailureFromAuthentication(String message) {

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
}
