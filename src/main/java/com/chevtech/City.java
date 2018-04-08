package com.chevtech;

import org.postgresql.geometric.PGpoint;

public class City {
    String name;
    PGpoint location;

    public City() { }

    public City(String name, PGpoint location){
        this.name = name;
        this.location = location;
    }

    public String getName(){ return name; }
    public PGpoint getLocation(){ return location; }
}
