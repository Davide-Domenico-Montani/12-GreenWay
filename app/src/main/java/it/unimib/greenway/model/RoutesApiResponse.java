package it.unimib.greenway.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class RoutesApiResponse extends RoutesResponse {
    private String status;
    private int totalResults;

    public RoutesApiResponse() {
        super();
    }

    public RoutesApiResponse(String status, int totalResults, List<Route> routes) {
        super(routes);
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
        return "RoutesApiResponse{" +
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

    protected RoutesApiResponse(Parcel in) {
        super(in);
        this.status = in.readString();
        this.totalResults = in.readInt();
    }

    public static final Parcelable.Creator<RoutesApiResponse> CREATOR = new Parcelable.Creator<RoutesApiResponse>() {
        @Override
        public RoutesApiResponse createFromParcel(Parcel source) {
            return new RoutesApiResponse(source);
        }

        @Override
        public RoutesApiResponse[] newArray(int size) {
            return new RoutesApiResponse[size];
        }
    };
}
