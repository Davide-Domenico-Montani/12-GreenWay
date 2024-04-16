package it.unimib.greenway.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.android.gms.maps.model.LatLng;

import java.util.Objects;

@Entity
public class Routes implements Parcelable{
    @NonNull
    @PrimaryKey
    String mode;
    @ColumnInfo(name = "origin")
    LatLng origin;

    @ColumnInfo(name = "destination")
    LatLng destination;
    @ColumnInfo(name = "y")
    private int y;

    public Routes(byte[] image, int x, int y) {
        this.id = x + "-" + y;
        this.image = image;
        this.x = x;
        this.y = y;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @NonNull
    @Override
    public String toString() {
        return "AirQuality{" +
                "image=" + image +
                ", x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AirQuality that = (AirQuality) o;
        return x == that.x && y == that.y && Objects.equals(image, that.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(image, x, y);
    }

    protected AirQuality(Parcel in) {
        int byteLength = in.readInt();
        byte[] byteArray = new byte[byteLength];
        in.readByteArray(byteArray);        this.x = in.readInt();
        this.y = in.readInt();
    }
    protected void readFromParcel(Parcel source) {
        int byteLength = source.readInt();
        byte[] byteArray = new byte[byteLength];
        source.readByteArray(byteArray);
        this.x = source.readInt();
        this.y = source.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    /*    dest.writeParcelable(this.image, flags);
        dest.writeInt(this.x);
        dest.writeInt(this.y);*/
    }
    public static final Parcelable.Creator<AirQuality> CREATOR = new Parcelable.Creator<AirQuality>() {
        @Override
        public AirQuality createFromParcel(Parcel source) {
            return new AirQuality(source);
        }

        @Override
        public AirQuality[] newArray(int size) {
            return new AirQuality[size];
        }
    };

}
