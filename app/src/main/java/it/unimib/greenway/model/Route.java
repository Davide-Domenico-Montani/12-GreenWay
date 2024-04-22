package it.unimib.greenway.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class Route implements Parcelable {
    private int distanceMeters;
    private String duration;
    private Polyline polyline;

    @Override
    public String toString() {
        return "Route{" +
                "distanceMeters=" + distanceMeters +
                ", duration=" + duration +
                ", polyline='" + polyline + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return distanceMeters == route.distanceMeters && duration == route.duration && Objects.equals(polyline, route.polyline);
    }

    @Override
    public int hashCode() {
        return Objects.hash(distanceMeters, duration, polyline);
    }

    public Route(int distanceMeters, String duration, Polyline polyline) {
        this.distanceMeters = distanceMeters;
        this.duration = duration;
        this.polyline = polyline;
    }

    public void setDistanceMeters(int distanceMeters) {
        this.distanceMeters = distanceMeters;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setPolyline(Polyline polyline) {
        this.polyline = polyline;
    }

    public int getDistanceMeters() {
        return distanceMeters;
    }

    public String getDuration() {
        return duration;
    }

    public Polyline getPolyline() {
        return polyline;
    }
    protected Route(Parcel in) {
        duration = in.readString();
        polyline = in.readParcelable(Polyline.class.getClassLoader());
        distanceMeters = in.readInt();
    }


    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
      /*  dest.writeString(duration);
        dest.writeParcelable(polyline, flags);
        dest.writeInt(distanceMeters);*/
    }

    public static final Parcelable.Creator<Route> CREATOR = new Parcelable.Creator<Route>() {
        @Override
        public Route createFromParcel(Parcel source) {
            return new Route(source);
        }

        @Override
        public Route[] newArray(int size) {
            return new Route[size];
        }
    };
}
