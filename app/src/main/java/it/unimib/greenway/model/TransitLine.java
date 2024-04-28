package it.unimib.greenway.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TransitLine implements Parcelable {
    private Vehicle vehicle;

    public TransitLine(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public String toString() {
        return "TransitLine{" +
                "vehicle=" + vehicle +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable((Parcelable) this.vehicle, flags);
    }

    public void readFromParcel(Parcel source) {
        this.vehicle = source.readParcelable(Vehicle.class.getClassLoader());
    }

    protected TransitLine(Parcel in) {
        this.vehicle = in.readParcelable(Vehicle.class.getClassLoader());
    }

    public static final Creator<TransitLine> CREATOR = new Creator<TransitLine>() {
        @Override
        public TransitLine createFromParcel(Parcel source) {
            return new TransitLine(source);
        }

        @Override
        public TransitLine[] newArray(int size) {
            return new TransitLine[size];
        }
    };
}
