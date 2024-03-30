package it.unimib.greenway.data.repository.user;

import it.unimib.greenway.model.User;

public interface UserResponseCallback {
    void onSuccessFromAuthentication(User user);
    void onFailureFromAuthentication(String message);
}
