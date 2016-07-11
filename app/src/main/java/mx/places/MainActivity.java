package mx.places;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import static mx.places.utils.Const.ID_CAT;
import static mx.places.utils.Const.CAT_CINAMA;
import static mx.places.utils.Const.CAT_THEATER;
import static mx.places.utils.Const.CAT_RESTAURNANT;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bundle = new Bundle();
    }

    public void searchCinema (View view) {
        bundle.putInt(ID_CAT, CAT_CINAMA);
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
            Intent intent = new Intent(MainActivity.this, PlacesActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }


}
