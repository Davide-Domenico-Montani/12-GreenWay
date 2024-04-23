package it.unimib.greenway.model;

public class Polyline {
    private String encodedPolyline;

    public String getEncodedPolyline() {
        return encodedPolyline;
    }

    public void setEncodedPolyline(String encodedPolyline) {
        this.encodedPolyline = encodedPolyline;
    }

    public Polyline(String encodedPolyline) {
        this.encodedPolyline = encodedPolyline;
    }

    @Override
    public String toString() {
        return "Polyline{" +
                "encodedPolyline='" + encodedPolyline + '\'' +
                '}';
    }
}
