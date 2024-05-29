package it.unimib.greenway.ui.main;

import static it.unimib.greenway.util.Constants.BENZINA_PARAMETER;
import static it.unimib.greenway.util.Constants.CO2_PRODUCTION_CAR_DIESEL;
import static it.unimib.greenway.util.Constants.CO2_PRODUCTION_CAR_ELETTRIC;
import static it.unimib.greenway.util.Constants.CO2_PRODUCTION_CAR_GASOLINE;
import static it.unimib.greenway.util.Constants.CO2_PRODUCTION_CAR_GPL;
import static it.unimib.greenway.util.Constants.CO2_PRODUCTION_CAR_METHANE;
import static it.unimib.greenway.util.Constants.DIESEL_PARAMETER;
import static it.unimib.greenway.util.Constants.ELECTRIC_PARAMETER;
import static it.unimib.greenway.util.Constants.ERROR_RETRIEVING_USER_INFO;
import static it.unimib.greenway.util.Constants.GPL_PARAMETER;
import static it.unimib.greenway.util.Constants.METANO_PARAMETER;
import static it.unimib.greenway.util.Constants.NEW_PASSWORD_ERROR;
import static it.unimib.greenway.util.Constants.OLD_PASSWORD_ERROR;
import static it.unimib.greenway.util.Constants.PASSWORD_ERROR_GOOGLE;
import static it.unimib.greenway.util.Constants.PERSONALIZED_PARAMETER;

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

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import it.unimib.greenway.R;
import it.unimib.greenway.data.repository.user.IUserRepository;
import it.unimib.greenway.model.Result;
import it.unimib.greenway.model.User;
import it.unimib.greenway.ui.UserViewModel;
import it.unimib.greenway.ui.UserViewModelFactory;
import it.unimib.greenway.util.ConverterUtil;
import it.unimib.greenway.util.ServiceLocator;
import it.unimib.greenway.util.SharedPreferencesUtil;


public class SettingFragment extends Fragment {

    private ImageButton backButton;
    private FragmentManager fragmentManager;
    private Button changePwButton;
    private TextInputLayout oldPw, newPw, repeatPw;
    private UserViewModel userViewModel;
    private SharedPreferencesUtil sharedPreferencesUtil;
    private Button changePhoto;
    private ConverterUtil converterUtil;
    private ArrayList<String> engineTypes;
    private double myEngineProduction;
    private TextInputLayout textInputCo2Car;
    private Button addCarCo2;
    private ArrayAdapter<String> arrayAdapter_engine;
    private TextInputLayout menu;
    private AutoCompleteTextView autoCompleteTextView;
    private boolean personalized;

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
        personalized = false;


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        oldPw = view.findViewById(R.id.textFieldOldPw);
        newPw = view.findViewById(R.id.textFieldNewPw);
        repeatPw = view.findViewById(R.id.textFieldRepeatPw);
        changePwButton = view.findViewById(R.id.buttonConfirm);
        changePhoto = view.findViewById(R.id.buttonChangePhoto);
        fragmentManager = requireActivity().getSupportFragmentManager();
        //menù
        menu =view.findViewById(R.id.menu);
        autoCompleteTextView = view.findViewById(R.id.list_items);
        addCarCo2 = view.findViewById(R.id.addCarCo2);
        textInputCo2Car = view.findViewById(R.id.text_input_co2Car);
        backButton = requireActivity().findViewById(R.id.backButton);
        backButton.setVisibility(View.VISIBLE);



        engineTypes = new ArrayList<>();
        engineTypes.add(BENZINA_PARAMETER);
        engineTypes.add(DIESEL_PARAMETER);
        engineTypes.add(GPL_PARAMETER);
        engineTypes.add(METANO_PARAMETER);
        engineTypes.add(ELECTRIC_PARAMETER);
        engineTypes.add(PERSONALIZED_PARAMETER);



        arrayAdapter_engine = new ArrayAdapter<>(requireActivity().getApplicationContext(), R.layout.list_items, engineTypes);
        autoCompleteTextView.setAdapter(arrayAdapter_engine);


        userViewModel.getUserDataMutableLiveData(userViewModel.getLoggedUser().getUserId())
                .observe(getViewLifecycleOwner(), result -> {
                    if (result.isSuccessUser()) {
                        User user = ((Result.UserResponseSuccess) result).getData();
                        myEngineProduction = user.getCo2Car();

                        switch ((int)myEngineProduction){

                                case -1:
                                    autoCompleteTextView.setText(engineTypes.get(0), false);
                                    textInputCo2Car.getEditText().setText(String.valueOf(CO2_PRODUCTION_CAR_GASOLINE));
                                    break;
                                case -2:
                                    autoCompleteTextView.setText(engineTypes.get(1), false);
                                    textInputCo2Car.getEditText().setText(String.valueOf(CO2_PRODUCTION_CAR_DIESEL));
                                    break;
                                case -3:
                                    autoCompleteTextView.setText(engineTypes.get(2), false);
                                    textInputCo2Car.getEditText().setText(String.valueOf(CO2_PRODUCTION_CAR_GPL));
                                    break;
                                case -4:
                                    autoCompleteTextView.setText(engineTypes.get(3), false);
                                    textInputCo2Car.getEditText().setText(String.valueOf(CO2_PRODUCTION_CAR_METHANE));
                                    break;
                                case -5:
                                    autoCompleteTextView.setText(engineTypes.get(4), false);
                                    textInputCo2Car.getEditText().setText(String.valueOf(CO2_PRODUCTION_CAR_ELETTRIC));
                                    break;
                                default:
                                    autoCompleteTextView.setText(engineTypes.get(5), false);
                                    textInputCo2Car.getEditText().setText(String.valueOf(myEngineProduction));
                                    break;
                        }
                    } else {
                            Snackbar.make(requireActivity().findViewById(android.R.id.content),
                                    getErrorMessage(((Result.Error) result).getMessage()),
                                    Snackbar.LENGTH_SHORT).show();
                    }
                });
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedEngine = parent.getItemAtPosition(position).toString();
                switch (selectedEngine) {
                    case BENZINA_PARAMETER:
                        textInputCo2Car.setEnabled(false);
                        myEngineProduction = -1;
                        textInputCo2Car.getEditText().setText(String.valueOf(CO2_PRODUCTION_CAR_GASOLINE));
                        personalized = false;
                        break;

                    case DIESEL_PARAMETER:
                        textInputCo2Car.setEnabled(false);
                        myEngineProduction = -2;
                        textInputCo2Car.getEditText().setText(String.valueOf(CO2_PRODUCTION_CAR_DIESEL));
                        personalized = false;
                        break;

                    case GPL_PARAMETER:
                        textInputCo2Car.setEnabled(false);
                        myEngineProduction = -3;
                        textInputCo2Car.getEditText().setText(String.valueOf(CO2_PRODUCTION_CAR_GPL));
                        personalized = false;
                        break;

                    case METANO_PARAMETER:
                        textInputCo2Car.setEnabled(false);
                        myEngineProduction = -4;
                        textInputCo2Car.getEditText().setText(String.valueOf(CO2_PRODUCTION_CAR_METHANE));
                        personalized = false;
                        break;

                    case ELECTRIC_PARAMETER:
                        textInputCo2Car.setEnabled(false);
                        myEngineProduction = -5;
                        textInputCo2Car.getEditText().setText(String.valueOf(CO2_PRODUCTION_CAR_ELETTRIC));
                        personalized = false;
                        break;

                    case PERSONALIZED_PARAMETER:
                        textInputCo2Car.setEnabled(true);
                        textInputCo2Car.getEditText().setText(String.valueOf(0.0));
                        personalized = true;
                        break;
                }
            }
        });

        addCarCo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(personalized) {
                    String input = textInputCo2Car.getEditText().getText().toString();
                    myEngineProduction = Double.parseDouble(input);
                }
                try {

                    if(myEngineProduction != 0) {
                        userViewModel.updateCo2CarMutableLiveData(userViewModel.getLoggedUser().getUserId(), myEngineProduction)
                                .observe(getViewLifecycleOwner(), result -> {
                                    if (result.isSuccessUser()) {
                                        Snackbar.make(view, getString(R.string.co2_car_emission_change), Snackbar.LENGTH_SHORT).show();
                                    } else {
                                        Snackbar.make(requireActivity().findViewById(android.R.id.content),
                                                getErrorMessage(((Result.Error) result).getMessage()),
                                                Snackbar.LENGTH_SHORT).show();
                                    }
                                });
                    }else{
                        Snackbar.make(requireView(), R.string.car_production_notLoad, Snackbar.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    Snackbar.make(requireView(), R.string.car_production_number_error, Snackbar.LENGTH_SHORT).show();
                }

            }
        });



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

                if(oldPassword.isEmpty()) {
                    oldPw.setError(getString(R.string.empty_field));
                }
                if(newPassword.isEmpty()) {
                    newPw.setError(getString(R.string.empty_field));
                }

                if(repeatPassword.isEmpty()) {
                    repeatPw.setError(getString(R.string.empty_field));
                }

                if(!newPassword.equals(repeatPassword)) {
                    repeatPw.setError(getString(R.string.error_password_match));
                }else{
                    oldPw.setErrorEnabled(false);
                    newPw.setErrorEnabled(false);
                    repeatPw.setErrorEnabled(false);
                    userViewModel.changePasswordMutableLiveData(userViewModel.getLoggedUser().getUserId(), newPassword, oldPassword)
                            .observe(getViewLifecycleOwner(), result -> {
                                if (result.isSuccessUser()) {
                                    Snackbar.make(view, getString(R.string.password_changed), Snackbar.LENGTH_SHORT).show();
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
            case ERROR_RETRIEVING_USER_INFO:
                return requireActivity().getString(R.string.error_retrieving_user_info);
            default:
                return requireActivity().getString(R.string.unexpected_error);
        }
    }
}