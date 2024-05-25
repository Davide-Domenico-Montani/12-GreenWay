package it.unimib.greenway.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import java.util.Objects;

public class ChallengeResponse implements Parcelable {
    private List<Challenge> challenges;

    public ChallengeResponse() {
    }

    public ChallengeResponse(List<Challenge> challenges) {
        this.challenges = challenges;
    }

    public ChallengeResponse(Challenge challenges) {
        this.challenges = (List<Challenge>) challenges;
    }

    public List<Challenge> getChallenges() {
        return challenges;
    }

    public void setChallenges(List<Challenge> challenges) {
        this.challenges = challenges;
    }

    @Override
    public String toString() {
        return "ChallengeResponse{" +
                "challenges=" + challenges +
                '}';
    }

    public static final Creator<ChallengeResponse> CREATOR = new Creator<ChallengeResponse>() {
        @Override
        public ChallengeResponse createFromParcel(Parcel in) {
            return new ChallengeResponse(in);
        }

        @Override
        public ChallengeResponse[] newArray(int size) {
            return new ChallengeResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.challenges);
    }

    public void readFromParcel(Parcel source) {
        this.challenges = source.createTypedArrayList(Challenge.CREATOR);
    }

    protected ChallengeResponse(Parcel in) {
        this.challenges = in.createTypedArrayList(Challenge.CREATOR);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ChallengeResponse that = (ChallengeResponse) obj;
        // replace this.field and that.field with the actual fields of the ChallengeResponse class
        return Objects.equals(this.challenges, that.challenges);
    }

}
