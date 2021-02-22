package com.example.metro;

import android.location.Location;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MetroStations {
    String name;
    float Slat;


    float Slong;

    @Override
    public boolean equals(@Nullable Object obj) {
       boolean isEqual = false;
        if (obj != null) {
            MetroStations st = (MetroStations) obj;
            if (st.name.equals(this.name)) {
                isEqual= true;
            }
        } else{ isEqual= false;}
        return isEqual;
    }


    public MetroStations(String name, float slat, float slong) {
        this.name = name;
        Slat = slat;
        Slong = slong;
    }


    public String getName() {
        return name;
    }

    public float getSlat() {
        return Slat;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setSlat(float slat) {
        Slat = slat;
    }

    public void setSlong(float slong) {
        Slong = slong;
    }

    public float getSlong() {
        return Slong;
    }


}
