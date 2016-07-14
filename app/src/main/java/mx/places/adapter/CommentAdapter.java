package mx.places.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mx.places.R;
import mx.places.iface.PlaceSelector;
import mx.places.model.Comments;
import mx.places.model.Place;

/**
 * Created by miguel_angel on 8/07/16.
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.PlaceHolder> {

    private Context context;
    private List<Comments> commentsList = new ArrayList<>();

    public CommentAdapter(List<Comments> commentsList, Context context) {
        this.commentsList = commentsList;
        this.context = context;
    }

    @Override
    public PlaceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item,parent,false);
        return new PlaceHolder(view);
    }

    @Override
    public void onBindViewHolder(PlaceHolder holder, int position) {
        final Comments comments = commentsList.get(position);
        holder.tv_comment.setText("Comentario:" +comments.getComment());
        holder.tv_date.setText("Fecha: " +comments.getDate());
        holder.rb_qualification.setEnabled(false);
        float numStarts = 0;

        try {
            numStarts = comments.getQualification();
            System.out.println("numStarts ::: " + numStarts);
            holder.rb_qualification.setRating(numStarts);
        } catch (Exception e) {
            System.out.println("numStarts ::: " + numStarts);
        } finally {
            holder.rb_qualification.setRating(numStarts);
        }

    }

    @Override
    public int getItemCount() {
        return commentsList.size();
    }

    class PlaceHolder extends RecyclerView.ViewHolder {

        private TextView tv_comment;
        private TextView tv_date;
        private RatingBar rb_qualification;

        public PlaceHolder(View itemView) {
            super(itemView);
            tv_comment       = (TextView) itemView.findViewById(R.id.tvComment);
            tv_date          = (TextView)   itemView.findViewById(R.id.tvDate);
            rb_qualification = (RatingBar) itemView.findViewById(R.id.ratingBarComment);
        }
    }

}
