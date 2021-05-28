package com.testWebApp.focusshiftsimulator;

public class Pair {
    private Double x;
    private Double y;

    Pair(Double x, Double y){
        this.x = x;
        this.y = y;
    }

    public Double getKey(){
        return x;
    }

    public Double getValue(){
        return y;
    }
}
