package it.unimib.greenway.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.unimib.greenway.R;


public class NavigatorFragment extends Fragment {

    public NavigatorFragment() {
        // Required empty public constructor
    }

    public static NavigatorFragment newInstance() {
        NavigatorFragment fragment = new NavigatorFragment();

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
        return inflater.inflate(R.layout.fragment_navigator, container, false);
    }
}