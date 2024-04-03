package it.unimib.greenway.ui.welcome;

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

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.apache.commons.validator.routines.EmailValidator;

import it.unimib.greenway.R;
import it.unimib.greenway.model.Result;
import it.unimib.greenway.ui.UserViewModel;


public class Sign_inFragment extends Fragment {

    TextInputEditText editTextname, editTextSurname, editTextEmail, editTextPassword, editTextRepeatPassword;
    TextInputLayout textInputLayoutName, textInputLayoutSurname, textInputLayoutEmail, textInputLayoutPassword, textInputLayoutRepeatPassword;
    Button btnConfirmRegister;

    private UserViewModel userViewModel;

    public Sign_inFragment() {
        // Required empty public constructor
    }


    public static Sign_inFragment newInstance(String param1, String param2) {
        Sign_inFragment fragment = new Sign_inFragment();


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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signin, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editTextname = view.findViewById(R.id.editTextName);
        editTextSurname = view.findViewById(R.id.editTextSurname);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        editTextRepeatPassword = view.findViewById(R.id.editTextRepeatPassword);
        btnConfirmRegister = view.findViewById(R.id.buttonRegister);

        textInputLayoutName = view.findViewById(R.id.textInputLayoutName);
        textInputLayoutSurname = view.findViewById(R.id.textInputLayoutSurname);
        textInputLayoutEmail = view.findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = view.findViewById(R.id.textInputLayoutPassword);
        textInputLayoutRepeatPassword = view.findViewById(R.id.textInputLayoutRepeatPassword);

        btnConfirmRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValid = true;

                if(TextUtils.isEmpty(editTextname.getText().toString())){
                    textInputLayoutName.setError(getString(R.string.insert_name));
                    isValid = false;
                }

                if(TextUtils.isEmpty(editTextSurname.getText().toString())){
                    textInputLayoutSurname.setError(getString(R.string.insert_surname));
                    isValid = false;
                }

                if(TextUtils.isEmpty(editTextEmail.getText().toString())){
                    textInputLayoutEmail.setError(getString(R.string.insert_email));
                    isValid = false;
                }

                if(TextUtils.isEmpty(editTextPassword.getText().toString())){
                    textInputLayoutPassword.setError(getString(R.string.insert_password));
                    isValid = false;
                }

                if(TextUtils.isEmpty(editTextRepeatPassword.getText().toString())){
                    textInputLayoutRepeatPassword.setError(getString(R.string.insert_password));
                    isValid = false;
                }

                if(!editTextPassword.getText().toString().equals(editTextRepeatPassword.getText().toString())) {
                    textInputLayoutRepeatPassword.setError(getString(R.string.password_not_match));
                    textInputLayoutPassword.setError(getString(R.string.password_not_match));
                    isValid = false;
                }

                if(!isValidEmail(editTextEmail.getText().toString()) && !TextUtils.isEmpty(editTextEmail.getText().toString())) {
                    textInputLayoutEmail.setError(getString(R.string.email_not_valid));
                    isValid = false;
                }

                if(isValid){
                    String Nome = editTextname.getText().toString();
                    String Cognome = editTextSurname.getText().toString();
                    String Email = editTextEmail.getText().toString();
                    String Password = editTextPassword.getText().toString();
                    userViewModel.registerUserMutableLiveData(Nome, Cognome, Email, Password).observe(
                            getViewLifecycleOwner(), result -> {
                                if (result.isSuccessUser()) {
                                    //userViewModel.setAuthenticationError(false);
                                    //Navigation.findNavController(view).navigate(
                                    //        R.id.action_registerFragment_to_loginFragment);
                                    Log.d("Register", "Success");
                                } else {
                                    //userViewModel.setAuthenticationError(true);
                                    //progressIndicator.setVisibility(View.GONE);
                                    /*Snackbar.make(requireActivity().findViewById(android.R.id.content),
                                            getErrorMessage(((Result.Error) result).getMessage()),
                                            Snackbar.LENGTH_SHORT).show();*/
                                    Log.d("Register", "Failure");
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
