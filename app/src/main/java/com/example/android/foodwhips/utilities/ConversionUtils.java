package com.example.android.foodwhips.utilities;

public class ConversionUtils {
    public static String secondsToHrsMins(String timeInSecs){
        String finalTime = "";
        int time = Integer.parseInt(timeInSecs);
        time = Math.round(time/60);
        finalTime = Integer.toString(time) + " minutes";

        if(time > 60){
            int hr = time / 60;
            int min = (time % 60);

            finalTime = Integer.toString(hr) +
                    (hr > 1 ? " hours and " : " hour and ") +
                    Integer.toString(min) +
                    (min > 1 ? " minutes" : " minute");
        }
        return finalTime;
    }

    public static String starRating(String rating){
        String stars = "";
        for(int i = 0; i < Integer.parseInt(rating); i++){
            stars += "★";
        }
        return stars;
    }
}
