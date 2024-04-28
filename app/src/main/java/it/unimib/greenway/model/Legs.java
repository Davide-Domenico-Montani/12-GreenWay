package it.unimib.greenway.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Legs implements Parcelable {

    private List<Steps> steps;

    public Legs(List<Steps> steps) {
        this.steps = steps;
    }


    public List<Steps> getSteps() {
        return steps;
    }

    public void setSteps(List<Steps> steps) {
        this.steps = steps;
    }

    @Override
    public String toString() {
        return "Legs{" +
                "steps=" + steps +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.steps);
    }

    public void readFromParcel(Parcel source) {
        this.steps = source.createTypedArrayList(Steps.CREATOR);
    }

    protected Legs(Parcel in) {
        this.steps = in.createTypedArrayList(Steps.CREATOR);
    }

    public static final Creator<Legs> CREATOR = new Creator<Legs>() {
        @Override
        public Legs createFromParcel(Parcel source) {
            return new Legs(source);
        }

        @Override
        public Legs[] newArray(int size) {
            return new Legs[size];
        }
    };
}
