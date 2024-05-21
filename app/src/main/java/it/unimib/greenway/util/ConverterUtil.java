package it.unimib.greenway.util;

import static it.unimib.greenway.util.Constants.CO2_PRODUCTION_CAR_DIESEL;
import static it.unimib.greenway.util.Constants.CO2_PRODUCTION_CAR_ELETTRIC;
import static it.unimib.greenway.util.Constants.CO2_PRODUCTION_CAR_GASOLINE;
import static it.unimib.greenway.util.Constants.CO2_PRODUCTION_CAR_GPL;
import static it.unimib.greenway.util.Constants.CO2_PRODUCTION_CAR_METHANE;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import androidx.lifecycle.ViewModelProvider;

import java.io.ByteArrayOutputStream;

import it.unimib.greenway.data.repository.user.IUserRepository;
import it.unimib.greenway.model.Route;
import it.unimib.greenway.ui.UserViewModel;
import it.unimib.greenway.ui.UserViewModelFactory;

public class ConverterUtil {
    UserViewModel userViewModel;
    public ConverterUtil() {
    }

    //Convert second in to houre and minute
    public String convertSecond(int totalSeconds){
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
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

    public double convertMeter(int meters) {
        return meters / 1000.0; // Dividi i metri per 1000 per ottenere i chilometri
    }
    public String co2Calculator(Route route, double co2Car) {
        if (route.getTravelMode().equals(Constants.DRIVE_CONSTANT)) {
            return co2Converter(convertMeter(route.getDistanceMeters()) * co2CarEngineProduction(co2Car));
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
                            case "HEAVY_RAIL" :
                                totalCO2 += Constants.CO2_PRODUCTION_METRO * route.getLegs().get(i).getSteps().get(j).getDistanceMeters()/1000;
                                break;
                            default:
                                break;

                        }
                    }
                }
            }
          return co2Converter(totalCO2);
        }
        if(route.getTravelMode().equals(Constants.WALK_CONSTANT)){
            return "0kg";
        }

        return "0kg";
    }

    public String co2Converter(double co2) {
        double kilograms = co2 / 1000;
        String format = "%." + 3 + "f";
        String formattedString = String.format(format, kilograms);
        return formattedString + "kg";
    }

    public int co2SavedProgressBar(double co2, double co2SavedCar, double co2SavedTransit, double co2SavedWalk){
        int co2Progress;
        co2Progress =(int)(co2*(100/(co2SavedCar + co2SavedTransit + co2SavedWalk)));
        return co2Progress;
    }

    public double co2CarEngineProduction(double co2Car){
        switch ((int)co2Car){
            case 0:
                return 0;
            case -1:
                return CO2_PRODUCTION_CAR_GASOLINE;
            case -2:
                return CO2_PRODUCTION_CAR_DIESEL;
            case -3:
                return CO2_PRODUCTION_CAR_GPL;
            case -4:
                return CO2_PRODUCTION_CAR_METHANE;
            case -5:
                return CO2_PRODUCTION_CAR_ELETTRIC;
            default:
                return co2Car;
        }
    }


    //conversione immagine BitMap a string per salvarla su realtime
    public static String bitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    public static Bitmap stringToBitmap(String encodedString) {
        byte[] decodedBytes = Base64.decode(encodedString, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

}
