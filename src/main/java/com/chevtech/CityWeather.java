package com.chevtech;

import java.time.LocalDate;

public class CityWeather {
    String city;
    int temp_lo;
    int temp_hi;
    double prcp;
    LocalDate date;

    public CityWeather() { }

    public CityWeather(String city, int temp_lo, int temp_hi, double prcp, LocalDate date){
        this.city = city;
        this.temp_lo = temp_lo;
        this.temp_hi = temp_hi;
        this.prcp = prcp;
        this.date = date;
    }

    public String getCity(){ return city; }
    public int getTemp_lo(){ return temp_lo; }
    public int getTemp_hi(){ return temp_hi; }
    public double getPrcp() { return prcp; }
    public LocalDate getDate() { return date; }
}
