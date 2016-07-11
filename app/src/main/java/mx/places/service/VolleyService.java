package mx.places.service;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by miguel_angel on 11/07/16.
 */
public class VolleyService {
    private static VolleyService mInstance;
    private RequestQueue requestQueue;
    private static Context context;

    private VolleyService(Context context){
        context = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized  VolleyService getInstance (Context context) {
        if(mInstance == null) {
            mInstance = new VolleyService(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req){
        getRequestQueue().add(req);
    }

}
