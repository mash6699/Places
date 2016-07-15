package mx.places.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import mx.places.R;
import mx.places.adapter.CommentAdapter;
import mx.places.model.CommentsList;

/**
 * Created by miguel_angel on 14/07/16.
 */
public class DFragment extends DialogFragment implements View.OnClickListener{

    private final static String TAG = DialogFragment.class.getSimpleName();
    CommentsList commentsList;

    CommentAdapter commentAdapter;
    RecyclerView recycler;
    Button bt_close;
    View rootView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dialog_layout, container,
                false);

        recycler = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        bt_close = (Button) rootView.findViewById(R.id.btClose);

        if(getArguments() !=null) {
            commentsList = (CommentsList) getArguments().getSerializable("COMMENT");
            Log.d(TAG, "tamanio " +  commentsList.getCommentsList().size());

            commentAdapter = new CommentAdapter(commentsList.getCommentsList(), getContext());
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            recycler.setLayoutManager(layoutManager);
            recycler.setItemAnimator(new DefaultItemAnimator());

            recycler.setAdapter(commentAdapter);
        }

        bt_close.setOnClickListener(this);

        getDialog().setTitle("Comenta");
        return rootView;
    }


    @Override
    public void onClick(View view) {
        getDialog().dismiss();
    }
}
