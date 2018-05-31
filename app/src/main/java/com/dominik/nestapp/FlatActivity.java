package com.dominik.nestapp;

import android.content.Context;
import android.content.Intent;
import android.location.Geocoder;
import android.location.Address;
import android.location.Location;
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
    TextView tvCentre;
    TextView tvAirport;
    TextView tvRailway;


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
        TextView tvStorage=(TextView) findViewById(R.id.flatStorage);

        tvArea.setText(String.valueOf(extras.getDouble("area"))+"m\u00B2");
        tvRooms.setText(String.valueOf(extras.getInt("rooms")));
        tvFloor.setText(String.valueOf(extras.getInt("floor")));

//additional info
        ImageView balconyView = (ImageView)findViewById(R.id.balconyView);
        String balconyIcon = extras.getInt("balcony") == 1 ? "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/icons%2Fbalcony_yes.png?alt=media&token=4111be0a-daaa-4cf6-91ef-3fc59475ae3a" : "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/icons%2Fbalcony_no.png?alt=media&token=8f16d18c-fdbb-44c2-86f7-86679b17efeb";
        Glide
                .with(this)
                .load(balconyIcon)
                .into(balconyView);

        ImageView parkingView = (ImageView)findViewById(R.id.parkingView);
        String parkingIcon = extras.getInt("parking") == 1 ? "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/icons%2Fcar_yes.png?alt=media&token=7851d496-cf4f-4028-ad7b-034c266431ce" : "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/icons%2Fcar_no.png?alt=media&token=f40cae3b-a6da-43f4-aa97-168bfabe5144";
        Glide
                .with(this)
                .load(parkingIcon)
                .into(parkingView);

        ImageView equipView = (ImageView)findViewById(R.id.equipView);
        String equipIcon = extras.getInt("equip") == 1 ? "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/icons%2Fequip_yes.png?alt=media&token=8de79ae5-1fc7-4fbf-9a68-851852fd7986" : "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/icons%2Fequip_no.png?alt=media&token=c3565eb0-1ceb-4df1-9162-27c2daac7886";
        Glide
                .with(this)
                .load(equipIcon)
                .into(equipView);

        ImageView gardenView = (ImageView)findViewById(R.id.gardenView);
        String gardenIcon = extras.getInt("garden") == 1 ? "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/icons%2Fgarden_yes.png?alt=media&token=7124ca76-fd77-43ef-b7d4-0924f41abc73" : "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/icons%2Fgarden_no.png?alt=media&token=bef23ff9-9677-4f63-bef1-8fb700b9a836";
        Glide
                .with(this)
                .load(gardenIcon)
                .into(gardenView);
//distances
        setDistances();
        ImageView centreView = (ImageView) findViewById(R.id.centreView);
        ImageView railwayView = (ImageView) findViewById(R.id.airportView);
        ImageView airportView = (ImageView) findViewById(R.id.railwayView);

        Glide
                .with(this)
                .load("https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/icons%2Fcentre.png?alt=media&token=763b47d8-a953-485c-b979-c7b2edacbc32")
                .into(centreView);

        Glide
                .with(this)
                .load("https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/icons%2Ftrain.png?alt=media&token=2d591ee8-b9dc-4ae0-80a4-9493452a4859")
                .into(airportView);

        Glide
                .with(this)
                .load("https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/icons%2Fplane.png?alt=media&token=3f8a8329-46e8-483b-84c6-4c9f1c11afb6")
                .into(railwayView);


//mapa
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);

    }

    public void setDistances(){
        //railway
        Location railwayStation=new Location("railway");
        railwayStation.setLatitude(51.098767);
        railwayStation.setLongitude(17.036519);
        //city centre
        Location cityCentre=new Location("cityCentre");
        cityCentre.setLatitude(51.109454);
        cityCentre.setLongitude(17.031332);
        //airport
        Location airport=new Location("airport");
        airport.setLatitude(51.104165);
        airport.setLongitude(16.880933);




        Location flatLocation=new Location("flatLocation");
        Address flatAddress=getAddress(this,extras.getString("address"));
        flatLocation.setLatitude(flatAddress.getLatitude());
        flatLocation.setLongitude(flatAddress.getLongitude());

        double railwayDistance = railwayStation.distanceTo(flatLocation)/1000;
        double cityDistance = cityCentre.distanceTo(flatLocation)/1000;
        double airportDistance = airport.distanceTo(flatLocation)/1000;

        tvAirport = (TextView)findViewById(R.id.distanceAirport);
        tvRailway = (TextView)findViewById(R.id.distanceRailway);
        tvCentre = (TextView)findViewById(R.id.distanceCentre);
        tvAirport.setText(String.format("%.2f",airportDistance)+"km");
        tvRailway.setText(String.format("%.2f",railwayDistance)+"km");
        tvCentre.setText(String.format("%.2f",cityDistance)+"km");

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

    public Address getAddress(Context context,String address){
        Geocoder coder = new Geocoder(context);
        List<Address> address2;
        Address location=null;

        try {
            // May throw an IOException
            address2 = coder.getFromLocationName(address, 5);

            location = address2.get(0);

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return location;
    }


}
