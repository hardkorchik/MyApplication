package com.guap.guap_tinder;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private String message;
    private LocationManager locationManager;
    private double x;
    private double y;
    private Marker marker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Intent intent = getIntent();
        if(intent.hasExtra("message")) {
            message = intent.getStringExtra("message");
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ActivityCompat.requestPermissions(this,new String[]{"android.permission.ACCESS_FINE_LOCATION"}, 1);
        locationManager=(LocationManager)getSystemService(LOCATION_SERVICE);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        float zoom=20;
        LatLng sydney = new LatLng(x, y);
        marker=mMap.addMarker(new MarkerOptions().position(sydney).title(message));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,zoom));
    }
    @Override
    protected void onResume(){
        super.onResume();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000*30,10,locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,1000*30,10,locationListener);
        checkEnabled();
    }
    @Override
    protected void onPause(){
        super.onPause();
        locationManager.removeUpdates(locationListener);
    }
    private LocationListener locationListener =new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            x=location.getLatitude();
            y=location.getLongitude();
            marker.remove();
            onMapReady(mMap);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
           //записываем лог(типоХD)изменился провайдер
        }

        @Override
        public void onProviderEnabled(String s) {
            checkEnabled();
            x=(int)locationManager.getLastKnownLocation(s).getLatitude();
            y=(int)locationManager.getLastKnownLocation(s).getLongitude();
        }

        @Override
        public void onProviderDisabled(String s) {
            checkEnabled();
        }
    };
    private void checkEnabled(){
        locationManager.isProviderEnabled(locationManager.GPS_PROVIDER);
        locationManager.isProviderEnabled(locationManager.NETWORK_PROVIDER);
    }
}
