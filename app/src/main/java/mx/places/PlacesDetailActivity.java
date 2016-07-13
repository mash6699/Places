package mx.places;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import mx.places.model.Place;
import mx.places.utils.Const;
import mx.places.utils.Utils;

import static mx.places.utils.Const.CAT_THEATER;
import static mx.places.utils.Const.ID_CAT;

/**
 * Created by miguel_angel on 8/07/16.
 */
public class PlacesDetailActivity extends AppCompatActivity implements RatingBar.OnRatingBarChangeListener {

    private final static String TAG = PlacesDetailActivity.class.getName();
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
        if(commet != null && !commet.isEmpty()) {

        }else {
            Toast.makeText(this.getApplicationContext(), "Debes ingresar un comentario!!" , Toast.LENGTH_LONG).show();
        }

    }


    public void goPlaceInMap(View view){

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
