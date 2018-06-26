package com.dominik.nestapp;

import android.content.Context;
import android.content.Intent;
import android.location.Geocoder;
import android.location.Address;
import android.location.Location;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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
    Flat flatToShow;
    private List<Flat> mFlatList;
    private FlatDBHelper dbHelper;
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

        dbHelper = FlatDBHelper.getInstanse(this);
        mFlatList=dbHelper.flatList("");

        String name =extras.getString("name");
        for(Flat x:mFlatList){
            if(x.getName().equals(name)){
                flatToShow=x;
            }
        }

        ImageView imageView1 = (ImageView) findViewById(R.id.imageView);
        String maxUrl = flatToShow.getMaxUrl();

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

        tvDevLogo.setImageResource(devToShow.getDevLogo());

//flat info
        TextView tvArea=(TextView) findViewById(R.id.flatArea);
        TextView tvRooms=(TextView) findViewById(R.id.flatRooms);
        TextView tvFloor=(TextView) findViewById(R.id.flatFloor);
        TextView tvStorage=(TextView) findViewById(R.id.flatStorage);

        tvArea.setText(String.valueOf(flatToShow.getArea())+"m\u00B2");
        tvStorage.setText(String.valueOf(flatToShow.getStorage())+"m\u00B2");
        tvRooms.setText(String.valueOf(flatToShow.getRooms()));
        tvFloor.setText(String.valueOf(flatToShow.getFloor()));

        ImageView heartImageView = (ImageView) findViewById(R.id.heartView);
        if(flatToShow.getLoved()==0){
            heartImageView.setVisibility(View.INVISIBLE);
        }
//additional info
        ImageView balconyView = (ImageView)findViewById(R.id.balconyView);

        if(flatToShow.getBalcony()==1){
            balconyView.setImageDrawable(getDrawable(R.drawable.balcony_yes));
        }else{balconyView.setImageDrawable(getDrawable(R.drawable.balcony_no));}

        ImageView parkingView = (ImageView)findViewById(R.id.parkingView);

        if(flatToShow.getParking()==1){
            parkingView.setImageDrawable(getDrawable(R.drawable.car_yes));
        }else{parkingView.setImageDrawable(getDrawable(R.drawable.car_no));}

        ImageView equipView = (ImageView)findViewById(R.id.equipView);

        if(flatToShow.getEquip()==1){
           equipView.setImageDrawable(getDrawable(R.drawable.equip_yes));
        }else{equipView.setImageDrawable(getDrawable(R.drawable.equip_no));}

        ImageView gardenView = (ImageView)findViewById(R.id.gardenView);

        if(flatToShow.getGarden()==1){
            gardenView.setImageDrawable(getDrawable(R.drawable.garden_yes));
        }else{gardenView.setImageDrawable(getDrawable(R.drawable.garden_no));}
//distances
        setDistances();
        ImageView centreView = (ImageView) findViewById(R.id.centreView);
        centreView.setImageDrawable(getDrawable(R.drawable.centre));
        ImageView railwayView = (ImageView) findViewById(R.id.railwayView);
        railwayView.setImageDrawable(getDrawable(R.drawable.train));
        ImageView airportView = (ImageView) findViewById(R.id.airportView);
        airportView.setImageDrawable(getDrawable(R.drawable.plane));



//mapa
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);
//intencja mail
        Button offerButton = (Button) findViewById(R.id.offerButton);

        offerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{devToShow.getDevEmail()});
                i.putExtra(Intent.EXTRA_SUBJECT, "Flat enquiry: "+flatToShow.getName()+" /"+flatToShow.getArea()+"m\u00B2");
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(FlatActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }

        });
//galeria
        Button galleryButton = (Button) findViewById(R.id.galleryButton);
        galleryButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Context viewContext= view.getContext();
                Intent intent = new Intent(viewContext, GalleryActivity.class);
                intent.putExtra("flatName",flatToShow.getName());
                startActivity(intent);
            }

        });
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
        Address flatAddress=getAddress(this,flatToShow.getAddress());
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
        gmap.setMinZoomPreference(14);
        gmap.setIndoorEnabled(true);
        gmap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this,R.raw.style_json));
        UiSettings uiSettings = gmap.getUiSettings();
        uiSettings.setIndoorLevelPickerEnabled(true);
        uiSettings.setMyLocationButtonEnabled(true);
        uiSettings.setMapToolbarEnabled(true);
        uiSettings.setCompassEnabled(true);
        uiSettings.setZoomControlsEnabled(true);
        LatLng LL = getLocationFromAddress(this,flatToShow.getAddress());

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
