package it.unimib.greenway.model;

public class TransitDetails {
    private TransitLine transitLine;

    public TransitDetails(TransitLine transitLine) {
        this.transitLine = transitLine;
    }

    public TransitLine getTransitLine() {
        return transitLine;
    }

    public void setTransitLine(TransitLine transitLine) {
        this.transitLine = transitLine;
    }

    @Override
    public String toString() {
        return "TransitDetails{" +
                "transitLine=" + transitLine +
                '}';
    }
}
