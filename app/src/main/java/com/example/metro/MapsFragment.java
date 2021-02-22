package com.example.metro;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragment extends AppCompatActivity implements OnMapReadyCallback  {
    static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS=23;
              private GoogleMap mMap;
              Button btn;

             @Override
              protected void onCreate(Bundle savedInstanceState) {
                  super.onCreate(savedInstanceState);
                  setContentView(R.layout.activity_main);

                  checkPermission();
              }

        @Override
              public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
                  switch (requestCode) {
                      case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                          if (grantResults.length > 0
                                                     && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                              SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                                                   mapFragment.getMapAsync(this);
                          } else {
                              // permission denied, boo! Disable the
                              // functionality that depends on this permission.
                          }
                          return;
                      }
                  }
              }


        @Override
        public void onMapReady(GoogleMap googleMap) {
            LatLng sydney = new LatLng(-34, 151);
            googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

            mMap = googleMap;
               //  mMap.setMyLocationEnabled(true);
                  mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                      @Override
                      public void onMyLocationChange(Location location) {
                          LatLng latlng=new LatLng(location.getLatitude(),location.getLongitude());
                          MarkerOptions markerOptions = new MarkerOptions();
                          markerOptions.position(latlng);

                          markerOptions.title("My Marker");
                          mMap.clear();
                          CameraUpdate cameraUpdate= CameraUpdateFactory.newLatLngZoom(
                                                     latlng, 15);
                          mMap.animateCamera(cameraUpdate);
                          mMap.addMarker(markerOptions);

                      }
                  });


        }


    private void checkPermission(){
                 if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION )
                                 != PackageManager.PERMISSION_GRANTED ) {

                      ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  },
                                         MY_PERMISSIONS_REQUEST_READ_CONTACTS );
                  }
                  else{
                      SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                      mapFragment.getMapAsync(this);
                  }

              }
}