package com.example.ezavedyraksaputra;

import android.app.Application;
import android.content.res.Configuration;

import com.androidnetworking.AndroidNetworking;

public class StartApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Required initialization logic here!
        AndroidNetworking.initialize(getApplicationContext());
    }

    // Called by the system when the device configuration changes while your component is running.
    // Overriding this method is totally optional!
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    // This is called when the overall system is running low on memory,
    // and would like actively running processes to tighten their belts.
    // Overriding this method is totally optional!
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}