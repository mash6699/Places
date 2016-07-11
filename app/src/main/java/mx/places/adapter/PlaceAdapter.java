package mx.places.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mx.places.R;
import mx.places.model.Place;

/**
 * Created by miguel_angel on 8/07/16.
 */
public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlaceHolder> {

   Context context;
   List<Place> placeList = new ArrayList<>();

    public PlaceAdapter(List<Place> places, Context context) {
        this.placeList = places;
        this.context = context;
    }

    @Override
    public PlaceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_item,parent,false);
        return new PlaceHolder(view);
    }

    @Override
    public void onBindViewHolder(PlaceHolder holder, int position) {
        Place place = placeList.get(position);
        holder.tv_name.setText(place.getName());
        holder.tv_schedule.setText(place.getSchedule());
        holder.tv_distace.setText(place.getDistance());
    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }

    class PlaceHolder extends RecyclerView.ViewHolder {

        private ImageView im_icon;
        private TextView tv_name;
        private TextView tv_distace;
        private TextView tv_schedule;
      //  private TextView tv_ranking;

        public PlaceHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tvPlaceName);
            tv_distace = (TextView)   itemView.findViewById(R.id.tvSchedule);
            tv_schedule = (TextView)  itemView.findViewById(R.id.tvDistance);
          //  tv_ranking =  itemView.findViewById();
        }
    }

}
