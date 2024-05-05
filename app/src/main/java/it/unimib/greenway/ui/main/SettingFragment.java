package it.unimib.greenway.ui.main;

import static it.unimib.greenway.util.Constants.ENCRYPTED_DATA_FILE_NAME;
import static it.unimib.greenway.util.Constants.NEW_PASSWORD_ERROR;
import static it.unimib.greenway.util.Constants.OLD_PASSWORD_ERROR;
import static it.unimib.greenway.util.Constants.PASSWORD_ERROR_GOOGLE;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import it.unimib.greenway.R;
import it.unimib.greenway.data.repository.user.IUserRepository;
import it.unimib.greenway.model.Result;
import it.unimib.greenway.ui.UserViewModel;
import it.unimib.greenway.ui.UserViewModelFactory;
import it.unimib.greenway.util.ConverterUtil;
import it.unimib.greenway.util.ServiceLocator;
import it.unimib.greenway.util.SharedPreferencesUtil;
import retrofit2.Converter;


public class SettingFragment extends Fragment {

    private ImageButton backButton;
    private FragmentManager fragmentManager;
    private Button changePwButton;
    private TextInputLayout oldPw, newPw, repeatPw;
    private UserViewModel userViewModel;
    private SharedPreferencesUtil sharedPreferencesUtil;
    private Button changePhoto;
    private ConverterUtil converterUtil;
    public SettingFragment() {
        // Required empty public constructor
    }

    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        converterUtil = new ConverterUtil();
        IUserRepository userRepository = ServiceLocator.getInstance().
                getUserRepository(requireActivity().getApplication());
        userViewModel = new ViewModelProvider(
                this,
                new UserViewModelFactory(userRepository)).get(UserViewModel.class);

        sharedPreferencesUtil = new SharedPreferencesUtil(requireActivity().getApplication());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        backButton = view.findViewById(R.id.backButton);
        oldPw = view.findViewById(R.id.textFieldOldPw);
        newPw = view.findViewById(R.id.textFieldNewPw);
        repeatPw = view.findViewById(R.id.textFieldRepeatPw);
        changePwButton = view.findViewById(R.id.buttonConfirm);
        changePhoto = view.findViewById(R.id.buttonChangePhoto);
        fragmentManager = requireActivity().getSupportFragmentManager();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.popBackStack();
            }
        });

        changePwButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPassword = oldPw.getEditText().getText().toString();
                String newPassword = newPw.getEditText().getText().toString();
                String repeatPassword = repeatPw.getEditText().getText().toString();

                if(oldPassword.isEmpty() || newPassword.isEmpty() || repeatPassword.isEmpty()){
                    oldPw.setError(getString(R.string.empty_field));
                    newPw.setError(getString(R.string.empty_field));
                    repeatPw.setError(getString(R.string.empty_field));
                }  else if(!newPassword.equals(repeatPassword)) {
                    repeatPw.setError(getString(R.string.error_password_match));
                }else{
                    userViewModel.changePasswordMutableLiveData(userViewModel.getLoggedUser().getUserId(), newPassword, oldPassword)
                            .observe(getViewLifecycleOwner(), result -> {
                                if (result.isSuccessUser()) {
                                    Snackbar.make(view, getString(R.string.password_changed), Snackbar.LENGTH_SHORT).show();
                                    //TODO: Fare check con login automatico se funziona, se non funziona bisogna carmbiare anche nelle sharedPreference
                                } else {
                                    Snackbar.make(requireActivity().findViewById(android.R.id.content),
                                            getErrorMessage(((Result.Error) result).getMessage()),
                                            Snackbar.LENGTH_SHORT).show();
                                }
                            });
                }


            }
        });

        changePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    // Se hai già il permesso CAMERA, avvia l'attività della fotocamera
                    cameraActivityResultLauncher.launch(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));

                } else {
                    // Se non hai il permesso CAMERA, richiedilo all'utente
                    requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA);
                }
            }
        });



        return view;
    }

    ActivityResultLauncher<Intent> cameraActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    userViewModel.changePhotoMutableLiveData(userViewModel.getLoggedUser().getUserId(), ConverterUtil.bitmapToString((Bitmap) result.getData().getExtras().get("data")));
                }
            });

    private final ActivityResultLauncher<String> requestCameraPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(),
            isGranted -> {
                if (isGranted) {
                    // Il permesso CAMERA è stato concesso, avvia l'attività della fotocamera
                    cameraActivityResultLauncher.launch(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
                } else {
                    // Il permesso CAMERA non è stato concesso, gestisci di conseguenza
                    Snackbar.make(requireView(), getResources().getString(R.string.camera_permission_denied), Snackbar.LENGTH_SHORT).show();
                }
            });



    private String getErrorMessage(String errorType) {
        switch (errorType) {
            case NEW_PASSWORD_ERROR:
                return requireActivity().getString(R.string.new_password_error);
            case OLD_PASSWORD_ERROR:
                return requireActivity().getString(R.string.error_oldpassword);
            case PASSWORD_ERROR_GOOGLE:
                return requireActivity().getString(R.string.error_password_google);
            default:
                return requireActivity().getString(R.string.unexpected_error);
        }
    }
}