package it.unimib.greenway.data.repository.user;

import java.util.List;

import it.unimib.greenway.model.User;

public interface UserResponseCallback {
    void onSuccessFromAuthentication(User user);
    void onFailureFromAuthentication(String message);
    void onSuccessFromRemoteDatabase(User user);
    void onFailureFromRemoteDatabase(String message);
    void onSuccessGettingFriendsFromRemoteDatabase(List<User> friends);
    void onFailureGettingFriendsFromRemoteDatabase(String message);
    void onSuccessLogout();
}
