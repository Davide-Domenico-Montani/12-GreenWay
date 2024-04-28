package it.unimib.greenway.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TransitDetails implements Parcelable {
    private TransitLine transitLine;

    public TransitDetails(TransitLine transitLine) {
        this.transitLine = transitLine;
    }

    public TransitLine getTransitLine() {
        return transitLine;
    }

    public void setTransitLine(TransitLine transitLine) {
        this.transitLine = transitLine;
    }

    @Override
    public String toString() {
        return "TransitDetails{" +
                "transitLine=" + transitLine +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.transitLine, flags);
    }

    public void readFromParcel(Parcel source) {
        this.transitLine = source.readParcelable(TransitLine.class.getClassLoader());
    }

    protected TransitDetails(Parcel in) {
        this.transitLine = in.readParcelable(TransitLine.class.getClassLoader());
    }

    public static final Creator<TransitDetails> CREATOR = new Creator<TransitDetails>() {
        @Override
        public TransitDetails createFromParcel(Parcel source) {
            return new TransitDetails(source);
        }

        @Override
        public TransitDetails[] newArray(int size) {
            return new TransitDetails[size];
        }
    };
}
