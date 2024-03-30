package it.unimib.greenway.data.source.user;

import android.util.Log;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import it.unimib.greenway.model.User;

public class UserAuthenticationRemoteDataSource extends BaseUserAuthenticationRemoteDataSource {

    private static final String TAG = UserAuthenticationRemoteDataSource.class.getSimpleName();
    private final FirebaseAuth firebaseAuth;
    public UserAuthenticationRemoteDataSource() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void signInWithGoogle(String idToken) {
        if (idToken !=  null) {
            // Got an ID token from Google. Use it to authenticate with Firebase.
            AuthCredential firebaseCredential = GoogleAuthProvider.getCredential(idToken, null);
            firebaseAuth.signInWithCredential(firebaseCredential).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success");
                    /*FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    if (firebaseUser != null) {
                        String[] parts = firebaseUser.getDisplayName().split(" ");
                        String nome = parts[0];  // Il primo elemento è il nome
                        String cognome = parts.length > 1 ? parts[1] : "";  // Il secondo elemento è il cognome, se presente

                        userResponseCallback.onSuccessFromAuthentication(
                                new User(firebaseUser.getUid(),
                                        nome, cognome,
                                        firebaseUser.getEmail(),
                                        "",
                                        "",
                                        firebaseUser.getPhotoUrl().toString(),
                                        null));
                    } else {
                        userResponseCallback.onFailureFromAuthentication(
                                getErrorMessage(task.getException()));
                    }*/
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.getException());
                    //userResponseCallback.onFailureFromAuthentication(getErrorMessage(task.getException()));
                }
            });
        }
    }
}
