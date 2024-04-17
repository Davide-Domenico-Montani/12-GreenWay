package it.unimib.greenway.ui.welcome;

import static it.unimib.greenway.util.Constants.ENCRYPTED_DATA_FILE_NAME;
import static it.unimib.greenway.util.Constants.ENCRYPTED_SHARED_PREFERENCES_FILE_NAME;
import static it.unimib.greenway.util.Constants.INVALID_CREDENTIALS_ERROR;
import static it.unimib.greenway.util.Constants.INVALID_USER_ERROR;
import static it.unimib.greenway.util.Constants.USE_NAVIGATION_COMPONENT;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.security.GeneralSecurityException;

import it.unimib.greenway.R;
import it.unimib.greenway.data.repository.user.IUserRepository;
import it.unimib.greenway.model.Result;
import it.unimib.greenway.model.User;
import it.unimib.greenway.ui.UserViewModel;
import it.unimib.greenway.ui.UserViewModelFactory;
import it.unimib.greenway.ui.main.MainActivity;
import it.unimib.greenway.util.DataEncryptionUtil;
import it.unimib.greenway.util.ServiceLocator;

public class WelcomeActivity extends AppCompatActivity {

    private DataEncryptionUtil dataEncryptionUtil;
    private UserViewModel userViewModel;
    ProgressBar progressIndicator;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome);

        IUserRepository userRepository = ServiceLocator.getInstance().
                getUserRepository(getApplication());
        userViewModel = new ViewModelProvider(
                this,
                new UserViewModelFactory(userRepository)).get(UserViewModel.class);
        dataEncryptionUtil = new DataEncryptionUtil(this);
        try {
            // Leggi i dati di login dal file

            String storedLoginData = dataEncryptionUtil.readSecretDataOnFile(ENCRYPTED_DATA_FILE_NAME);
            Log.d("test", storedLoginData);
            if(storedLoginData.isEmpty()) {
                Log.d("test", "è null");
            }

            if (storedLoginData != null && !storedLoginData.isEmpty()) {

                String[] loginInfo = storedLoginData.split(":");
                String storedEmail = loginInfo[1];
                String storedPassword = loginInfo[2];
                Log.d("test", storedEmail);
                Log.d("test", storedPassword);
                if(!storedPassword.equals(".")) {
                    //Se sono presenti informazioni di login, effettua il login automatico
                    performAutoLogin(storedEmail, storedPassword);
                }else{
                    dataEncryptionUtil.deleteAll(ENCRYPTED_SHARED_PREFERENCES_FILE_NAME, ENCRYPTED_DATA_FILE_NAME);
                }
            }

        } catch (GeneralSecurityException | IOException e) {
            Log.e("Error reading login data from file", e.toString());
            e.printStackTrace();
        }
    }

    private void performAutoLogin(String storedEmail, String storedPassword) {
        progressIndicator = findViewById(R.id.progressBar);
        textView = findViewById(R.id.textViewLogin);
        textView.setVisibility(View.VISIBLE);
        progressIndicator.setVisibility(View.VISIBLE);

        userViewModel.getUserMutableLiveData(storedEmail, storedPassword).observe(
                this, result -> {
                    if (result.isSuccessUser()) {
                        User user = ((Result.UserResponseSuccess) result).getData();
                        retrieveUserInformationAndStartActivity(user, R.id.action_welcomeActivity_to_mainActivity);

                    } else {
                        progressIndicator.setVisibility(View.GONE);
                        textView.setVisibility(View.GONE);
                        Snackbar.make(this.findViewById(android.R.id.content),
                                getErrorMessage(((Result.Error) result).getMessage()),
                                Snackbar.LENGTH_SHORT).show();
                    }
                });
    }

    private void retrieveUserInformationAndStartActivity(User user, int destination) {

        userViewModel.getUserDataMutableLiveData(user.getUserId()).observe(
                this, userDataRetrivalResul -> {
                    startActivityBasedOnCondition(MainActivity.class, destination);
                }
        );
    }

    private void startActivityBasedOnCondition(Class<?> destinationActivity, int destination) {
        if (USE_NAVIGATION_COMPONENT) {
            Navigation.findNavController(this, R.id.fragment_container).navigate(destination);
        } else {
            Intent intent = new Intent(this, destinationActivity);
            startActivity(intent);
        }
        finish();
    }

    private String getErrorMessage(String errorType) {
        switch (errorType) {
            case INVALID_CREDENTIALS_ERROR:
                return getString(R.string.error_login_password_message);
            case INVALID_USER_ERROR:
                return getString(R.string.error_login_user_message);
            default:
                return getString(R.string.unexpected_error);
        }
    }
}