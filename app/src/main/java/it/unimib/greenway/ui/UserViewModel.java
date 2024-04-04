package it.unimib.greenway.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import it.unimib.greenway.data.repository.user.IUserRepository;
import it.unimib.greenway.model.Result;

public class UserViewModel extends ViewModel {
    private static final String TAG = UserViewModel.class.getSimpleName();
    private final IUserRepository userRepository;

    private MutableLiveData<Result> userMutableLiveData;

    public UserViewModel(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public MutableLiveData<Result> getGoogleUserMutableLiveData(String token) {
        if (userMutableLiveData == null) {
            getGoogleUserData(token);
        }
        return userMutableLiveData;
    }

    private void getGoogleUserData(String token) {
        userMutableLiveData = userRepository.getGoogleUser(token);
    }

    public MutableLiveData<Result> registerUserMutableLiveData(String nome, String cognome, String email, String password) {
        if (userMutableLiveData == null) {
            registerUser(nome, cognome, email, password);
        }
        return userMutableLiveData;
    }

    public void registerUser(String nome, String cognome, String email, String password) {
        userMutableLiveData = userRepository.registerUser(nome, cognome, email, password);
    }

    public MutableLiveData<Result> loginUserMutableLiveData(String email, String password) {
        if (userMutableLiveData == null) {
            loginUser(email, password);
        }
        return userMutableLiveData;
    }

    public void loginUser(String email, String password) {
        userMutableLiveData = userRepository.loginUser(email, password);
    }

}
