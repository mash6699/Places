package mx.places;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import mx.places.model.Place;
import mx.places.utils.Const;

/**
 * Created by miguel_angel on 8/07/16.
 */
public class PlacesDetailActivity extends AppCompatActivity implements RatingBar.OnRatingBarChangeListener {

    private final static String TAG = PlacesDetailActivity.class.getName();
    private Place place;

    private TextView tv_name;
    private TextView tv_respo;
    private TextView tv_address;
    private TextView tv_phones;
    private TextView tv_schedule;
    private TextView tv_distance;

    private TextView tv_rating;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout);


        tv_name = (TextView)  findViewById(R.id.tvPlaceName);
        tv_respo = (TextView)  findViewById(R.id.tvResp);
        tv_address = (TextView)  findViewById(R.id.tvAddress);
        tv_phones = (TextView)  findViewById(R.id.tvPhones);
        tv_schedule = (TextView)  findViewById(R.id.tvSchedule);
        tv_distance = (TextView)  findViewById(R.id.tvDistance);


        ratingBar = (RatingBar) findViewById(R.id.rb_cat);
        tv_rating = (TextView)  findViewById(R.id.tv_ranking);

        if (getIntent().getExtras() != null) {
            place = (Place) getIntent().getExtras().getSerializable(Const.PLACE);
        }

        ratingBar.setOnRatingBarChangeListener(this);

    }



    private void setPlaceValues(){

    }

    private void sendComment(){

    }


    public void goPlaceInMap(View view){

    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
        tv_rating.setText((String.valueOf(v)));
    }
}
