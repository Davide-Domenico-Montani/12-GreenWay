package it.unimib.greenway.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.progressindicator.LinearProgressIndicator;

import it.unimib.greenway.R;


public class UserFragment extends Fragment {

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

        // Inflate the layout for this fragment
        TextView coSavedCar = view.findViewById(R.id.co2CarTextView);
        coSavedCar.setText(String.valueOf(progressValue));
        LinearProgressIndicator progressBar = view.findViewById(R.id.progressCar);
        progressBar.setProgress(progressValue, true);

        return view;
    }

}