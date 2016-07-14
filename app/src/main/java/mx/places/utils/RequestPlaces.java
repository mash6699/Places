package mx.places.utils;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by miguel_angel on 11/07/16.
 */
public class RequestPlaces {

    private static String HOST = "http://54.87.140.128/";
    private static String API_PATH = "ServiceApp/services/rest/" ;
    private static String API_GET_COMMENTS = "getComentarios" ;
    private static String API_SEND_COMMENT = "setComentario" ;
    private static String API_GET_LOCATION = "getUbicaciones2" ;
    private static String API_GOOGLE_MATRIX = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=";


    public static String API_GET_COMMENTS() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(HOST);
        stringBuilder.append(API_PATH);
        stringBuilder.append(API_GET_COMMENTS);
        return stringBuilder.toString();
    }

    public static  String API_SEND_COMMENT() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(HOST);
        stringBuilder.append(API_PATH);
        stringBuilder.append(API_SEND_COMMENT);
        return stringBuilder.toString();
    }

    public static String API_GET_LOCATION() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(HOST);
        stringBuilder.append(API_PATH);
        stringBuilder.append(API_GET_LOCATION);
        return stringBuilder.toString();
    }

    public static String API_GET_MATRIX(LatLng origen, LatLng destino){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(API_GOOGLE_MATRIX);
        stringBuilder.append(origen.latitude + "," + origen.longitude);
        stringBuilder.append("&destinations=");
        stringBuilder.append(destino.latitude + "," + destino.longitude );
        stringBuilder.append("&mode=driving&language=mx-MX");
        return stringBuilder.toString();
    }


}
