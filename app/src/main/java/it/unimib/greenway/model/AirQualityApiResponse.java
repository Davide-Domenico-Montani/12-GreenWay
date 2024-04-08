package it.unimib.greenway.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class AirQualityApiResponse extends AirQualityResponse{
    private String status;
    private int totalResults;

    public AirQualityApiResponse() {
        super();
    }

    public AirQualityApiResponse(String status, int totalResults, List<AirQuality> airQualities) {
        super(airQualities);
        this.status = status;
        this.totalResults = totalResults;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    @Override
    public String toString() {
        return "AirQualityApiResponse{" +
                "status='" + status + '\'' +
                ", totalResults=" + totalResults +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.status);
        dest.writeInt(this.totalResults);
    }

    public void readFromParcel(Parcel source) {
        super.readFromParcel(source);
        this.status = source.readString();
        this.totalResults = source.readInt();
    }

    protected AirQualityApiResponse(Parcel in) {
        super(in);
        this.status = in.readString();
        this.totalResults = in.readInt();
    }

    public static final Parcelable.Creator<AirQualityApiResponse> CREATOR = new Parcelable.Creator<AirQualityApiResponse>() {
        @Override
        public AirQualityApiResponse createFromParcel(Parcel source) {
            return new AirQualityApiResponse(source);
        }

        @Override
        public AirQualityApiResponse[] newArray(int size) {
            return new AirQualityApiResponse[size];
        }
    };
}
