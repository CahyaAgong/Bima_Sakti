package com.bimasakti.diskominfosan.location;

public class getLocation {

    double latitude;
    double longitude;
    String status;
    String token;
    String key;

//    public getLocation(double latitude, double longitude, String status, String token) {
//        this.latitude = latitude;
//        this.longitude = longitude;
//        this.status = status;
//        this.token = token;
//    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
