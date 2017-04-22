package com.example.sam.duluthbikes;

/**
 * Created by Ruta on 22.04.2017.
 */

public class UnitConverter {

    public String convertHoursMinSecToString(long time){
        int sec = (int) (time / 1000) % 60 ;
        int min = (int) ((time / (1000*60)) % 60);
        int hours = (int) ((time / (1000*60*60)) % 24);

        String converted = Integer.toString(hours)+"h "+Integer.toString(min)+"min "+Integer.toString(sec)+"sec";
        return converted;
    }


    public double getDistInKm(Double distance){
        return distance/1000;
    }

    public double getDistInMiles(Double distance){
        return distance*0.000621371;
    }

    public double getKmPerHour(Double distance, Long timelapse){

        return (getDistInKm(distance)/(timelapse/1000))*3600;
    }

    public double getMilesPerHour(Double distance, Long timelapse){

        return (getDistInMiles(distance)/(timelapse/1000))*3600;
    }

}
