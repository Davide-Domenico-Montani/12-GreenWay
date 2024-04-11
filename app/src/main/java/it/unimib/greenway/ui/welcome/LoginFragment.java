package it.unimib.greenway.ui.welcome;

import static it.unimib.greenway.util.Constants.EMAIL_ADDRESS;
import static it.unimib.greenway.util.Constants.ENCRYPTED_DATA_FILE_NAME;
import static it.unimib.greenway.util.Constants.ENCRYPTED_SHARED_PREFERENCES_FILE_NAME;
import static it.unimib.greenway.util.Constants.ID;
import static it.unimib.greenway.util.Constants.PASSWORD;
import static it.unimib.greenway.util.Constants.SHARED_PREFERENCES_FILE_NAME;
import static it.unimib.greenway.util.Constants.SHARED_PREFERENCES_FIRST_LOADING;
import static it.unimib.greenway.util.Constants.USE_NAVIGATION_COMPONENT;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.apache.commons.validator.routines.EmailValidator;

import java.io.IOException;
import java.security.GeneralSecurityException;

import it.unimib.greenway.R;
import it.unimib.greenway.model.Result;
import it.unimib.greenway.model.User;
import it.unimib.greenway.ui.UserViewModel;
import it.unimib.greenway.ui.main.MainActivity;
import it.unimib.greenway.util.DataEncryptionUtil;
import it.unimib.greenway.util.SharedPreferencesUtil;


public class LoginFragment extends Fragment {

    Button btnConfirmLogin;
    TextInputEditText editTextEmail, editTextPassword;
    TextInputLayout textInputLayoutEmail, textInputLayoutPassword;
    ProgressBar progressIndicator;
    private UserViewModel userViewModel;
    private DataEncryptionUtil dataEncryptionUtil;
    private SharedPreferencesUtil sharedPreferencesUtil;

    public LoginFragment() {
        // Required empty public constructor
    }


    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnConfirmLogin = view.findViewById(R.id.buttonLogin);
        progressIndicator = view.findViewById(R.id.progressIndicator);
        btnConfirmLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValid = true;
                editTextEmail = view.findViewById(R.id.editTextEmail);
                editTextPassword = view.findViewById(R.id.editTextPassword);
                textInputLayoutEmail = view.findViewById(R.id.textInputLayoutEmail);
                textInputLayoutPassword = view.findViewById(R.id.textInputLayoutPassword);

                if(TextUtils.isEmpty(editTextEmail.getText().toString())){
                    textInputLayoutEmail.setError(getString(R.string.insert_email));
                    isValid = false;
                }

                if(TextUtils.isEmpty(editTextPassword.getText().toString())){
                    textInputLayoutPassword.setError(getString(R.string.insert_password));
                    isValid = false;
                }

                if(!isValidEmail(editTextEmail.getText().toString()) && !TextUtils.isEmpty(editTextEmail.getText().toString())) {
                    textInputLayoutEmail.setError(getString(R.string.email_not_valid));
                    isValid = false;
                }

                if(isValid){
                    String Email = editTextEmail.getText().toString();
                    String Password = editTextPassword.getText().toString();
                    progressIndicator.setVisibility(View.VISIBLE);

                    userViewModel.loginUserMutableLiveData(Email, Password).observe(
                            getViewLifecycleOwner(), result -> {
                                if (result.isSuccessUser()) {
                                    User user = ((Result.UserResponseSuccess) result).getData();
                                    Log.d("LoginFragment", "User: " + user.getUserId() + " " + user.getEmail());
                                    Intent intent = new Intent(getActivity(), MainActivity.class);
                                    startActivity(intent);
                                    saveLoginData(user.getUserId(), Email, Password);
                                    //userViewModel.setAuthenticationError(false);
                                    retrieveUserInformationAndStartActivity(user, R.id.action_loginFragment_to_mainActivity);

                                } else {
                                    //userViewModel.setAuthenticationError(true);
                                    progressIndicator.setVisibility(View.GONE);
                                    //Snackbar.make(requireActivity().findViewById(android.R.id.content),
                                      //      getErrorMessage(((Result.Error) result).getMessage()),
                                        //    Snackbar.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }

    public boolean isValidEmail(String email) {
        return EmailValidator.getInstance().isValid(email);
    }
    private void retrieveUserInformationAndStartActivity(User user, int destination) {


        userViewModel.getUserDataMutableLiveData(user.getUserId()).observe(
                getViewLifecycleOwner(), userDataRetrivalResul -> {

                    startActivityBasedOnCondition(MainActivity.class, destination);
                }
        );
    }

    private void startActivityBasedOnCondition(Class<?> destinationActivity, int destination) {
        if (USE_NAVIGATION_COMPONENT) {
            Navigation.findNavController(requireView()).navigate(destination);
        } else {
            Intent intent = new Intent(requireContext(), destinationActivity);
            startActivity(intent);
        }
        requireActivity().finish();
    }
    private void saveLoginData(String id,String email, String password) {
        dataEncryptionUtil = new DataEncryptionUtil(requireContext());
        sharedPreferencesUtil = new SharedPreferencesUtil(requireActivity().getApplication());
        sharedPreferencesUtil.writeBooleanData(ENCRYPTED_SHARED_PREFERENCES_FILE_NAME,
                SHARED_PREFERENCES_FIRST_LOADING, true);
        try {
            dataEncryptionUtil.writeSecretDataWithEncryptedSharedPreferences(
                    ENCRYPTED_SHARED_PREFERENCES_FILE_NAME, ID, id);
            dataEncryptionUtil.writeSecretDataWithEncryptedSharedPreferences(
                    ENCRYPTED_SHARED_PREFERENCES_FILE_NAME, EMAIL_ADDRESS, email);

            dataEncryptionUtil.writeSecretDataWithEncryptedSharedPreferences(
                    ENCRYPTED_SHARED_PREFERENCES_FILE_NAME, PASSWORD, password);

            sharedPreferencesUtil.writeBooleanData(SHARED_PREFERENCES_FILE_NAME,
                    SHARED_PREFERENCES_FIRST_LOADING, true);
            dataEncryptionUtil.writeSecreteDataOnFile(ENCRYPTED_DATA_FILE_NAME,
                    id.concat(":").concat(email).concat(":").concat(password));
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
    }
}