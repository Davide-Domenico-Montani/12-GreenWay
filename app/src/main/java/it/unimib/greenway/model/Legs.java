package it.unimib.greenway.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

public class Legs implements Parcelable {
    private int distanceMeters;
    private String duration;
    private Polyline polyline;

    private List<Steps> steps;

    protected Legs(Parcel in) {
        distanceMeters = in.readInt();
        duration = in.readString();
    }

    public static final Creator<Legs> CREATOR = new Creator<Legs>() {
        @Override
        public Legs createFromParcel(Parcel in) {
            return new Legs(in);
        }

        @Override
        public Legs[] newArray(int size) {
            return new Legs[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(distanceMeters);
        dest.writeString(duration);
    }
}
