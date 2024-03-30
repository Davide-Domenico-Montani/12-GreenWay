package it.unimib.greenway.data.repository.user;

import androidx.lifecycle.MutableLiveData;

import it.unimib.greenway.data.source.user.BaseUserAuthenticationRemoteDataSource;
import it.unimib.greenway.model.Result;
import it.unimib.greenway.model.User;

public class UserRepository implements IUserRepository, UserResponseCallback{


    private final MutableLiveData<Result> userMutableLiveData;
    private final BaseUserAuthenticationRemoteDataSource userRemoteDataSource;

    public UserRepository(BaseUserAuthenticationRemoteDataSource userRemoteDataSource) {
        this.userRemoteDataSource = userRemoteDataSource;
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
    public void onSuccessFromAuthentication(User user) {

    }

    @Override
    public void onFailureFromAuthentication(String message) {

    }
}
