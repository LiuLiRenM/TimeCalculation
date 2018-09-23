package com.example.a22686.timecalculation;

import org.litepal.crud.LitePalSupport;

/*
这是一个典型的Java bean，写成这样可以与数据库中的Schedule表形成映射
 */
public class Schedule extends LitePalSupport {

    private String date;
    private double morningtime;
    private double afternoontime;
    private double nighttime;
    private double remakestime;
    private String remakes;

    public String getRemakes() {
        return remakes;
    }

    public void setRemakes(String remakes) {
        this.remakes = remakes;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getMorningtime() {
        return morningtime;
    }

    public void setMorningtime(double morningtime) {
        this.morningtime = morningtime;
    }
    public double getAfternoontime() {
        return afternoontime;
    }

    public void setAfternoontime(double afternoontime) {
        this.afternoontime = afternoontime;
    }

    public double getNighttime() {
        return nighttime;
    }

    public void setNighttime(double nighttime) {
        this.nighttime = nighttime;
    }

    public double getRemakestime() {
        return remakestime;
    }

    public void setRemakestime(double remakestime) {
        this.remakestime = remakestime;
    }



}
