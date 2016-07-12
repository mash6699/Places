package mx.places.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ironbit on 12/07/16.
 */
public class PlaceList {

    @SerializedName("Places")
    private List<Place> placeList = new ArrayList<>();

    public List<Place> getPlaceList()  {
        return placeList;
    }
}
