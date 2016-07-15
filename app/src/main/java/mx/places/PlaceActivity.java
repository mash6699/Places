package mx.places;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import mx.places.adapter.PlaceAdapter;
import mx.places.iface.PlaceSelector;
import mx.places.model.Place;
import mx.places.model.PlaceList;
import mx.places.service.VolleyService;
import mx.places.utils.Const;
import mx.places.utils.RequestPlaces;
import mx.places.utils.Utils;

import static mx.places.utils.Const.ID_CAT;

/**
 * Created by miguel_angel on 8/07/16.
 */
public class PlaceActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, PlaceSelector {

    private final static String TAG = PlaceActivity.class.getName();
    private int CAT = 0;
    private String title = null;
    Context context;
    ProgressDialog progressDialog;

    Toolbar toolbar;
    RecyclerView recycler;
    PlaceAdapter placeAdapter;
    List<Place> placeList = new ArrayList<>();


    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private static final int REQUEST_CODE_LOCATION = 2;
    private LatLng latLng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.places_layout);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recycler = (RecyclerView) findViewById(R.id.recycler_view);

        context = this.getApplicationContext();

        if (getIntent().getExtras() != null) {
            CAT = getIntent().getExtras().getInt(ID_CAT);
            init(CAT);
        } else {
            CAT = Utils.getCat(getApplicationContext());
            init(CAT);
        }


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        placeAdapter = new PlaceAdapter(placeList, getApplicationContext(), this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recycler.setLayoutManager(layoutManager);
        recycler.setItemAnimator(new DefaultItemAnimator());
        recycler.setAdapter(placeAdapter);

        buildGoogleApiClient();

     //   loadService();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Obteniendo datos");
        progressDialog.show();
        progressDialog.setCancelable(false);

    }


    public void init(int cat){
        title = Utils.getNameCategory(cat);
        toolbar.setTitle(title);
        toolbar.setLogo(Utils.getIconByCategory(cat, getApplicationContext()));
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
            latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            Log.d(TAG, " Latitude: " + mLastLocation.getLatitude() + " Longitude: " + mLastLocation.getLongitude());
            loadService();
        } else {
            progressDialog.dismiss();
            Toast.makeText(this, R.string.no_location_detected, Toast.LENGTH_LONG).show();
            latLng = null;
        }
    }



    void loadService() {

//        progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Obteniendo datos");
//        progressDialog.show();

        String url = RequestPlaces.API_GET_LOCATION();

        final String json = Utils.getJsonLocation(CAT, latLng);
        Log.d(TAG,"LoadService: " + url);

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();

                        try {
                            PlaceList mPlaceList =  new Gson().fromJson(response, PlaceList.class);
                            Log.d(TAG, "tamanio " + placeList.size());

                            placeList.addAll(mPlaceList.getPlaceList());

                            placeAdapter.notifyDataSetChanged();

                            //TODO AQUI INICIA EL OTRO PROCESO DE EMPAREJAMIENTO
                          /*  if (mLastLocation != null) {
                                sendDistance();
                            }*/

                        } catch (Exception e) {
                            Log.e(TAG, e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse " + error.getMessage());
                progressDialog.dismiss();
            }
        })
        {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return json == null ? null : json.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                            json, "utf-8");
                    return null;
                }
            }
        };

        stringRequest.setRetryPolicy(Utils.getRetryPolicy());
        VolleyService.getInstance(this.getApplicationContext()).addToRequestQueue(stringRequest);

    }


    public void sendDistance(){
        Iterator<Place> iterator = placeList.iterator();
        while(iterator.hasNext()){
            Place place = iterator.next();
            Log.d(TAG, place.getCoordinates());
            serviceDistance(place);
        }

        progressDialog.dismiss();
    }


    public void serviceDistance(Place mPlace){
        Log.d(TAG,"serviceDistance");
        String [] loc = mPlace.getCoordinates().split(",");
        if(loc.length == 2 ) {
            LatLng lngOrig = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLatitude());
            LatLng lngDest = new LatLng(Double.parseDouble(loc[0].toString()), Double.parseDouble(loc[1].toString()));
            String url = RequestPlaces.API_GET_MATRIX(lngOrig, lngDest);

            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonObject = new JSONObject(response);

                                if(jsonObject.has("status") && jsonObject.getString("status").equals("OK")){

                                    String destAddress = jsonObject.getJSONArray("destination_addresses").toString();

                                    JSONObject duration = jsonObject.getJSONObject("duration");
                                    String sDuration = duration.getString("text");
                                    int iDuration = duration.getInt("value");

                                    JSONObject distance = jsonObject.getJSONObject("distance");
                                    String sDistance = distance.getString("text");
                                    int iDistance = duration.getInt("value");

                                    Log.d(TAG, "duration " + sDuration  + " " + iDuration);
                                    Log.d(TAG, "distance " + sDistance  + " " + iDistance);
                                }



                                Log.d(TAG, "matrix response  " + response.length());

                            } catch (Exception e) {
                                Log.e(TAG, e.getMessage());
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "onErrorResponse " + error.getMessage());
                    progressDialog.dismiss();
                }
            })
            {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }
            };

            stringRequest.setRetryPolicy(Utils.getRetryPolicy());
            VolleyService.getInstance(this.getApplicationContext()).addToRequestQueue(stringRequest);
        }
    }

    @Override
    public void selector(Place place) {
        place.setIdCat(CAT);
        Intent i = new Intent(this.getApplication(), PlaceDetailActivity.class);
        i.putExtra(Const.LAT, latLng.latitude);
        i.putExtra(Const.LON, latLng.longitude);
        i.putExtra(Const.PLACE, place);
        startActivity(i);
    }
}



/*
Asi responde matrix, emparejaria mediante cooordenadas
{
   "destination_addresses" : [ "19.69254,-101.1859869" ],
   "origin_addresses" : [ "19.4421072,19.4421072" ],
   "rows" : [
      {
         "elements" : [
            {
               "status" : "ZERO_RESULTS"
            }
         ]
      }
   ],
   "status" : "OK"
}


 */
