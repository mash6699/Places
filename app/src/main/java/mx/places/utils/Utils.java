package mx.places.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RetryPolicy;

import org.json.JSONException;
import org.json.JSONObject;

import mx.places.R;

import static mx.places.utils.Const.ID_CAT;

/**
 * Created by miguelangel on 10/07/2016.
 */
public class Utils {

    public final static String TAG = Utils.class.getSimpleName();

    public static String getNameCategory (int cat) {
        String name = null;
        switch (cat) {
            case Const.CAT_CINEMA:
                name = Const.CINEMA;
                break;
            case Const.CAT_THEATER:
                name = Const.THEATER;
                break;
            case Const.CAT_RESTAURNANT:
                name = Const.RESTAURANT;
                break;
        }
        Log.d(TAG, name);
        return name;
    }

    public static Drawable getIconByCategory(int cat, Context context){
        Drawable imageId = null;
        switch (cat) {
            case Const.CAT_CINEMA:
                imageId = context.getResources().getDrawable(R.drawable.img_cinema);
                break;
            case Const.CAT_THEATER:
                imageId = context.getResources().getDrawable(R.drawable.img_theater);
                break;
            case Const.CAT_RESTAURNANT:
                imageId = context.getResources().getDrawable(R.drawable.img_restaurant);
                break;
        }
        return imageId;
    }

    public static boolean isOnline(Activity a) {
        ConnectivityManager cm = (ConnectivityManager) a.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        }
        return false;
    }

    public static String getJsonLocation(int cat) {
        String jsonReult = null;
        JSONObject params = new JSONObject();
        try {
            params.put("idTipo", cat);
            params.put("idStatus", -1);
            jsonReult = params.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            Log.d(TAG, "JSON: " + jsonReult);
            return jsonReult;
        }
    }

    public static String getJsonAllComments(int idLugar){
        String jsonReult = null;
        JSONObject params = new JSONObject();
        try {
            params.put("idLugar", idLugar);

            jsonReult = params.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            Log.d(TAG, "JSON: " + jsonReult);
            return jsonReult;
        }
    }


    public static String getJsonComment(int idLugar,String comment, int c, String loc){
        String jsonReult = null;
        JSONObject params = new JSONObject();
        try {
            params.put("idLugar", idLugar);
            params.put("usuario", "Android");
            params.put("comentario", comment);
            params.put("calificacion", c);
            params.put("coordenadas", loc);
            jsonReult = params.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            Log.d(TAG, "JSON: " + jsonReult);
            return jsonReult;
        }
    }


    public static RetryPolicy getRetryPolicy(){
        return  new DefaultRetryPolicy(
                1500,
                3,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    }

    public static void saveCat(Bundle bundle, Context context) {
        SharedPreferences sp = context.getSharedPreferences("place", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(ID_CAT,bundle.getInt(ID_CAT));
        editor.commit();
    }

    public static int getCat(Context context){
        SharedPreferences sp = context.getSharedPreferences("place", 0);
        return sp.getInt(ID_CAT, 1);
    }


    /*
      AlertDialog.Builder builder = new AlertDialog.Builder(a);
            builder.setMessage("No tienes conexion a la red")
                    .setCancelable(true)
                    .setPositiveButton("OK", null);
            AlertDialog alert = builder.create();
            alert.show();
     */

}
