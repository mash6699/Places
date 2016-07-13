package mx.places;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import mx.places.utils.Utils;

import static mx.places.utils.Const.ID_CAT;
import static mx.places.utils.Const.CAT_CINEMA;
import static mx.places.utils.Const.CAT_THEATER;
import static mx.places.utils.Const.CAT_RESTAURNANT;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    Bundle bundle = new Bundle();
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.app_name));

        setSupportActionBar(toolbar);
    }

    public void searchCinema (View view) {
        bundle.putInt(ID_CAT, CAT_CINEMA);
        sendIntentPlace(bundle);
    }

    public void searchTheater (View view) {
        bundle.putInt(ID_CAT, CAT_THEATER);
        sendIntentPlace(bundle);
    }

    public void searchRestaurant (View view) {
        bundle.putInt(ID_CAT, CAT_RESTAURNANT);
        sendIntentPlace(bundle);
    }

    public void sendIntentPlace (Bundle bundle) {
        try {
            Utils.saveCat(bundle,getApplicationContext());
            Log.d(TAG, "sendIntentPlace: " + bundle.get(ID_CAT));
            Intent intent = new Intent(MainActivity.this, PlacesActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }


}
