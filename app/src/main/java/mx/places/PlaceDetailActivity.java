package mx.places;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import mx.places.model.Place;
import mx.places.model.PlaceList;
import mx.places.service.VolleyService;
import mx.places.utils.Const;
import mx.places.utils.RequestPlaces;
import mx.places.utils.Utils;

/**
 * Created by miguel_angel on 8/07/16.
 */
public class PlaceDetailActivity extends AppCompatActivity implements RatingBar.OnRatingBarChangeListener {

    private final static String TAG = PlaceDetailActivity.class.getName();
    private Place place;

    private Toolbar toolbar;
    private TextView tv_name;
    private TextView tv_respo;
    private TextView tv_address;
    private TextView tv_phones;
    private TextView tv_schedule;
    private TextView tv_distance;

    private EditText ed_comment;

    private TextView tv_rating;
    private RatingBar ratingBar;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tv_name = (TextView)  findViewById(R.id.tvPlaceName);
        tv_respo = (TextView)  findViewById(R.id.tvResp);
        tv_address = (TextView)  findViewById(R.id.tvAddress);
        tv_phones = (TextView)  findViewById(R.id.tvPhones);
        tv_schedule = (TextView)  findViewById(R.id.tvSchelud);
        tv_distance = (TextView)  findViewById(R.id.tvDistance);

        ed_comment = (EditText) findViewById(R.id.ed_comment);

        ratingBar = (RatingBar) findViewById(R.id.rb_cat);
        tv_rating = (TextView)  findViewById(R.id.tv_ranking);

        if (getIntent().getExtras() != null) {
            place = (Place) getIntent().getExtras().getSerializable(Const.PLACE);
            setPlaceInView();
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ratingBar.setOnRatingBarChangeListener(this);

    }

    private void setPlaceInView(){
        Log.d(TAG, "setPlaceInView");
        try {
            if (place != null) {
                toolbar.setLogo(Utils.getIconByCategory(place.getIdCat(), getApplicationContext()));
                toolbar.setTitle(place.getName());
                tv_name.setText(place.getName());
                tv_address.setText(place.getAddress());
                tv_schedule.setText(place.getSchedule());
                if (place.getAllRespon() != null) {
                    tv_respo.setText(place.getAllRespon());
                }

                if (place.getAllPhones() != null) {
                    tv_phones.setText(place.getAllPhones());
                }

                //   tv_distance.setText();
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }


    private void setPlaceValues(){

    }

    public void sendComment(View view){
        Log.d(TAG, "sendComment");
        String commet = ed_comment.getText().toString();
        int numStarts= ratingBar.getNumStars();
        if(commet != null && !commet.isEmpty()) {
            serviceComment(commet, numStarts);
        }else {
            Toast.makeText(this.getApplicationContext(), "Debes ingresar un comentario!!" , Toast.LENGTH_LONG).show();
        }
    }

    private void serviceComment(String comment, int num) {

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Enviando comentario!");
        progressDialog.show();

        String url = RequestPlaces.API_SEND_COMMENT();
        final String json = Utils.getJsonComment(place.getId(),comment,num,place.getCoordinates());
        Log.d(TAG,"LoadService: " + url);

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.has("message")) {
                                String resultMessage = jsonObject.getString("message").toUpperCase();
                                if(resultMessage.contains("OK")){
                                    Toast.makeText(getApplicationContext(), "Cometario enviado", Toast.LENGTH_LONG).show();
                                    resetFields();
                                }else {
                                    Toast.makeText(getApplicationContext(), ":( intentalo más tarde!!", Toast.LENGTH_LONG).show();
                                }
                            }

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

    private void resetFields(){
        Log.d(TAG, "resetFields");
        ratingBar.setNumStars(0);
        ed_comment.setText("");
    }


    public void goPlaceInMap(View view){
        Intent i = new Intent(this.getApplication(), MapsActivity.class); //PlaceLocationActivity
        i.putExtra(Const.PLACE, place);
        startActivity(i);

    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
        int value = (int) v;
        tv_rating.setText(String.valueOf(value));
    }

/*    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Nullable
    @Override
    public Intent getSupportParentActivityIntent() {
        Bundle bundle = new Bundle();
        bundle.putInt(ID_CAT, place.getIdCat());
        Intent intent = super.getParentActivityIntent();
        return intent;
    }*/

/*
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(ID_CAT, place.getIdCat());
        setResult(200, intent);
        super.onBackPressed();
    }*/
}
