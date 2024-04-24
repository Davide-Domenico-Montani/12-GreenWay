package it.unimib.greenway.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.util.Objects;

public class Route implements Parcelable {
    private String travelMode;
    private int distanceMeters;
    private String duration;
    private Polyline polyline;
    private LatLng start;
    private LatLng destination;

    public Route(String travelMode, int distanceMeters, String duration, Polyline polyline, LatLng start, LatLng destination) {
        this.travelMode = travelMode;
        this.distanceMeters = distanceMeters;
        this.duration = duration;
        this.polyline = polyline;
        this.start = start;
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "Route{" +
                "travelMode='" + travelMode + '\'' +
                ", distanceMeters=" + distanceMeters +
                ", duration='" + duration + '\'' +
                ", polyline=" + polyline +
                ", start=" + start +
                ", destination=" + destination +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return distanceMeters == route.distanceMeters && Objects.equals(travelMode, route.travelMode) && Objects.equals(duration, route.duration) && Objects.equals(polyline, route.polyline) && Objects.equals(start, route.start) && Objects.equals(destination, route.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(travelMode, distanceMeters, duration, polyline, start, destination);
    }

    public LatLng getStart() {
        return start;
    }

    public void setStart(LatLng start) {
        this.start = start;
    }

    public LatLng getDestination() {
        return destination;
    }

    public void setDestination(LatLng destination) {
        this.destination = destination;
    }

    public String getTravelMode() {
        return travelMode;
    }

    public void setTravelMode(String travelMode) {
        this.travelMode = travelMode;
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
        travelMode = in.readString();
        duration = in.readString();
        polyline = in.readParcelable(Polyline.class.getClassLoader());
        distanceMeters = in.readInt();
        start = in.readParcelable(LatLng.class.getClassLoader());
        destination = in.readParcelable(LatLng.class.getClassLoader());
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
