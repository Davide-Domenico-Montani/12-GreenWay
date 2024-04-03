package it.unimib.greenway.ui.welcome;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.apache.commons.validator.routines.EmailValidator;

import it.unimib.greenway.R;
import it.unimib.greenway.model.Result;
import it.unimib.greenway.model.User;
import it.unimib.greenway.ui.UserViewModel;


public class LoginFragment extends Fragment {

    Button btnConfirmLogin;
    TextInputEditText editTextEmail, editTextPassword;
    TextInputLayout textInputLayoutEmail, textInputLayoutPassword;
    private UserViewModel userViewModel;
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
                    userViewModel.loginUserMutableLiveData(Email, Password).observe(
                            getViewLifecycleOwner(), result -> {
                                if (result.isSuccessUser()) {
                                    User user = ((Result.UserResponseSuccess) result).getData();
                                    Log.d("LoginFragment", "User: " + user.getUserId() + " " + user.getEmail());
                                    //saveLoginData(user.getUserId(), Email, Password);
                                    //userViewModel.setAuthenticationError(false);
                                    //retrieveUserInformationAndStartActivity(user, R.id.action_loginFragment_to_mainActivity);

                                } else {
                                    //userViewModel.setAuthenticationError(true);
                                    //progressIndicator.setVisibility(View.GONE);
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
}