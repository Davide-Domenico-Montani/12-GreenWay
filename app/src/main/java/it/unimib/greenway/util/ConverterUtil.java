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
        if(route.getTravelMode().equals(Constants.TRANSIT_CONSTANT)){
            double totalCO2 = 0;
            for(int i = 0; i < route.getLegs().size(); i++){
                for(int j = 0; j < route.getLegs().get(i).getSteps().size(); j++){
                    if (route.getLegs().get(i).getSteps().get(j).getTransitDetails() == null)
                        totalCO2 = totalCO2;
                    else {
                        switch (route.getLegs().get(i).getSteps().get(j).getTransitDetails().getTransitLine().getVehicle().getType()) {
                            case "BUS":
                                totalCO2 += Constants.CO2_PRODUCTION_BUS * route.getLegs().get(i).getSteps().get(j).getDistanceMeters()/1000;
                                break;
                            case "TRAM":
                                totalCO2 += Constants.CO2_PRODUCTION_TRAM * route.getLegs().get(i).getSteps().get(j).getDistanceMeters()/1000;
                                break;
                            case "TRAIN":
                                totalCO2 += Constants.CO2_PRODUCTION_TRAIN * route.getLegs().get(i).getSteps().get(j).getDistanceMeters()/1000;
                                break;
                            case "METRO" :
                                totalCO2 += Constants.CO2_PRODUCTION_METRO * route.getLegs().get(i).getSteps().get(j).getDistanceMeters()/1000;
                                break;
                            case "HEAVY_RAIL" :
                                totalCO2 += Constants.CO2_PRODUCTION_METRO * route.getLegs().get(i).getSteps().get(j).getDistanceMeters()/1000;
                                break;
                            default:
                                totalCO2 = totalCO2;
                                break;

                        }
                    }
                }
            }
          return (totalCO2 / 1000) + " kg";
        }
        if(route.getTravelMode().equals(Constants.WALK_CONSTANT)){
            return "0 kg";
        }

        else return "32";
    }

    public String co2Converter(double co2) {
        double kilograms = co2 / 1000;
        return kilograms + "kg";
    }

}
