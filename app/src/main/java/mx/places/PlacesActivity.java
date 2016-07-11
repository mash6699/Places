package mx.places;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;

import mx.places.adapter.PlaceAdapter;
import mx.places.model.Place;
import mx.places.service.VolleyService;
import mx.places.utils.RequestPlaces;
import mx.places.utils.Utils;

import static mx.places.utils.Const.ID_CAT;

/**
 * Created by miguel_angel on 8/07/16.
 */
public class PlacesActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private final static String TAG = PlacesActivity.class.getName();
    private int CAT = 0;
    private String title = null;

    Toolbar toolbar;
    RecyclerView recycler;
    PlaceAdapter placeAdapter;
    List<Place> placeList = new ArrayList<>();

    String json = "";

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private static final int REQUEST_CODE_LOCATION = 2;

    // SUMAYA 19.4406976, -99.2068888

    //https://maps.googleapis.com/maps/api/distancematrix/json?origins=19.4421072,-99.2053086&destinations=19.4406976,-99.2068888&mode=bicycling&language=mx-MX

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.places_layout);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recycler = (RecyclerView) findViewById(R.id.recycler_view);

        if (getIntent().getExtras() != null) {
            CAT = getIntent().getExtras().getInt(ID_CAT);
            title = Utils.getNameCategory(CAT);
            toolbar.setTitle(title);
            toolbar.setLogo(Utils.getIconByCategory(CAT, getApplicationContext()));
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        placeAdapter = new PlaceAdapter(placeList, getApplicationContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recycler.setLayoutManager(layoutManager);
        recycler.setItemAnimator(new DefaultItemAnimator());
        recycler.setAdapter(placeAdapter);

        buildGoogleApiClient();

        loadPlacesData();

    }

    protected synchronized void buildGoogleApiClient(){
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    public void loadPlacesData() {
        Place place = new Place("lugar", "horario", "distancia", "ranking");
        placeList.add(place);
        Place place1 = new Place("lugar", "horario", "distancia", "ranking");
        placeList.add(place1);
        Place place2 = new Place("lugar", "horario", "distancia", "ranking");
        placeList.add(place2);
        Place place3 = new Place("lugar", "horario", "distancia", "ranking");
        placeList.add(place3);
        Place place4 = new Place("lugar", "horario", "distancia", "ranking");
        placeList.add(place4);

        placeAdapter.notifyDataSetChanged();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG, "onConnected");
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_LOCATION);

        } else {
            getMyLocation();
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult");
        if (requestCode == REQUEST_CODE_LOCATION) {
            if(grantResults.length == 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getMyLocation();
            }
        }
    }

    protected void getMyLocation () {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if(mLastLocation != null) {
            Log.d(TAG, " Latitude: " + mLastLocation.getLatitude() + " Longitude: " + mLastLocation.getLongitude());
        } else {
            Toast.makeText(this, R.string.no_location_detected, Toast.LENGTH_LONG).show();
        }
    }



    void loadService() {
        String url = RequestPlaces.getAPI_GET_LOCATION();

        //RequestQueue queue = VolleyService.getInstance(this.getApplicationContext()).getRequestQueue();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        VolleyService.getInstance(this.getApplicationContext()).addToRequestQueue(stringRequest);
    }



}



