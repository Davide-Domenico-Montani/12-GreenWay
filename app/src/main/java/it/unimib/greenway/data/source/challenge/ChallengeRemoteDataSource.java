package it.unimib.greenway.data.source.challenge;

import static it.unimib.greenway.util.Constants.CHALLENGE_DATABASE_REFERENCE;
import static it.unimib.greenway.util.Constants.ERROR_RETRIEVING_CHALLENGE;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import it.unimib.greenway.model.Challenge;

public class ChallengeRemoteDataSource extends BaseChallengeRemoteDataSource{

    private final DatabaseReference databaseReference;
    private List<Challenge> challengeList;
    public ChallengeRemoteDataSource() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().getRef();
    }
    @Override
    public void getChallenge() {
        challengeList = new ArrayList<>();

        // Aggiungi un listener per ottenere i dati una volta
        databaseReference.child(CHALLENGE_DATABASE_REFERENCE).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot challengeSnapshot : dataSnapshot.getChildren()) {
                    Challenge challenge = challengeSnapshot.getValue(Challenge.class);
                    challengeList.add(challenge);
                }
                challengeCallBack.onSuccessFromRemote(challengeList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                challengeCallBack.onFailureFromRemote(ERROR_RETRIEVING_CHALLENGE);
            }
        });
    }
}
