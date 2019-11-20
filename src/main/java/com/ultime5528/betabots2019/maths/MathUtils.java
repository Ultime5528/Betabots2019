package com.ultime5528.betabots2019.maths;

/**
 * MathUtils
 */
public class MathUtils {

    public static double absArgmax(double... arr){
        double largest = 0;
        for(double x : arr) {

            if(Math.abs(x) > largest){
                largest = Math.abs(x);
            }
        }

        return largest;
    }

    public static double deadzone(double v, double min){
        if(v > min) return (v-min)/(1-min);
        else if(v < -min) return (v+min)/(1-min);
        else return 0;
    }
}