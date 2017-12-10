package com.guap.guap_tinder;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;
public class MapsActivity extends FragmentActivity implements  OnMapReadyCallback{
    private GoogleMap mMap;
    private String registration;
    private LocationManager locationManager;
    private PrintWriter out;
    private double x;
    private double y;
    private Marker marker;
    private ArrayList<Marker> list_marker;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Intent intent = getIntent();
        if(intent.hasExtra("registration")) {
            registration = intent.getStringExtra("registration");
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ActivityCompat.requestPermissions(this,new String[]{"android.permission.ACCESS_FINE_LOCATION"}, 1);
        locationManager=(LocationManager)getSystemService(LOCATION_SERVICE);
        list_marker.clear();
        onMapReady(mMap);
        if(GLOBAL.massage!=null){
            ///ВЫВОДИМ СООБЩЕНИЕ НАД МЕТКОЙ
        }
    }
    public void send_massage(View view){
        try {
            out = new PrintWriter(GLOBAL.socket.getOutputStream());
            String outMessage;
            TextView massage = (TextView) findViewById(R.id.massage);         //ОТПРАВЛЯЮ СООБЩЕНИЕ
            outMessage='1'+registration+'/'+massage;
            out.println(outMessage);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        float zoom=20;
        for (Map.Entry entry : GLOBAL.mark.entrySet()) {
            //LatLng sydney = new LatLng(x, y);
            list_marker.add(mMap.addMarker(new MarkerOptions().position((LatLng)entry.getValue()).title((String)entry.getKey())));
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(list_marker.get(0).getPosition(), zoom));
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
            try {
                out = new PrintWriter(GLOBAL.socket.getOutputStream());
                String outMessage;                                          //ОТПРАВЛЯЮ КООРДИНАТЫ
                outMessage='2'+registration+'/'+ x+'/'+y+'/';
                out.println(outMessage);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
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
        locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
}
