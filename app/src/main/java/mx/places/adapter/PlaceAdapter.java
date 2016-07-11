package mx.places.adapter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import mx.places.R;

/**
 * Created by miguel_angel on 8/07/16.
 */
public class PlaceAdapter extends AppCompatActivity {


    class PlaceHolder extends RecyclerView.ViewHolder {

        private ImageView im_icon;
        private TextView tv_name;
        private TextView tv_distace;
        private TextView tv_schedule;
        private TextView tv_ranking;

        public PlaceHolder(View itemView) {
            super(itemView);
        }
    }

}
