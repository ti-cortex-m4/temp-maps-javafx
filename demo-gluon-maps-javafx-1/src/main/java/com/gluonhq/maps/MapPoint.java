package com.gluonhq.maps;

import javafx.beans.NamedArg;

/**
 *
 */
public class MapPoint {

    private double latitude, longitude;

    public MapPoint(@NamedArg("latitude") double lat, @NamedArg("longitude") double lon) {
        this.latitude = lat;
        this.longitude = lon;
    }
    
    public double getLatitude() {
        return this.latitude;
    }
    
    public double getLongitude() {
        return this.longitude;
    }
    
    public void update(double lat, double lon) {
        this.latitude = lat;
        this.longitude = lon;
    }
    
}
