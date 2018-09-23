package com.example.a22686.timecalculation;

import org.litepal.crud.LitePalSupport;

public class Sum extends LitePalSupport {


    private double timeSum;
    private double moneySum;
    private double price;

    public double getTimeSum() {
        return timeSum;
    }

    public void setTimeSum(double timeSum) {
        this.timeSum = timeSum;
    }


    public double getMoeneySum() {
        return moneySum;
    }

    public void setMoeneySum(double moeneySum) {
        this.moneySum = moeneySum;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
