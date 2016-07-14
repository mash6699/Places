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

import mx.places.R;
import mx.places.adapter.CommentAdapter;
import mx.places.model.CommentsList;

/**
 * Created by miguel_angel on 14/07/16.
 */
public class DFragment extends DialogFragment {

    private final static String TAG = DialogFragment.class.getSimpleName();
    CommentsList commentsList;

    CommentAdapter commentAdapter;
    RecyclerView recycler;
    View rootView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dialog_layout, container,
                false);

        recycler = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        if(getArguments() !=null) {
            commentsList = (CommentsList) getArguments().getSerializable("COMMENT");
            Log.d(TAG, "tamanio " +  commentsList.getCommentsList().size());

            commentAdapter = new CommentAdapter(commentsList.getCommentsList(), getContext());
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            recycler.setLayoutManager(layoutManager);
            recycler.setItemAnimator(new DefaultItemAnimator());

            recycler.setAdapter(commentAdapter);
        }



        getDialog().setTitle("Comenta");
        return rootView;
    }



}
