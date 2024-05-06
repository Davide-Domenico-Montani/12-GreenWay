package it.unimib.greenway.data.source.user;

import static android.provider.Settings.System.getString;
import static it.unimib.greenway.util.Constants.CARKM_PARAMETER_DATABASE;
import static it.unimib.greenway.util.Constants.CAR_PARAMETER_DATABASE;
import static it.unimib.greenway.util.Constants.DRIVE_CONSTANT;
import static it.unimib.greenway.util.Constants.ERROR_RETRIEVING_USER_INFO;
import static it.unimib.greenway.util.Constants.NEW_PASSWORD_ERROR;
import static it.unimib.greenway.util.Constants.OLD_PASSWORD_ERROR;
import static it.unimib.greenway.util.Constants.PASSWORD_DATABASE_REFERENCE;
import static it.unimib.greenway.util.Constants.PASSWORD_ERROR_GOOGLE;
import static it.unimib.greenway.util.Constants.PHOTOURL_DATABASE_REFERENCE;
import static it.unimib.greenway.util.Constants.TRANSITKM_PARAMETER_DATABASE;
import static it.unimib.greenway.util.Constants.TRANSIT_CONSTANT;
import static it.unimib.greenway.util.Constants.TRANSIT_PARAMETER_DATABASE;
import static it.unimib.greenway.util.Constants.USER_DATABASE_REFERENCE;
import static it.unimib.greenway.util.Constants.WALKKM_PARAMETER_DATABASE;
import static it.unimib.greenway.util.Constants.WALK_PARAMETER_DATABASE;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Objects;

import it.unimib.greenway.R;
import it.unimib.greenway.model.User;

public class UserDataRemoteDataSource extends BaseUserDataRemoteDataSource{
    private static final String TAG = UserDataRemoteDataSource.class.getSimpleName();


    private final DatabaseReference databaseReference;
    public UserDataRemoteDataSource() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().getRef();
    }

    @Override
    public void saveUserData(User user) {
        databaseReference.child(USER_DATABASE_REFERENCE).child(user.getUserId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    userResponseCallback.onSuccessFromRemoteDatabase(user);
                }else{
                    databaseReference.child(USER_DATABASE_REFERENCE).child(user.getUserId()).setValue(user);
                    userResponseCallback.onSuccessFromRemoteDatabase(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                userResponseCallback.onFailureFromRemoteDatabase(error.getMessage());
            }
        });
    }

    @Override
    public void getUserInfo(String idToken) {
        databaseReference.child(USER_DATABASE_REFERENCE).child(idToken).get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                userResponseCallback.onFailureFromRemoteDatabase(ERROR_RETRIEVING_USER_INFO);
            } else {
                DataSnapshot userSnapshot = task.getResult();
                User user = userSnapshot.getValue(User.class);

                userResponseCallback.onSuccessFromRemoteDatabase(user);
            }
        });
    }

    @Override
    public void updateCo2Saved(String idToken, String transportType, double co2Saved, double kmTravel) {
        if(transportType.equals(DRIVE_CONSTANT)) {
            databaseReference.child(USER_DATABASE_REFERENCE).child(idToken).child(CAR_PARAMETER_DATABASE).get().addOnSuccessListener(dataSnapshot -> {
                if(dataSnapshot.exists()){
                    double co2SavedCarDatabase =  dataSnapshot.getValue(Double.class);
                    co2SavedCarDatabase += co2Saved;
                    databaseReference.child(USER_DATABASE_REFERENCE).child(idToken).child(CAR_PARAMETER_DATABASE).setValue(co2SavedCarDatabase);
                    updateKmTravelled(idToken, transportType, kmTravel);

                }else{

                }
            });
        }else if(transportType.equals(TRANSIT_CONSTANT)) {
            databaseReference.child(USER_DATABASE_REFERENCE).child(idToken).child(TRANSIT_PARAMETER_DATABASE).get().addOnSuccessListener(dataSnapshot -> {
                if(dataSnapshot.exists()){
                    double co2SavedCarDatabase = (double) dataSnapshot.getValue(Double.class);
                    co2SavedCarDatabase += co2Saved;


                    databaseReference.child(USER_DATABASE_REFERENCE).child(idToken).child(TRANSIT_PARAMETER_DATABASE).setValue(co2SavedCarDatabase);
                    updateKmTravelled(idToken, transportType, kmTravel);


                }else{

                }
            });
        }else{
            databaseReference.child(USER_DATABASE_REFERENCE).child(idToken).child(WALK_PARAMETER_DATABASE).get().addOnSuccessListener(dataSnapshot -> {
                if(dataSnapshot.exists()){
                    double co2SavedCarDatabase = (double) dataSnapshot.getValue(Double.class);
                    co2SavedCarDatabase += co2Saved;
                    databaseReference.child(USER_DATABASE_REFERENCE).child(idToken).child(WALK_PARAMETER_DATABASE).setValue(co2SavedCarDatabase);
                    updateKmTravelled(idToken, transportType, kmTravel);

                }else{

                }
            });
        }
    }

    @Override
    public void updateKmTravelled(String idToken, String transportType, double kmTravel) {
        if(transportType.equals(DRIVE_CONSTANT)) {
            databaseReference.child(USER_DATABASE_REFERENCE).child(idToken).child(CARKM_PARAMETER_DATABASE).get().addOnSuccessListener(dataSnapshot -> {
                if(dataSnapshot.exists()){
                    double kmDatabase =  dataSnapshot.getValue(Double.class);
                    kmDatabase += kmTravel;
                    databaseReference.child(USER_DATABASE_REFERENCE).child(idToken).child(CARKM_PARAMETER_DATABASE).setValue(kmDatabase);

                    getUserInfo(idToken); //Come callback
                }else{

                }
            });
        }else if(transportType.equals(TRANSIT_CONSTANT)) {
            databaseReference.child(USER_DATABASE_REFERENCE).child(idToken).child(TRANSITKM_PARAMETER_DATABASE).get().addOnSuccessListener(dataSnapshot -> {
                if(dataSnapshot.exists()){
                    double kmDatabase = (double) dataSnapshot.getValue(Double.class);
                    kmDatabase += kmTravel;
                    databaseReference.child(USER_DATABASE_REFERENCE).child(idToken).child(TRANSITKM_PARAMETER_DATABASE).setValue(kmDatabase);

                    getUserInfo(idToken); //Come callback
                }else{

                }
            });
        }else{
            databaseReference.child(USER_DATABASE_REFERENCE).child(idToken).child(WALKKM_PARAMETER_DATABASE).get().addOnSuccessListener(dataSnapshot -> {
                if(dataSnapshot.exists()){
                    double kmDatabase = (double) dataSnapshot.getValue(Double.class);
                    kmDatabase += kmTravel;
                    databaseReference.child(USER_DATABASE_REFERENCE).child(idToken).child(WALKKM_PARAMETER_DATABASE).setValue(kmDatabase);
                    getUserInfo(idToken); //Come callback
                }else{

                }
            });
        }

    }

    @Override
    public void changePassword(String token, String newPw, String oldPw) {
        Log.d("changePassword", "changePassword: " + token + " " + newPw + " " + oldPw);
        databaseReference.child(USER_DATABASE_REFERENCE).child(token).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String passwordDb = snapshot.child(PASSWORD_DATABASE_REFERENCE).getValue(String.class);

                    //controllo se vecchia password inserita è uguale a quella dentro database
                    if(!passwordDb.equals("")) {
                        if (passwordDb.equals(oldPw)) {
                            if (!passwordDb.equals(newPw)) {
                                databaseReference.child(USER_DATABASE_REFERENCE).
                                        child(token).child(PASSWORD_DATABASE_REFERENCE).setValue(newPw);
                                FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
                                fUser.updatePassword(newPw);
                                userResponseCallback.onSuccessFromRemoteDatabase(null);


                            } else {
                                userResponseCallback.onFailureFromRemoteDatabase(NEW_PASSWORD_ERROR);
                            }
                        } else {
                            userResponseCallback.onFailureFromRemoteDatabase(OLD_PASSWORD_ERROR);
                        }
                    }else{
                        userResponseCallback.onFailureFromRemoteDatabase(PASSWORD_ERROR_GOOGLE);
                    }
                }
            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void changePhoto(String token, String imageBitmap) {
        databaseReference.child(USER_DATABASE_REFERENCE).child(token).child(PHOTOURL_DATABASE_REFERENCE).setValue(imageBitmap);
        databaseReference.child(USER_DATABASE_REFERENCE).child(token).get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                userResponseCallback.onFailureFromRemoteDatabase(task.getException().getLocalizedMessage());
            } else {
                User user = task.getResult().getValue(User.class);
                userResponseCallback.onSuccessFromRemoteDatabase(user);
            }
        });
    }


}
