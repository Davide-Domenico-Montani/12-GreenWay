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

    public abstract void updateCo2Car(String idToken,double co2Car);
    public abstract void updateKmTravelled(String idToken,String transportType, String parameterCo2, double co2Database,double kmTravel);
    public abstract void changePassword(String token,String newPw,String oldPw);
    public abstract void changePhoto(String token,String imageBitmap);
    public abstract void updateStatusChallenge(String idToken, String transportType, String parameterco2 , String parameterKm, double co2Database, double kmDatabase);

    public abstract void updateUserPoint(String idToken, int point);
    public abstract void getFriends(String idToken);
    public abstract void addFriend(String idToken, String friendId);
    public abstract  void removeFriend(String idToken, String friendId);
    public abstract void getAllUsers(String idToken);
}
