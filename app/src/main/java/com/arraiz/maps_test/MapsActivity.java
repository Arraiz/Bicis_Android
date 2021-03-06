package com.arraiz.maps_test;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapsActivity extends AppCompatActivity  implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mGoogleMap;
    public static final String BASE_URL = "http://api.arraiz.eus/";

    //por cuestiones de seguridad la cabecera es difernte a esta!
    public static final String cabeceraBici = "thisisbicisapi";
    private static final int MY_PERMISSION_REQUEST_FINE_LOCATION = 101;
    private static final int MY_PERMISSION_REQUEST_COARSE_LOCATION = 102;

    private static ArrayList<StationModel> sStationModels = new ArrayList<>();


    private static TextView mNameTV;
    private static TextView mBicisTV;
    private static TextView mAnclajesTV;
    private SimpleCursorAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        mNameTV=findViewById(R.id.textView_name);
        mAnclajesTV=findViewById(R.id.textView_anclajes);
        mBicisTV=findViewById(R.id.textView_bicis);


        final String[] from = new String[] {"Estacion"};

        final int[] to = new int[] {R.id.estacion_tv_search};

        mAdapter = new SimpleCursorAdapter(this,
                R.layout.sugestion_layout,
                null,
                from,
                to,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);



        //retrofit configuration
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final APIbicis apIbicis = retrofit.create(APIbicis.class);

        Call<List<StationModel>> getBicis = apIbicis.getStations();

        getBicis.enqueue(new Callback<List<StationModel>>() {
            @Override
            public void onResponse(Call<List<StationModel>> call, Response<List<StationModel>> response) {


                for (int i =0;i<response.body().size();i++) {
                    sStationModels.add(response.body().get(i));
                }
                for (int i=0;i<sStationModels.size();i++){

                    mGoogleMap.addMarker(new MarkerOptions()
                            .icon(BitmapDescriptorFactory.fromResource(getResources().getIdentifier("marker_"+sStationModels.get(i).getBicisLibres(),"mipmap",getPackageName())))
                            .position(new LatLng(Double.valueOf(sStationModels.get(i).getLat()),Double.valueOf(sStationModels.get(i).getLon()))));
                }
            }

            @Override
            public void onFailure(Call<List<StationModel>> call, Throwable t) {
                Toast.makeText(MapsActivity.this, "No internet conecction", Toast.LENGTH_SHORT).show();
            }
        });




        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        getSupportActionBar().show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mGoogleMap.setOnMarkerClickListener(this);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //User has previously accepted this permission
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mGoogleMap.setMyLocationEnabled(true);
            }
        } else {
            //Not in api-23, no need to prompt
            mGoogleMap.setMyLocationEnabled(true);
        }



    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                //  TODO: Prompt with explanation!

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay!
                    if (ActivityCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        mGoogleMap.setMyLocationEnabled(true);
                    }
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.search_station);

        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setSuggestionsAdapter(mAdapter);
        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int i) {
                Log.d("MARKER",sStationModels.get(i).getNombre());
                return false;
            }

            @Override
            public boolean onSuggestionClick(int i) {

                Cursor cursor = (Cursor) mAdapter.getItem(i);
                String s=cursor.getString(0);

                Log.d("SEAR",sStationModels.get(Integer.valueOf(s)).getNombre());

                LatLng coordinate = new LatLng(Double.valueOf(sStationModels.get(Integer.valueOf(s)).getLat()), Double.valueOf(sStationModels.get(Integer.valueOf(s)).getLon())); //Store these lat lng values somewhere. These should be constant.
                CameraUpdate location = CameraUpdateFactory.newLatLngZoom(
                        coordinate, 15);
                mGoogleMap.animateCamera(location);
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                populateAdapter(s);
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    private void populateAdapter(String query) {
        final MatrixCursor c = new MatrixCursor(new String[]{BaseColumns._ID, "Estacion"});
        for (int i = 0; i < sStationModels.size(); i++) {

            if(query.length()>1) {
                if (sStationModels.get(i).getNombre().toLowerCase().contains(query.toLowerCase()))
                    c.addRow(new Object[]{i, sStationModels.get(i).getNombre()});
            }else{
                if (sStationModels.get(i).getNombre().toLowerCase().indexOf(query.toLowerCase())>=0){
                    c.addRow(new Object[]{i, sStationModels.get(i).getNombre()});
                }
            }
        }
        mAdapter.changeCursor(c);
        mAdapter.notifyDataSetChanged();


    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        //TOOD fixear los indices de la
               Log.d("MARKER",marker.getId().substring(1));
               Log.d("MARKER",sStationModels.get(Integer.valueOf(marker.getId().substring(1))).getId().substring(2));
                mNameTV.setText(sStationModels.get(Integer.valueOf(marker.getId().substring(1))).getNombre());


        return false;
    }




}
