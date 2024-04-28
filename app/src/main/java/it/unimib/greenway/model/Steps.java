package it.unimib.greenway.model;

public class Steps {
    private int distanceMeters;

    private TransitDetails transitDetails;

    public Steps(int distanceMeters, TransitDetails transitDetails) {
        this.distanceMeters = distanceMeters;
        this.transitDetails = transitDetails;
    }

    public int getDistanceMeters() {
        return distanceMeters;
    }

    public void setDistanceMeters(int distanceMeters) {
        this.distanceMeters = distanceMeters;
    }

    public TransitDetails getTransitDetails() {
        return transitDetails;
    }

    public void setTransitDetails(TransitDetails transitDetails) {
        this.transitDetails = transitDetails;
    }

    @Override
    public String toString() {
        return "Steps{" +
                "distanceMeters=" + distanceMeters +
                ", transitDetails=" + transitDetails +
                '}';
    }
}
