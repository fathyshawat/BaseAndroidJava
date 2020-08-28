package com.example.javabase.MyBase.Gps;

public interface GpsTrakerListener {
    void onTrackerSuccess(Double lat, Double log);

    void onStartTracker();
}
