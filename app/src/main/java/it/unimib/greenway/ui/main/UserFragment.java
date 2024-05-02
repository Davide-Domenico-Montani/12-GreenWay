package it.unimib.greenway.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import it.unimib.greenway.R;


public class UserFragment extends Fragment {

    private ImageButton buttonSettings;
    private LinearProgressIndicator linearProgress;
    int progressValue = 50;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        buttonSettings = view.findViewById(R.id.settingsButton);
        // Inflate the layout for this fragment
        TextView coSavedCar = view.findViewById(R.id.co2CarTextView);
        coSavedCar.setText(String.valueOf(progressValue));
        LinearProgressIndicator progressBar = view.findViewById(R.id.progressCar);
        progressBar.setProgress(progressValue, true);

        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_userFragment_to_settingFragment);
            }
        });

        return view;
    }

}