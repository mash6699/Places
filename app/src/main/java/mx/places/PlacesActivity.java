package mx.places;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import mx.places.utils.Utils;

import static mx.places.utils.Const.ID_CAT;

/**
 * Created by miguel_angel on 8/07/16.
 */
public class PlacesActivity extends AppCompatActivity {

    private final static String TAG = PlacesActivity.class.getName();
    private int CAT = 0;

    TextView txtCategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.places_layout);

        txtCategory = (TextView) findViewById(R.id.tvCat);

        if (getIntent().getExtras() != null) {
            CAT = getIntent().getExtras().getInt(ID_CAT);
            txtCategory.setText(Utils.getNameCategory(CAT));
        }
    }
}
