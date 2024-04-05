package it.unimib.greenway.data.source.user;

import it.unimib.greenway.data.repository.user.UserResponseCallback;
import it.unimib.greenway.model.User;

public abstract class BaseUserDataRemoteDataSource {
    protected UserResponseCallback userResponseCallback;

    public void setUserResponseCallback(UserResponseCallback userResponseCallback) {
        this.userResponseCallback = userResponseCallback;
    }

    public abstract void saveUserData(User user);
    public abstract void getUserInfo(String idToken);
}
