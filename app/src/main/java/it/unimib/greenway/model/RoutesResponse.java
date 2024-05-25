package it.unimib.greenway.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class RoutesResponse implements Parcelable {
    @SerializedName("routes")
    private List<Route> routes;

    public RoutesResponse() {
    }

    public RoutesResponse(List<Route> routes) {
        this.routes = routes;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    @Override
    public String toString() {
        return "RoutesResponse{" +
                "routes=" + routes +
                '}';
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }
    protected RoutesResponse(Parcel in) {
        this.routes = in.createTypedArrayList(Route.CREATOR);
    }


    public static final Creator<RoutesResponse> CREATOR = new Creator<RoutesResponse>() {
        @Override
        public RoutesResponse createFromParcel(Parcel in) {
            return new RoutesResponse(in);
        }

        @Override
        public RoutesResponse[] newArray(int size) {
            return new RoutesResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
    }
    public void readFromParcel(Parcel source) {
        this.routes = source.createTypedArrayList(Route.CREATOR);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoutesResponse)) return false;
        RoutesResponse that = (RoutesResponse) o;
        return Objects.equals(routes, that.routes);
    }

}
