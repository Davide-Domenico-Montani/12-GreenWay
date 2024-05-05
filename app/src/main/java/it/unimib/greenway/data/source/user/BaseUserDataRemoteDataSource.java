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

    public abstract void updateCo2Saved(String idToken,String transportType,double co2Saved,double kmTravel);
    public abstract void updateKmTravelled(String idToken,String transportType,double kmTravel);
    public abstract void changePassword(String token,String newPw,String oldPw);
    public abstract void changePhoto(String token,String imageBitmap);
}
