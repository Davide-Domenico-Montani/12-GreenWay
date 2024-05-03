package it.unimib.greenway.data.source.user;

import it.unimib.greenway.data.repository.user.UserResponseCallback;
import it.unimib.greenway.model.User;

public abstract class BaseUserAuthenticationRemoteDataSource {
    protected UserResponseCallback userResponseCallback;

    public void setUserResponseCallback(UserResponseCallback userResponseCallback) {
        this.userResponseCallback = userResponseCallback;
    }

    public abstract void signInWithGoogle(String idToken);
    public abstract void signUp(String nome, String cognome, String email, String password);
    public abstract void login(String email, String password);
    public abstract void getUserCredential(String email, String password);
    public abstract User getLoggedUser();
    public abstract void logout();



}
