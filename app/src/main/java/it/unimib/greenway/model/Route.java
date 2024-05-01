package it.unimib.greenway.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Route implements Parcelable {
    private double co2;
    private String travelMode;
    private int distanceMeters;
    private String staticDuration;
    private Polyline polyline;
    private LatLng start;
    private LatLng destination;
    private List<Legs> legs;

    public Route(String travelMode, int distanceMeters, String staticDuration, Polyline polyline, LatLng start, LatLng destination, List<Legs> legs, double co2) {
        this.travelMode = travelMode;
        this.distanceMeters = distanceMeters;
        this.staticDuration = staticDuration;
        this.polyline = polyline;
        this.start = start;
        this.destination = destination;
        this.legs = legs;
        this.co2 = co2;
    }

    @Override
    public String toString() {
        return "Route{" +
                "travelMode='" + travelMode + '\'' +
                ", distanceMeters=" + distanceMeters +
                ", duration='" + staticDuration + '\'' +
                ", polyline=" + polyline +
                ", start=" + start +
                ", destination=" + destination +
                ", legs=" + legs +
                ", co2="+co2 +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return distanceMeters == route.distanceMeters && Objects.equals(travelMode, route.travelMode) && Objects.equals(staticDuration, route.staticDuration) && Objects.equals(polyline, route.polyline) && Objects.equals(start, route.start) && Objects.equals(destination, route.destination) && Objects.equals(legs, route.legs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(travelMode, distanceMeters, staticDuration, polyline, start, destination, legs);
    }

    public double getCo2() {
        return co2;
    }

    public void setCo2(double co2) {
        this.co2 = co2;
    }

    public String getStaticDuration() {
        return staticDuration;
    }

    public void setStaticDuration(String staticDuration) {
        this.staticDuration = staticDuration;
    }

    public List<Legs> getLegs() {
        return legs;
    }

    public void setLegs(List<Legs> legs) {
        this.legs = legs;
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

    public void setDuration(String staticDuration) {
        this.staticDuration = staticDuration;
    }

    public void setPolyline(Polyline polyline) {
        this.polyline = polyline;
    }

    public int getDistanceMeters() {
        return distanceMeters;
    }

    public String getDuration() {
        return staticDuration;
    }

    public Polyline getPolyline() {
        return polyline;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.travelMode);
        dest.writeInt(this.distanceMeters);
        dest.writeString(this.staticDuration);
        dest.writeParcelable((Parcelable) this.polyline, flags);
        dest.writeParcelable(this.start, flags);
        dest.writeParcelable(this.destination, flags);
        dest.writeList(this.legs);
    }

    public void readFromParcel(Parcel source) {
        this.travelMode = source.readString();
        this.distanceMeters = source.readInt();
        this.staticDuration = source.readString();
        this.polyline = source.readParcelable(Polyline.class.getClassLoader());
        this.start = source.readParcelable(LatLng.class.getClassLoader());
        this.destination = source.readParcelable(LatLng.class.getClassLoader());
        this.legs = source.createTypedArrayList(Legs.CREATOR);
    }

    protected Route(Parcel in) {
        this.travelMode = in.readString();
        this.distanceMeters = in.readInt();
        this.staticDuration = in.readString();
        this.polyline = in.readParcelable(Polyline.class.getClassLoader());
        this.start = in.readParcelable(LatLng.class.getClassLoader());
        this.destination = in.readParcelable(LatLng.class.getClassLoader());
        this.legs = in.createTypedArrayList(Legs.CREATOR);
    }

    public static final Creator<Route> CREATOR = new Creator<Route>() {
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
