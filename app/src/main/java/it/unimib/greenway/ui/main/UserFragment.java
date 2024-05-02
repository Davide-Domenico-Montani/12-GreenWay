package it.unimib.greenway.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.snackbar.Snackbar;

import it.unimib.greenway.R;
import it.unimib.greenway.data.repository.user.IUserRepository;
import it.unimib.greenway.model.Result;
import it.unimib.greenway.model.User;
import it.unimib.greenway.ui.UserViewModel;
import it.unimib.greenway.ui.UserViewModelFactory;
import it.unimib.greenway.util.ConverterUtil;
import it.unimib.greenway.util.ServiceLocator;


public class UserFragment extends Fragment {

    private ConverterUtil converterUtil;

    private LinearProgressIndicator linearProgress;

    private UserViewModel userViewModel;

    private double kmCar;
    private double kmTransit;

    private double co2Car;

    private double co2SavedCar;
    private double co2SavedTransit;
    private double co2SavedWalk;

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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        // Inflate the layout for this fragment



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

                        //co2SavedCar
                        TextView co2SavedCarTextView = view.findViewById(R.id.co2CarTextView);
                        co2SavedCarTextView.setText(converterUtil.co2Converter(co2SavedCar));
                        LinearProgressIndicator co2SavedCarProgressBar = view.findViewById(R.id.progressCar);
                        co2SavedCarProgressBar.setProgress(converterUtil.co2SavedProgressBar(co2SavedCar, co2SavedCar, co2SavedTransit, co2SavedWalk), true);

                        //co2SavedTransit
                        TextView co2SavedTransitTextView = view.findViewById(R.id.co2PublicTTextView);
                        co2SavedTransitTextView.setText(converterUtil.co2Converter(co2SavedTransit));
                        LinearProgressIndicator co2SavedTransitProgressBar = view.findViewById(R.id.progressTransit);
                        co2SavedTransitProgressBar.setProgress(converterUtil.co2SavedProgressBar(co2SavedTransit, co2SavedCar, co2SavedTransit, co2SavedWalk), true);

                        //co2SavedWalk
                        TextView co2SavedWalkTextView = view.findViewById(R.id.co2WalkTextView);
                        co2SavedWalkTextView.setText(converterUtil.co2Converter(co2SavedWalk));
                        LinearProgressIndicator co2SavedWalkProgressBar = view.findViewById(R.id.progressWalk);
                        co2SavedWalkProgressBar.setProgress(converterUtil.co2SavedProgressBar(co2SavedWalk, co2SavedCar, co2SavedTransit, co2SavedWalk), true);


                    }else {

                    }
                });
        return view;
    }

}