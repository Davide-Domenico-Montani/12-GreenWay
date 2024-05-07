package it.unimib.greenway.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Challenge implements Parcelable {
    int id;
    String description;
    int point;
    double target;
    boolean co2SavedWalk;
    boolean co2SavedCar;
    boolean co2SavedTransit;
    boolean kmCar;
    boolean kmTransit;
    boolean kmWalk;

    public Challenge() {
    }

    public Challenge(int id, String description, int point, double target, boolean co2SavedWalk, boolean co2SavedCar, boolean co2SavedTransit, boolean kmCar, boolean kmTransit, boolean kmWalk) {
        this.id = id;
        this.description = description;
        this.point = point;
        this.target = target;
        this.co2SavedWalk = co2SavedWalk;
        this.co2SavedCar = co2SavedCar;
        this.co2SavedTransit = co2SavedTransit;
        this.kmCar = kmCar;
        this.kmTransit = kmTransit;
        this.kmWalk = kmWalk;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public double getTarget() {
        return target;
    }

    public void setTarget(double target) {
        this.target = target;
    }

    public boolean isCo2SavedWalk() {
        return co2SavedWalk;
    }

    public void setCo2SavedWalk(boolean co2SavedWalk) {
        this.co2SavedWalk = co2SavedWalk;
    }

    public boolean isCo2SavedCar() {
        return co2SavedCar;
    }

    public void setCo2SavedCar(boolean co2SavedCar) {
        this.co2SavedCar = co2SavedCar;
    }

    public boolean isCo2SavedTransit() {
        return co2SavedTransit;
    }

    public void setCo2SavedTransit(boolean co2SavedTransit) {
        this.co2SavedTransit = co2SavedTransit;
    }

    public boolean isKmCar() {
        return kmCar;
    }

    public void setKmCar(boolean kmCar) {
        this.kmCar = kmCar;
    }

    public boolean isKmTransit() {
        return kmTransit;
    }

    public void setKmTransit(boolean kmTransit) {
        this.kmTransit = kmTransit;
    }

    public boolean isKmWalk() {
        return kmWalk;
    }

    public void setKmWalk(boolean kmWalk) {
        this.kmWalk = kmWalk;
    }

    @Override
    public String toString() {
        return "Challenge{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", point=" + point +
                ", target=" + target +
                ", co2SavedWalk=" + co2SavedWalk +
                ", co2SavedCar=" + co2SavedCar +
                ", co2SavedTransit=" + co2SavedTransit +
                ", kmCar=" + kmCar +
                ", kmTransit=" + kmTransit +
                ", kmWalk=" + kmWalk +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.description);
        dest.writeInt(this.point);
        dest.writeDouble(this.target);
        dest.writeByte(this.co2SavedWalk ? (byte) 1 : (byte) 0);
        dest.writeByte(this.co2SavedCar ? (byte) 1 : (byte) 0);
        dest.writeByte(this.co2SavedTransit ? (byte) 1 : (byte) 0);
        dest.writeByte(this.kmCar ? (byte) 1 : (byte) 0);
        dest.writeByte(this.kmTransit ? (byte) 1 : (byte) 0);
        dest.writeByte(this.kmWalk ? (byte) 1 : (byte) 0);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readInt();
        this.description = source.readString();
        this.point = source.readInt();
        this.target = source.readDouble();
        this.co2SavedWalk = source.readByte() != 0;
        this.co2SavedCar = source.readByte() != 0;
        this.co2SavedTransit = source.readByte() != 0;
        this.kmCar = source.readByte() != 0;
        this.kmTransit = source.readByte() != 0;
        this.kmWalk = source.readByte() != 0;
    }

    protected Challenge(Parcel in) {
        this.id = in.readInt();
        this.description = in.readString();
        this.point = in.readInt();
        this.target = in.readDouble();
        this.co2SavedWalk = in.readByte() != 0;
        this.co2SavedCar = in.readByte() != 0;
        this.co2SavedTransit = in.readByte() != 0;
        this.kmCar = in.readByte() != 0;
        this.kmTransit = in.readByte() != 0;
        this.kmWalk = in.readByte() != 0;
    }

    public static final Creator<Challenge> CREATOR = new Creator<Challenge>() {
        @Override
        public Challenge createFromParcel(Parcel source) {
            return new Challenge(source);
        }

        @Override
        public Challenge[] newArray(int size) {
            return new Challenge[size];
        }
    };
}
