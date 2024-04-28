package it.unimib.greenway.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Steps implements Parcelable {
    private int distanceMeters;
    private String staticDuration;

    private TransitDetails transitDetails;

    public Steps(int distanceMeters, String staticDuration, TransitDetails transitDetails) {
        this.distanceMeters = distanceMeters;
        this.staticDuration = staticDuration;
        this.transitDetails = transitDetails;
    }

    public String getStaticDuration() {
        return staticDuration;
    }

    public void setStaticDuration(String staticDuration) {
        this.staticDuration = staticDuration;
    }

    public int getDistanceMeters() {
        return distanceMeters;
    }

    public void setDistanceMeters(int distanceMeters) {
        this.distanceMeters = distanceMeters;
    }

    public TransitDetails getTransitDetails() {
        return transitDetails;
    }

    public void setTransitDetails(TransitDetails transitDetails) {
        this.transitDetails = transitDetails;
    }

    @Override
    public String toString() {
        return "Steps{" +
                "distanceMeters=" + distanceMeters +
                ", transitDetails=" + transitDetails +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.distanceMeters);
        dest.writeString(this.staticDuration);
        dest.writeParcelable(this.transitDetails, flags);
    }

    public void readFromParcel(Parcel source) {
        this.distanceMeters = source.readInt();
        this.staticDuration = source.readString();
        this.transitDetails = source.readParcelable(TransitDetails.class.getClassLoader());
    }

    protected Steps(Parcel in) {
        this.distanceMeters = in.readInt();
        this.staticDuration = in.readString();
        this.transitDetails = in.readParcelable(TransitDetails.class.getClassLoader());
    }

    public static final Creator<Steps> CREATOR = new Creator<Steps>() {
        @Override
        public Steps createFromParcel(Parcel source) {
            return new Steps(source);
        }

        @Override
        public Steps[] newArray(int size) {
            return new Steps[size];
        }
    };
}
