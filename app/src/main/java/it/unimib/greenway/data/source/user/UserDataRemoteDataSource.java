package it.unimib.greenway.data.source.user;

import static android.provider.Settings.System.getString;
import static it.unimib.greenway.util.Constants.ADDING_FRIEND_ERROR;
import static it.unimib.greenway.util.Constants.CARKM_PARAMETER_DATABASE;
import static it.unimib.greenway.util.Constants.CAR_PARAMETER_DATABASE;
import static it.unimib.greenway.util.Constants.CHALLENGE_DATABASE_REFERENCE;
import static it.unimib.greenway.util.Constants.CO2CONSUMED_PARAMETER_DATABASE;
import static it.unimib.greenway.util.Constants.CO2_CAR_PARAMETER_DATABASE;
import static it.unimib.greenway.util.Constants.CO2_PRODUCTION_CAR_DIESEL;
import static it.unimib.greenway.util.Constants.CO2_PRODUCTION_CAR_GASOLINE;
import static it.unimib.greenway.util.Constants.CO2_PRODUCTION_CAR_GPL;
import static it.unimib.greenway.util.Constants.CO2_PRODUCTION_CAR_METHANE;
import static it.unimib.greenway.util.Constants.CO2_PRODUCTION_CAR_ELETTRIC;
import static it.unimib.greenway.util.Constants.DRIVE_CONSTANT;
import static it.unimib.greenway.util.Constants.ERROR_RETRIEVING_ALL_USERS;
import static it.unimib.greenway.util.Constants.ERROR_RETRIEVING_FRIENDS;
import static it.unimib.greenway.util.Constants.ERROR_RETRIEVING_USER_INFO;
import static it.unimib.greenway.util.Constants.FRIEND_DATABASE_REFERENCE;
import static it.unimib.greenway.util.Constants.NEW_PASSWORD_ERROR;
import static it.unimib.greenway.util.Constants.OLD_PASSWORD_ERROR;
import static it.unimib.greenway.util.Constants.PASSWORD_DATABASE_REFERENCE;
import static it.unimib.greenway.util.Constants.PASSWORD_ERROR_GOOGLE;
import static it.unimib.greenway.util.Constants.PHOTOURL_DATABASE_REFERENCE;
import static it.unimib.greenway.util.Constants.POINT_DATABASE_REFERENCE;
import static it.unimib.greenway.util.Constants.REMOVE_FRIEND_ERROR;
import static it.unimib.greenway.util.Constants.STATUS_CHALLENGE_DATABASE_REFERENCE;
import static it.unimib.greenway.util.Constants.TRANSITKM_PARAMETER_DATABASE;
import static it.unimib.greenway.util.Constants.TRANSIT_CONSTANT;
import static it.unimib.greenway.util.Constants.TRANSIT_PARAMETER_DATABASE;
import static it.unimib.greenway.util.Constants.USER_DATABASE_REFERENCE;
import static it.unimib.greenway.util.Constants.WALKKM_PARAMETER_DATABASE;
import static it.unimib.greenway.util.Constants.WALK_PARAMETER_DATABASE;

import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import it.unimib.greenway.R;
import it.unimib.greenway.model.Challenge;
import it.unimib.greenway.model.StatusChallenge;
import it.unimib.greenway.model.User;

public class UserDataRemoteDataSource extends BaseUserDataRemoteDataSource{
    private static final String TAG = UserDataRemoteDataSource.class.getSimpleName();



    public double co2Car;
    private final DatabaseReference databaseReference;
    public UserDataRemoteDataSource() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().getRef();
        co2Car = 0.0;
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
    public void updateCo2Saved(String idToken, String transportType, double co2Saved, double kmTravel, double co2Consumed) {
        databaseReference.child(USER_DATABASE_REFERENCE).child(idToken).child(CO2CONSUMED_PARAMETER_DATABASE).get().addOnSuccessListener(dataSnapshot -> {
            if(dataSnapshot.exists()){
                double co2ConsumedDb =  dataSnapshot.getValue(Double.class);
                co2ConsumedDb += co2Consumed;
                databaseReference.child(USER_DATABASE_REFERENCE).child(idToken).child(CO2CONSUMED_PARAMETER_DATABASE).setValue(co2ConsumedDb);
            }else{

            }
        });
        if(transportType.equals(DRIVE_CONSTANT)) {
            databaseReference.child(USER_DATABASE_REFERENCE).child(idToken).child(CAR_PARAMETER_DATABASE).get().addOnSuccessListener(dataSnapshot -> {
                if(dataSnapshot.exists()){
                    double co2SavedCarDatabase =  dataSnapshot.getValue(Double.class);
                    co2SavedCarDatabase += co2Saved;
                    databaseReference.child(USER_DATABASE_REFERENCE).child(idToken).child(CAR_PARAMETER_DATABASE).setValue(co2SavedCarDatabase);
                    updateKmTravelled(idToken, transportType, CAR_PARAMETER_DATABASE, co2Saved, kmTravel);

                }else{

                }
            });
        }else if(transportType.equals(TRANSIT_CONSTANT)) {
            databaseReference.child(USER_DATABASE_REFERENCE).child(idToken).child(TRANSIT_PARAMETER_DATABASE).get().addOnSuccessListener(dataSnapshot -> {
                if(dataSnapshot.exists()){
                    double co2SavedCarDatabase = (double) dataSnapshot.getValue(Double.class);
                    co2SavedCarDatabase += co2Saved;


                    databaseReference.child(USER_DATABASE_REFERENCE).child(idToken).child(TRANSIT_PARAMETER_DATABASE).setValue(co2SavedCarDatabase);
                    updateKmTravelled(idToken, transportType,TRANSIT_PARAMETER_DATABASE, co2Saved,kmTravel);


                }else{

                }
            });
        }else{
            databaseReference.child(USER_DATABASE_REFERENCE).child(idToken).child(WALK_PARAMETER_DATABASE).get().addOnSuccessListener(dataSnapshot -> {
                if(dataSnapshot.exists()){
                    double co2SavedCarDatabase = (double) dataSnapshot.getValue(Double.class);
                    co2SavedCarDatabase += co2Saved;
                    databaseReference.child(USER_DATABASE_REFERENCE).child(idToken).child(WALK_PARAMETER_DATABASE).setValue(co2SavedCarDatabase);
                    updateKmTravelled(idToken, transportType, WALK_PARAMETER_DATABASE, co2Saved,kmTravel );

                }else{

                }
            });
        }
    }

    @Override
    public void updateCo2Car(String idToken, double co2Car) {
        databaseReference.child(USER_DATABASE_REFERENCE).child(idToken).child(CO2_CAR_PARAMETER_DATABASE).setValue(co2Car);
        getUserInfo(idToken);
    }

    @Override
    public void updateKmTravelled(String idToken, String transportType, String parameterCo2, double co2Database,  double kmTravel) {
        if(transportType.equals(DRIVE_CONSTANT)) {
            databaseReference.child(USER_DATABASE_REFERENCE).child(idToken).child(CARKM_PARAMETER_DATABASE).get().addOnSuccessListener(dataSnapshot -> {
                if(dataSnapshot.exists()){
                    double kmDatabase =  dataSnapshot.getValue(Double.class);
                    kmDatabase += kmTravel;
                    databaseReference.child(USER_DATABASE_REFERENCE).child(idToken).child(CARKM_PARAMETER_DATABASE).setValue(kmDatabase);
                    updateStatusChallenge(idToken, transportType, parameterCo2, CARKM_PARAMETER_DATABASE, co2Database, kmTravel);
                }else{

                }
            });
        }else if(transportType.equals(TRANSIT_CONSTANT)) {
            databaseReference.child(USER_DATABASE_REFERENCE).child(idToken).child(TRANSITKM_PARAMETER_DATABASE).get().addOnSuccessListener(dataSnapshot -> {
                if(dataSnapshot.exists()){
                    double kmDatabase = (double) dataSnapshot.getValue(Double.class);
                    kmDatabase += kmTravel;
                    databaseReference.child(USER_DATABASE_REFERENCE).child(idToken).child(TRANSITKM_PARAMETER_DATABASE).setValue(kmDatabase);
                    updateStatusChallenge(idToken, transportType, parameterCo2, TRANSITKM_PARAMETER_DATABASE,co2Database, kmTravel);
                }else{

                }
            });
        }else{
            databaseReference.child(USER_DATABASE_REFERENCE).child(idToken).child(WALKKM_PARAMETER_DATABASE).get().addOnSuccessListener(dataSnapshot -> {
                if(dataSnapshot.exists()){
                    double kmDatabase = (double) dataSnapshot.getValue(Double.class);
                    kmDatabase += kmTravel;
                    databaseReference.child(USER_DATABASE_REFERENCE).child(idToken).child(WALKKM_PARAMETER_DATABASE).setValue(kmDatabase);
                    updateStatusChallenge(idToken, transportType, parameterCo2, WALKKM_PARAMETER_DATABASE, co2Database, kmTravel);
                }else{

                }
            });
        }
    }


    @Override
    public void updateStatusChallenge(String idToken, String transportType, String parameterco2, String parameterKm, double co2Database, double kmDatabase) {
        List<Challenge> challengeList = new ArrayList<>();
        databaseReference.child(CHALLENGE_DATABASE_REFERENCE).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                for(DataSnapshot challengeSnapshot: task.getResult().getChildren()){
                    Challenge challenge = challengeSnapshot.getValue(Challenge.class);
                    challengeList.add(challenge);
                }
                databaseReference.child(USER_DATABASE_REFERENCE).child(idToken).child(STATUS_CHALLENGE_DATABASE_REFERENCE).get().addOnCompleteListener(task1 -> {
                    List<StatusChallenge> statusChallengeList = new ArrayList<>();
                    if(task1.isSuccessful()){
                        for(DataSnapshot statusChallengeSnapshot: task1.getResult().getChildren()){
                            StatusChallenge statusChallenge = statusChallengeSnapshot.getValue(StatusChallenge.class);
                            statusChallengeList.add(statusChallenge);
                        }
                        if(statusChallengeList != null){
                                for (Challenge challenge : challengeList) {
                                    if (challenge.isKmCar() && parameterKm.equals(CARKM_PARAMETER_DATABASE)) {
                                        statusChallengeList.get(challenge.getId()-1).setStatusChallenge(statusChallengeList.get(challenge.getId()-1).getStatusChallenge() + kmDatabase);
                                    }
                                    if (challenge.isKmTransit() && parameterKm.equals(TRANSITKM_PARAMETER_DATABASE)) {
                                        statusChallengeList.get(challenge.getId()-1).setStatusChallenge(statusChallengeList.get(challenge.getId()-1).getStatusChallenge() + kmDatabase);
                                    }
                                    if (challenge.isKmWalk() && parameterKm.equals(WALKKM_PARAMETER_DATABASE)) {
                                        statusChallengeList.get(challenge.getId()-1).setStatusChallenge(statusChallengeList.get(challenge.getId()-1).getStatusChallenge() + kmDatabase);
                                    }
                                    if (challenge.isCo2SavedCar() && parameterco2.equals(CAR_PARAMETER_DATABASE)) {
                                        statusChallengeList.get(challenge.getId()-1).setStatusChallenge(statusChallengeList.get(challenge.getId()-1).getStatusChallenge() + co2Database);
                                    }
                                    if (challenge.isCo2SavedTransit() && parameterco2.equals(TRANSIT_PARAMETER_DATABASE)) {
                                        statusChallengeList.get(challenge.getId()-1).setStatusChallenge(statusChallengeList.get(challenge.getId()-1).getStatusChallenge() + co2Database);
                                    }
                                    if (challenge.isCo2SavedWalk() && parameterco2.equals(WALK_PARAMETER_DATABASE)) {
                                        statusChallengeList.get(challenge.getId()-1).setStatusChallenge(statusChallengeList.get(challenge.getId()-1).getStatusChallenge() + co2Database);
                                    }

                                    statusChallengeList.get(challenge.getId()-1).setPercentage((int) ((statusChallengeList.get(challenge.getId()-1).getStatusChallenge() / challenge.getTarget()) * 100));
                                    if(statusChallengeList.get(challenge.getId()-1).getPercentage() >= 100 && !statusChallengeList.get(challenge.getId()-1).isCompleted()) {
                                        statusChallengeList.get(challenge.getId()-1).setCompleted(true);
                                        updateUserPoint(idToken, challenge.getPoint());
                                    }
                                }

                            databaseReference.child(USER_DATABASE_REFERENCE).child(idToken).child(STATUS_CHALLENGE_DATABASE_REFERENCE).setValue(statusChallengeList);
                            getUserInfo(idToken); //Come callback
                        }
                    }
                });
            }
        });
    }

    @Override
    public void updateUserPoint(String idToken, int point) {
        databaseReference.child(USER_DATABASE_REFERENCE).child(idToken).child(POINT_DATABASE_REFERENCE).get().addOnSuccessListener(dataSnapshot -> {
            if(dataSnapshot.exists()){
                int pointDatabase =  dataSnapshot.getValue(Integer.class);
                pointDatabase += point;
                databaseReference.child(USER_DATABASE_REFERENCE).child(idToken).child(POINT_DATABASE_REFERENCE).setValue(pointDatabase);
                getUserInfo(idToken); //Come callback
            }else{

            }
        });
    }

    @Override
    public void getFriends(String idToken) {
        databaseReference.child(USER_DATABASE_REFERENCE).child(idToken).child(FRIEND_DATABASE_REFERENCE).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                List<String> idFriends = new ArrayList<>();
                for(DataSnapshot idFriendSnapshot: task.getResult().getChildren()){
                    String idFriend = idFriendSnapshot.getValue(String.class);
                    idFriends.add(idFriend);
                }
                List<User> friends = new ArrayList<>();
                    databaseReference.child(USER_DATABASE_REFERENCE).get().addOnCompleteListener(task1 -> {
                        if(task1.isSuccessful()){
                            for(DataSnapshot userSnapshot: task1.getResult().getChildren()){
                                User user = userSnapshot.getValue(User.class);
                                if(idFriends.contains(user.getUserId())){
                                    friends.add(user);
                                }
                            }
                            userResponseCallback.onSuccessGettingFriendsFromRemoteDatabase(friends);
                        }else{
                            userResponseCallback.onFailureGettingFriendsFromRemoteDatabase(ERROR_RETRIEVING_ALL_USERS);
                        }
                    });


            }else{
                userResponseCallback.onFailureGettingFriendsFromRemoteDatabase(ERROR_RETRIEVING_FRIENDS);
            }
        });
    }

    @Override
    public void addFriend(String idToken, String friendId) {
        databaseReference.child(USER_DATABASE_REFERENCE).child(idToken).child(FRIEND_DATABASE_REFERENCE).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                List<String> idFriends = new ArrayList<>();
                for(DataSnapshot idFriendSnapshot: task.getResult().getChildren()){
                    String idFriend = idFriendSnapshot.getValue(String.class);
                    idFriends.add(idFriend);
                }
                if(!idFriends.contains(friendId)){
                    idFriends.add(friendId);
                    databaseReference.child(USER_DATABASE_REFERENCE).child(idToken).child(FRIEND_DATABASE_REFERENCE).setValue(idFriends);
                    getFriends(idToken);
                }else{
                    userResponseCallback.onFailureGettingFriendsFromRemoteDatabase(ADDING_FRIEND_ERROR);
                }
            }else{
                userResponseCallback.onFailureGettingFriendsFromRemoteDatabase(ADDING_FRIEND_ERROR);
            }
        });
    }

    @Override
    public void removeFriend(String idToken, String friendId) {
        databaseReference.child(USER_DATABASE_REFERENCE).child(idToken).child(FRIEND_DATABASE_REFERENCE).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                List<String> idFriends = new ArrayList<>();
                for(DataSnapshot idFriendSnapshot: task.getResult().getChildren()){
                    String idFriend = idFriendSnapshot.getValue(String.class);
                    idFriends.add(idFriend);
                }
                if(idFriends.contains(friendId)){
                    idFriends.remove(friendId);
                    databaseReference.child(USER_DATABASE_REFERENCE).child(idToken).child(FRIEND_DATABASE_REFERENCE).setValue(idFriends);
                    getFriends(idToken);
                }else{
                    userResponseCallback.onFailureGettingFriendsFromRemoteDatabase(REMOVE_FRIEND_ERROR);
                }
            }else{
                userResponseCallback.onFailureGettingFriendsFromRemoteDatabase(REMOVE_FRIEND_ERROR);
            }
        });
    }

    @Override
    public void getAllUsers(String idToken) {
        databaseReference.child(USER_DATABASE_REFERENCE).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                List<User> users = new ArrayList<>();
                for(DataSnapshot userSnapshot: task.getResult().getChildren()){
                    User user = userSnapshot.getValue(User.class);
                    if(!user.getUserId().equals(idToken)){
                        users.add(user);
                    }
                }
                userResponseCallback.onSuccessGettingAllFriendsFromRemoteDatabase(users);
            }else{
                userResponseCallback.onFailureGettingAllFriendsFromRemoteDatabase(task.getException().getLocalizedMessage());
            }
        });
    }


    @Override
    public void changePassword(String token, String newPw, String oldPw) {
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




    /*public void addChallenge(){
    //Richiamato nell getuserinfo
        List<Challenge> challengeList = new ArrayList<>();
        challengeList.add(new Challenge(1, "Percorri almeno 50 km a piedi", 100, 50000.0, false, false, false, false, false, true));
        challengeList.add(new Challenge(2, "Risparmia 10 kg di CO2 riducendo l'uso dell'auto.", 70, 10000.0, false, true, false, false, false, false));
        challengeList.add(new Challenge(3, "Percorri 100 km utilizzando i mezzi pubblici.", 200, 100000.0, false, false, false, false, true, false));
        challengeList.add(new Challenge(4, "Risparmia 20 kg di CO2 scegliendo mezzi pubblici.", 130, 20000.0, false, false, true, false, false, false));
        challengeList.add(new Challenge(5, "Percorri 150 km a piedi per ridurre le emissioni di CO2.", 400, 150000.0, false, false, false, false, false, true));
        challengeList.add(new Challenge(6, "Utilizza i mezzi pubblici e risparmia 15 kg di CO2.", 120, 15000.0, false, false, true, false, false, false));
        challengeList.add(new Challenge(7, "Percorri 300km a piedi oppure con i mezzi pubblici.", 300, 300000.0, false, false, false, false, true, true));

        for(Challenge challenge: challengeList) {
            databaseReference.child("challenge").child(String.valueOf(challenge.getId())).setValue(challenge);
        }
    }*/
}
