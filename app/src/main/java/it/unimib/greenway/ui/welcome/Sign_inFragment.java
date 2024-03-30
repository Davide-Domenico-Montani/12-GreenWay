package it.unimib.greenway.ui.welcome;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import it.unimib.greenway.R;


public class Sign_inFragment extends Fragment {

    TextInputEditText editTextname, editTextSurname, editTextEmail, editTextPassword, editTextRepeatPassword;
    TextInputLayout textInputLayoutName, textInputLayoutSurname, textInputLayoutEmail, textInputLayoutPassword, textInputLayoutRepeatPassword;
    Button btnConfirmRegister;



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
                if(TextUtils.isEmpty(editTextname.getText().toString())){
                    textInputLayoutName.setError(getString(R.string.insert_name));
                }

                if(TextUtils.isEmpty(editTextSurname.getText().toString())){
                    textInputLayoutSurname.setError(getString(R.string.insert_surname));
                }

                if(TextUtils.isEmpty(editTextEmail.getText().toString())){
                    textInputLayoutEmail.setError(getString(R.string.insert_email));
                }

                if(TextUtils.isEmpty(editTextPassword.getText().toString())){
                    textInputLayoutPassword.setError(getString(R.string.insert_password));
                }

                if(TextUtils.isEmpty(editTextRepeatPassword.getText().toString())){
                    textInputLayoutRepeatPassword.setError(getString(R.string.insert_password));
                }
            }
        });
    }
}
