package it.unimib.greenway.util;

import it.unimib.greenway.model.Route;

public class ConverterUtil {
    public ConverterUtil() {}

    //Convert second in to houre and minute
    public String convertSecond(int totalSeconds){
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int seconds = totalSeconds % 60;
        int day = 0;
        while(hours >= 24){
            day++;
            hours -= 24;
        }
        if(day != 0)
            return day + "d " + hours +"h " + minutes + "m";
        if(hours != 0)
            return hours +"h " + minutes + "m";
        else
            return minutes + "m ";
    }

    public static double convertMeter(int meters) {
        return meters / 1000.0; // Dividi i metri per 1000 per ottenere i chilometri
    }
    public String co2Calculator(Route route) {
        if (route.getTravelMode().equals(Constants.DRIVE_CONSTANT)) {
            return co2Converter(convertMeter(route.getDistanceMeters()) * 108.2);
        }
        else return "32";
    }

    public String co2Converter(double co2) {
        double kilograms = co2 / 1000;
        return kilograms + "kg";
    }

}
