package net.apkode.matano.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import net.apkode.matano.R;
import net.apkode.matano.adapter.CommentaireAdapter;
import net.apkode.matano.api.APICommentaire;
import net.apkode.matano.interfac.ICommentaire;
import net.apkode.matano.model.Commentaire;
import net.apkode.matano.model.Event;

import java.util.ArrayList;
import java.util.List;


public class CommentaireFragment extends Fragment implements ICommentaire {
    private APICommentaire apiCommentaire;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    public CommentaireFragment() {
    }

    public static CommentaireFragment newInstance() {
        CommentaireFragment commentaireFragment = new CommentaireFragment();
        return commentaireFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        apiCommentaire = new APICommentaire(this, context);
        Log.e("e", "onAttach");

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("e", "onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("e", "onCreateView");
        return inflater.inflate(R.layout.fragment_commentaire, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.e("e", "onViewCreated");
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        progressBar = (ProgressBar) view.findViewById(R.id.loading);

        Bundle bundle = getArguments();
        if (bundle != null) {
            Event event = (Event) bundle.getSerializable("Event");

            if (event != null) {
                apiCommentaire.getData(event);
                recyclerView.setAdapter(new CommentaireAdapter(new ArrayList<Commentaire>()));
            }
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.e("e", "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("e", "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("e", "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("e", "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("e", "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("e", "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("e", "onDetach");
    }

    @Override
    public void getResponse(List<Commentaire> commentaires) {
        progressBar.setVisibility(View.GONE);
        recyclerView.setAdapter(new CommentaireAdapter(commentaires));
    }
}
