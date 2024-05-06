package it.unimib.greenway.ui.main;

import static it.unimib.greenway.util.Constants.ENCRYPTED_DATA_FILE_NAME;
import static it.unimib.greenway.util.Constants.ENCRYPTED_SHARED_PREFERENCES_FILE_NAME;
import static it.unimib.greenway.util.Constants.ERROR_RETRIEVING_USER_INFO;
import static it.unimib.greenway.util.Constants.NEW_PASSWORD_ERROR;
import static it.unimib.greenway.util.Constants.OLD_PASSWORD_ERROR;
import static it.unimib.greenway.util.Constants.PASSWORD_ERROR_GOOGLE;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.snackbar.Snackbar;

import it.unimib.greenway.R;
import it.unimib.greenway.data.repository.user.IUserRepository;
import it.unimib.greenway.model.Result;
import it.unimib.greenway.model.User;
import it.unimib.greenway.ui.UserViewModel;
import it.unimib.greenway.ui.UserViewModelFactory;
import it.unimib.greenway.util.ConverterUtil;
import it.unimib.greenway.util.DataEncryptionUtil;
import it.unimib.greenway.util.ServiceLocator;


public class UserFragment extends Fragment {

    private ImageButton buttonSettings;
    private ConverterUtil converterUtil;

    private LinearProgressIndicator linearProgress;

    private ImageView userImage;
    private TextView userName;
    private UserViewModel userViewModel;
    private Button logoutButton;

    private double kmCar;
    private double kmTransit;

    private double co2Car;

    private double co2SavedCar;
    private double co2SavedTransit;
    private double co2SavedWalk;
    private TextView co2SavedCarTextView, co2SavedTransitTextView,  co2SavedWalkTextView;
    private LinearProgressIndicator co2SavedCarProgressBar, co2SavedTransitProgressBar,  co2SavedWalkProgressBar;
    DataEncryptionUtil dataEncryptionUtil;
    public UserFragment() {
        // Required empty public constructor
    }

    public static UserFragment newInstance() {
        UserFragment fragment = new UserFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IUserRepository userRepository = ServiceLocator.getInstance().
                getUserRepository(requireActivity().getApplication());
        userViewModel = new ViewModelProvider(
                this,
                new UserViewModelFactory(userRepository)).get(UserViewModel.class);

        converterUtil = new ConverterUtil();
        dataEncryptionUtil = new DataEncryptionUtil(getActivity().getApplication());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        buttonSettings = view.findViewById(R.id.settingsButton);
        // Inflate the layout for this fragment
        logoutButton = view.findViewById(R.id.buttonLogout);
        userImage = view.findViewById(R.id.userPhoto);
        userName = view.findViewById(R.id.username);
        co2SavedCarTextView = view.findViewById(R.id.co2CarTextView);
        co2SavedCarProgressBar = view.findViewById(R.id.progressCar);
         co2SavedTransitTextView = view.findViewById(R.id.co2PublicTTextView);
        co2SavedTransitProgressBar = view.findViewById(R.id.progressTransit);
         co2SavedWalkTextView = view.findViewById(R.id.co2WalkTextView);
         co2SavedWalkProgressBar = view.findViewById(R.id.progressWalk);



        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_userFragment_to_settingFragment);
            }
        });
        logoutButton.setOnClickListener(v -> {
              userViewModel.logout().observe(getViewLifecycleOwner(), result -> {
                    if (result.isSuccessUser()) {
                        dataEncryptionUtil.deleteAll(ENCRYPTED_SHARED_PREFERENCES_FILE_NAME, ENCRYPTED_DATA_FILE_NAME);
                        Navigation.findNavController(view).navigate(R.id.action_userFragment_to_welcomeActivity2);
                    } else {
                        Snackbar.make(view, requireActivity().getString(R.string.unexpected_error),
                                Snackbar.LENGTH_SHORT).show();
                    }
                });


        });
        return view;
    }
    @Override
    public void onResume(){
        super.onResume();
        userViewModel.getUserDataMutableLiveData(userViewModel.getLoggedUser().getUserId()).observe(
                getViewLifecycleOwner(), result -> {
                    if (result.isSuccessUser()) {
                        User user = ((Result.UserResponseSuccess) result).getData();
                        kmCar = user.getKmCar();
                        kmTransit = user.getKmTransit();
                        co2Car = user.getCo2Car();
                        co2SavedCar = user.getCo2SavedCar();
                        co2SavedTransit = user.getCo2SavedTransit();
                        co2SavedWalk = user.getCo2SavedWalk();
                        userName.setText(user.getName() + " " + user.getSurname());

                        //co2SavedCar
                        String format = "%." + 3 + "f";
                        String formattedString = String.format(format,co2SavedCar);

                        co2SavedCarTextView.setText(formattedString + "kg");
                         co2SavedCarProgressBar.setProgress(converterUtil.co2SavedProgressBar(co2SavedCar, co2SavedCar, co2SavedTransit, co2SavedWalk), true);

                        //co2SavedTransit

                        formattedString = String.format(format,co2SavedTransit);
                        co2SavedTransitTextView.setText(formattedString + "kg");
                        co2SavedTransitProgressBar.setProgress(converterUtil.co2SavedProgressBar(co2SavedTransit, co2SavedCar, co2SavedTransit, co2SavedWalk), true);

                        //co2SavedWalk

                        formattedString = String.format(format,co2SavedWalk);
                        co2SavedWalkTextView.setText(formattedString + "kg");
                        co2SavedWalkProgressBar.setProgress(converterUtil.co2SavedProgressBar(co2SavedWalk, co2SavedCar, co2SavedTransit, co2SavedWalk), true);

                        if(user.getPhotoUrlGoogle() != null && user.getPhotoUrl().equals("")){
                            Glide.with(requireContext())
                                    .load(user.getPhotoUrlGoogle())
                                    .placeholder(R.drawable.icon_user)
                                    .error(R.drawable.icon_user)
                                    .circleCrop()
                                    .into(userImage);
                        }else if(!user.getPhotoUrl().equals("")){
                            Glide.with(requireContext())
                                    .load(converterUtil.stringToBitmap(user.getPhotoUrl()))
                                    .error(R.drawable.icon_user)
                                    .circleCrop()
                                    .into(userImage);
                        }


                    }else {
                        Snackbar.make(requireActivity().findViewById(android.R.id.content),
                                getErrorMessage(((Result.Error) result).getMessage()),
                                Snackbar.LENGTH_SHORT).show();
                    }
                });
    }

    private String getErrorMessage(String errorType) {
        switch (errorType) {
            case ERROR_RETRIEVING_USER_INFO:
                return requireActivity().getString(R.string.error_retrieving_user_info);
            default:
                return requireActivity().getString(R.string.unexpected_error);
        }
    }
}