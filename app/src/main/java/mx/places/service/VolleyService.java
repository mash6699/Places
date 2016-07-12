package mx.places.service;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by miguel_angel on 11/07/16.
 */
public class VolleyService {

    private static VolleyService mInstance = null;
    private Context context;
    private RequestQueue mRequestQueue;

    private VolleyService(Context context){
        this.context = context;
        this.mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized  VolleyService getInstance (Context context) {
        if(mInstance == null) {
            mInstance = new VolleyService(context);
        }
        return mInstance;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return mRequestQueue;
    }

}
