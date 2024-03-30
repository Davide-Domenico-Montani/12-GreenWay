package it.unimib.greenway.data.source.user;

import it.unimib.greenway.data.repository.user.UserResponseCallback;

public class BaseUserDataRemoteDataSource {
    protected UserResponseCallback userResponseCallback;

    public void setUserResponseCallback(UserResponseCallback userResponseCallback) {
        this.userResponseCallback = userResponseCallback;
    }

}
