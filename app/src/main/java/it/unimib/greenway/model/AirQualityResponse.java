package it.unimib.greenway.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AirQualityResponse implements Parcelable {
    @SerializedName("airQualities")
    private List<AirQuality> airQualities;

    public AirQualityResponse() {
    }

    public AirQualityResponse(List<AirQuality> airQualities) {
        this.airQualities = airQualities;
    }

    public List<AirQuality> getAirQualities() {
        return airQualities;
    }

    public void setAirQualities(List<AirQuality> airQualities) {
        this.airQualities = airQualities;
    }

    @Override
    public String toString() {
        return "AirQualityResponse{" +
                "imageList=" + airQualities +
                '}';
    }

    public static final Creator<AirQualityResponse> CREATOR = new Creator<AirQualityResponse>() {
        @Override
        public AirQualityResponse createFromParcel(Parcel in) {
            return new AirQualityResponse(in);
        }

        @Override
        public AirQualityResponse[] newArray(int size) {
            return new AirQualityResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.airQualities);
    }

    public void readFromParcel(Parcel source) {
        this.airQualities = source.createTypedArrayList(AirQuality.CREATOR);
    }

    protected AirQualityResponse(Parcel in) {
        this.airQualities = in.createTypedArrayList(AirQuality.CREATOR);
    }
}
