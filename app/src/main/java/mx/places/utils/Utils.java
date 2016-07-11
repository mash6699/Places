package mx.places.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.BundleCompat;
import android.util.Log;

import mx.places.PlacesActivity;

/**
 * Created by miguelangel on 10/07/2016.
 */
public class Utils {

    public final static String TAG = Utils.class.getSimpleName();

    public static String getNameCategory (int cat) {
        String name = null;
        switch (cat) {
            case Const.CAT_CINAMA:
                name = Const.CINEMA;
            case Const.CAT_THEATER:
                name = Const.THEATER;
            case Const.CAT_RESTAURNANT:
                name = Const.RESTAURANT;
        }
        Log.d(TAG, name);
        return name;
    }

}
