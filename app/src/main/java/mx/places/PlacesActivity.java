package mx.places;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.places.adapter.PlaceAdapter;
import mx.places.model.Place;
import mx.places.model.PlaceList;
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
    Context context;
    ProgressDialog progressDialog;

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

    String jsonPlaces =
            "{\"Places\" :[\n" +
            "  {\n" +
            "    \"fnIdLugar\": 31,\n" +
            "    \"fcNombre\": \"Proveedora De Refacciones Casemar Sa De Cv\",\n" +
            "    \"fcHorario\": \"09:00 - 21:00 HRS\",\n" +
            "    \"fncalificacion\": 1,\n" +
            "    \"fcDireccion\": \"Av. Rayon Norte Y Calle Negrete S/N Centro, Cp. 30640, Huixtla, Chiapas\",\n" +
            "    \"fcCoordenadas\": \"15.141713,-92.4630238\",\n" +
            "    \"fcTelefono1\": \"(964) 642 1199\",\n" +
            "    \"fcTelefono2\": null,\n" +
            "    \"fcTelefono3\": null,\n" +
            "    \"fcTelefono4\": null,\n" +
            "    \"fcResponsable1\": \"Juan Carlos Lopez Sanchez\",\n" +
            "    \"fcResponsable2\": null\n" +
            "  },\n" +
            "  {\n" +
            "    \"fnIdLugar\": 51,\n" +
            "    \"fcNombre\": \"Juana Susana Garcia Seturino Y/O Proveedora De Llantas \",\n" +
            "    \"fcHorario\": \"09:00 - 21:00 HRS\",\n" +
            "    \"fncalificacion\": 3,\n" +
            "    \"fcDireccion\": \"Costa No. 419 Nte. Zona Centro, Durango Durango, C.P. 34000\",\n" +
            "    \"fcCoordenadas\": \"24.0312662,-104.6782826\",\n" +
            "    \"fcTelefono1\": \"(618) 812 5868\",\n" +
            "    \"fcTelefono2\": null,\n" +
            "    \"fcTelefono3\": null,\n" +
            "    \"fcTelefono4\": null,\n" +
            "    \"fcResponsable1\": \"Maria Magdalena Ávila Martínez\",\n" +
            "    \"fcResponsable2\": null\n" +
            "  }\n"
            +
            " ]\n" +
            "}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.places_layout);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recycler = (RecyclerView) findViewById(R.id.recycler_view);

        context = this.getApplicationContext();

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

      //  loadService();


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
/*        Place place = new Place("lugar", "horario", "distancia", "ranking");
        placeList.add(place);
        Place place1 = new Place("lugar", "horario", "distancia", "ranking");
        placeList.add(place1);
        Place place2 = new Place("lugar", "horario", "distancia", "ranking");
        placeList.add(place2);
        Place place3 = new Place("lugar", "horario", "distancia", "ranking");
        placeList.add(place3);
        Place place4 = new Place("lugar", "horario", "distancia", "ranking");
        placeList.add(place4);*/
        PlaceList mPlaceList = null;
        try {
            Gson gson = new Gson();
            mPlaceList =  gson.fromJson(jsonPlaces.toString(), PlaceList.class);
            Log.d(TAG, "tamanio " + mPlaceList.getPlaceList().size());
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

        placeList.addAll(mPlaceList.getPlaceList());

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

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Obteniendo datos");
        progressDialog.show();

        String url = RequestPlaces.getAPI_GET_LOCATION();
        final String json = Utils.getJsonLocation(CAT);
        Log.d(TAG,"LoadService: " + url);

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                try {
                    List<Place> placeList = (List<Place>) new Gson().fromJson(response, Place.class);
                    Log.d(TAG, "tamanio " + placeList.size());
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



}



