package it.unimib.greenway.model;

public class TransitLine {
    private Vehicle vehicle;

    public TransitLine(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public String toString() {
        return "TransitLine{" +
                "vehicle=" + vehicle +
                '}';
    }
}
