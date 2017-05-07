package com.darelbitsy.dbweather.utils.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.darelbitsy.dbweather.utils.helper.DatabaseOperation;
import com.darelbitsy.dbweather.utils.holder.ConstantHolder;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import static com.darelbitsy.dbweather.utils.holder.ConstantHolder.IS_GPS_PERMISSION_GRANTED;
import static com.darelbitsy.dbweather.utils.holder.ConstantHolder.LOCATION_UPDATE;
import static com.darelbitsy.dbweather.utils.holder.ConstantHolder.PREFS_NAME;

/**
 * Created by Darel Bitsy on 23/02/17.
 * Service that get the user location
 * request user location
 */

@SuppressWarnings("MissingPermission")
public class LocationTracker extends Service implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener,
        LocationListener {

    private LocationManager mLocationManager;
    private GoogleApiClient mGoogleApiClient;


    @Override
    public int onStartCommand(final Intent intent, final int flags, final int startId) {
        Log.i(ConstantHolder.TAG, "Inside Registered request Location update, and service started");

        //Configuring the google api client
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();

        mGoogleApiClient.connect();
        mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        final boolean isGpsPermissionOn = getApplicationContext().getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
                .getBoolean(IS_GPS_PERMISSION_GRANTED, false);

        if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && isGpsPermissionOn) {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    300000,
                    0,
                    this);

            if (mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        300000,
                        0,
                        this);
            }
        }

        return START_NOT_STICKY;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(ConstantHolder.TAG, "Inside the onConnected for google api client");
        getLocation();
    }

    @Override
    public void onConnectionSuspended(final int i) {
        //Empty for now, will be implemented later if needed
    }

    @Override
    public void onConnectionFailed(@NonNull final ConnectionResult connectionResult) {
        //Leaving this empty because if not available we will use the gps card
    }

    @Override
    public void onLocationChanged(final Location location) {
        Log.i(ConstantHolder.TAG, "Inside the service location , the location just changed");
        sendLocationToActivity(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        //Not Using it for now
    }

    @Override
    public void onProviderEnabled(String provider) {
        //Not using it for now
    }

    @Override
    public void onProviderDisabled(String provider) {
        Intent openGpsSetting = new Intent(Settings.ACTION_LOCALE_SETTINGS);
        openGpsSetting.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(openGpsSetting);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        if(mGoogleApiClient.isConnected()) {

            Log.i(ConstantHolder.TAG, "Inside the onDisconnect for google api client");
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();

        }

        if (mLocationManager != null) {
            mLocationManager.removeUpdates(this);
        }

        super.onTaskRemoved(rootIntent);
    }

    /**
     * get last know location
     * if not available request location update
     */
    private void getLocation() {
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, createLocationRequest(),  this);

        } else {
            Log.i(ConstantHolder.TAG, "Inside the getLocation method, i find location");
            sendLocationToActivity(location);
        }
    }


    /**
     * Method that create an LocationRequest
     * with specific parameter
     * @return LocationRequest
     */
    private LocationRequest createLocationRequest() {
        return LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(300000) // Seconds, in milliseconds
                .setFastestInterval(1000); // 1 Seconds, in milliseconds
    }

    /**
     * Send location details to activity
     * by an broadcast request
     * @param location represent the user location
     */
    private void sendLocationToActivity(final Location location) {
        final Intent coordinateIntent = new Intent(LOCATION_UPDATE);

        if (DatabaseOperation.getInstance(getApplicationContext())
                .saveCoordinates(location.getLatitude(), location.getLongitude())) {
            sendBroadcast(coordinateIntent);
        }
    }

}