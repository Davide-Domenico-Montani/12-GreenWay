package it.unimib.greenway.util;

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

}
