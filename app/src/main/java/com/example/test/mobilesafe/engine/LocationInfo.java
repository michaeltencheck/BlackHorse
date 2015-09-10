package com.example.test.mobilesafe.engine;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.test.suitebuilder.annotation.LargeTest;

import java.security.Provider;

/**
 * Created by test on 9/9/2015.
 */
public class LocationInfo {
    private static LocationInfo locationInfo;
    private static Context context;
    private static MyLocationListener listener;

    private LocationInfo() {
    }

    public static synchronized LocationInfo getInstance(Context context) {
        if (locationInfo == null) {
            locationInfo = new LocationInfo();
            LocationInfo.context = context;
        }
        return locationInfo;
    }

    public String getLocationInfo() {
        LocationManager locationManager = (LocationManager)
                context.getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        criteria.setAltitudeRequired(false);
        criteria.setAccuracy(60000);
        criteria.setSpeedAccuracy(3000);
        String provider = locationManager.getBestProvider(criteria, true);


        locationManager.requestLocationUpdates(provider, 6000, 50, getListener());
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);

        String locationInfo = sp.getString("location", "");
        return locationInfo;

    }

    private synchronized MyLocationListener getListener() {
        if (listener == null) {
            listener = new MyLocationListener();
        }
        return listener;
    }

    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            String longitude = "jingdu : " + location.getLongitude();
            String latitude = "weidu : " + location.getLatitude();
            String locationInfo = longitude + "----" + latitude;
            SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("location", locationInfo);
            editor.commit();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }

}
