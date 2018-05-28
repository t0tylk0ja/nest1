package com.dominik.nestapp;

import android.content.Context;
import android.content.Intent;
import android.location.Geocoder;
import android.location.Address;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class FlatActivity extends AppCompatActivity implements OnMapReadyCallback {


    Bundle extras;
    HashMap<String,Dev> devList;
    Dev devToShow;

    private MapView mapView;
    private GoogleMap gmap;
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flat);

        extras = getIntent().getExtras();

        TextView tv1=(TextView) findViewById(R.id.textView);
        tv1.setText(extras.getString("name"));

        ImageView imageView1 = (ImageView) findViewById(R.id.imageView);
        String maxUrl = extras.getString("maxUrl");

        Glide
                .with(this)
                .load(maxUrl)
                .into(imageView1);
//dev info
        devList=new DevList().returnDevList();
        devToShow=devList.get(extras.getString("dev"));
        TextView tvDevName=(TextView) findViewById(R.id.devName);
        ImageView tvDevLogo = (ImageView) findViewById(R.id.devLogo);
        tvDevName.setText(devToShow.getDevName());

        Glide
                .with(this)
                .load(devToShow.getDevLogo())
                .into(tvDevLogo);

//flat info
        TextView tvArea=(TextView) findViewById(R.id.flatArea);
        TextView tvRooms=(TextView) findViewById(R.id.flatRooms);
        TextView tvFloor=(TextView) findViewById(R.id.flatFloor);

        tvArea.setText(String.valueOf(extras.getDouble("area"))+"m\u00B2");
        tvRooms.setText(String.valueOf(extras.getInt("rooms")));
        tvFloor.setText(String.valueOf(extras.getInt("floor")));
        //mapa
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }
    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
        gmap.setMinZoomPreference(12);
        gmap.setIndoorEnabled(true);
        gmap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this,R.raw.style_json));
        UiSettings uiSettings = gmap.getUiSettings();
        uiSettings.setIndoorLevelPickerEnabled(true);
        uiSettings.setMyLocationButtonEnabled(true);
        uiSettings.setMapToolbarEnabled(true);
        uiSettings.setCompassEnabled(true);
        uiSettings.setZoomControlsEnabled(true);
        LatLng LL = getLocationFromAddress(this,extras.getString("address"));

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(LL);
        gmap.addMarker(markerOptions);

        gmap.moveCamera(CameraUpdateFactory.newLatLng(LL));
    }

    public LatLng getLocationFromAddress(Context context,String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }


}
