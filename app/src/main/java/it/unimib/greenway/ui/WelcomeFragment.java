package it.unimib.greenway.ui;

import android.app.Activity;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.carousel.CarouselLayoutManager;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import it.unimib.greenway.CarouselItem;
import it.unimib.greenway.R;
import it.unimib.greenway.adapter.CarouselAdapter;


public class WelcomeFragment extends Fragment {
    private static final String TAG = WelcomeFragment.class.getSimpleName();

    private List<CarouselItem> carouselItems;
    private RecyclerView carouselRecyclerView;
    private CarouselAdapter carouselAdapter;
    private SignInClient oneTapClient;
    private BeginSignInRequest signInRequest;
    private ActivityResultLauncher<IntentSenderRequest> activityResultLauncher;
    private ActivityResultContracts.StartIntentSenderForResult startIntentSenderForResult;


    CarouselLayoutManager carouselLayoutManager;


    Button loginButton, signInButton, google;

    public WelcomeFragment() {
    }

    public static WelcomeFragment newInstance(String param1, String param2) {
        WelcomeFragment fragment = new WelcomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        oneTapClient = Identity.getSignInClient(requireActivity());
        signInRequest = BeginSignInRequest.builder()
                .setPasswordRequestOptions(BeginSignInRequest.PasswordRequestOptions.builder()
                        .setSupported(true)
                        .build())
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        .setServerClientId(getString(R.string.server_client_id))
                        .setFilterByAuthorizedAccounts(false)
                        .build())
                .setAutoSelectEnabled(true)
                .build();

        startIntentSenderForResult = new ActivityResultContracts.StartIntentSenderForResult();

        activityResultLauncher = registerForActivityResult(startIntentSenderForResult, activityResult -> {
            if (activityResult.getResultCode() == Activity.RESULT_OK) {
                try {
                    SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(activityResult.getData());
                    String idToken = credential.getGoogleIdToken();
                    if (idToken !=  null) {
                        Log.d("Test", idToken);
                        // Got an ID token from Google. Use it to authenticate with Firebase.
                        /*userViewModel.getGoogleUserMutableLiveData(idToken).observe(getViewLifecycleOwner(), authenticationResult -> {

                            if (authenticationResult.isSuccessUser()) {
                                User user = ((Result.UserResponseSuccess) authenticationResult).getData();
                                saveLoginData(user.getUserId(), user.getNome(), user.getCognome(), user.getEmail(), "");
                                userViewModel.setAuthenticationError(false);
                                retrieveUserInformationAndStartActivity(user, R.id.action_authenticationFragment_to_mainActivity);

                            } else {
                                userViewModel.setAuthenticationError(true);

                                Snackbar.make(requireActivity().findViewById(android.R.id.content),
                                        getErrorMessage(((Result.Error) authenticationResult).getMessage()),
                                        Snackbar.LENGTH_SHORT).show();
                            }
                        });*/
                    }
                } catch (ApiException e) {
                    Snackbar.make(requireActivity().findViewById(android.R.id.content),
                            requireActivity().getString(R.string.unexpected_error),
                            Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_welcome, container, false);

        CarouselItem item1 = new CarouselItem(R.drawable.image1, getString(R.string.string1Carosel));
        CarouselItem item2 = new CarouselItem(R.drawable.image2, getString(R.string.string2Carosel));
        CarouselItem item3 = new CarouselItem(R.drawable.image3, getString(R.string.string3Carosel));
        CarouselItem item4 = new CarouselItem(R.drawable.image4, getString(R.string.string4Carosel));

        carouselItems = new ArrayList<>();
        carouselItems.add(item1);
        carouselItems.add(item2);
        carouselItems.add(item3);
        carouselItems.add(item4);


        carouselRecyclerView = rootView.findViewById(R.id.carousel_recycler_view);
        carouselAdapter = new CarouselAdapter(carouselItems);
        carouselRecyclerView.setAdapter(carouselAdapter);
        carouselLayoutManager = new CarouselLayoutManager();
        carouselRecyclerView.setLayoutManager(carouselLayoutManager);

        return rootView;


    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        loginButton = view.findViewById(R.id.button_login);
        signInButton = view.findViewById(R.id.button_signin);
        google = view.findViewById(R.id.buttonGoogle);


        google.setOnClickListener(v -> oneTapClient.beginSignIn(signInRequest)
                .addOnSuccessListener(requireActivity(), new OnSuccessListener<BeginSignInResult>() {
                    @Override
                    public void onSuccess(BeginSignInResult result) {
                        Log.d(TAG, "onSuccess from oneTapClient.beginSignIn(BeginSignInRequest)");
                        IntentSenderRequest intentSenderRequest =
                                new IntentSenderRequest.Builder(result.getPendingIntent()).build();
                        activityResultLauncher.launch(intentSenderRequest);
                    }
                })
                .addOnFailureListener(requireActivity(), new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // No saved credentials found. Launch the One Tap sign-up flow, or
                        // do nothing and continue presenting the signed-out UI.
                        Log.d(TAG, e.getLocalizedMessage());

                        Snackbar.make(requireActivity().findViewById(android.R.id.content),
                                requireActivity().getString(R.string.error_no_google_account_found_message),
                                Snackbar.LENGTH_SHORT).show();
                    }
                }));
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_fragment_welcome_to_login);
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_fragment_welcome_to_sign_in);
            }
        });
    }




}