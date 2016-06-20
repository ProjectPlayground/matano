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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import net.apkode.matano.R;
import net.apkode.matano.adapter.CommentaireAdapter;
import net.apkode.matano.api.APICommentaire;
import net.apkode.matano.helper.UtilisateurLocalStore;
import net.apkode.matano.interfaces.ICommentaire;
import net.apkode.matano.model.Commentaire;
import net.apkode.matano.model.Event;

import java.util.ArrayList;
import java.util.List;


public class CommentaireFragment extends Fragment implements ICommentaire {
    private APICommentaire apiCommentaire;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ProgressBar progressBarCommentaire;
    private EditText edtCommentaire;
    private UtilisateurLocalStore utilisateurLocalStore;
    private LinearLayout linearLayoutBtn;

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
        utilisateurLocalStore = new UtilisateurLocalStore(context);
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
        progressBarCommentaire = (ProgressBar) view.findViewById(R.id.loadingCommentaire);

        linearLayoutBtn = (LinearLayout) view.findViewById(R.id.btn);

        Bundle bundle = getArguments();
        if (bundle != null) {
            final Event event = (Event) bundle.getSerializable("Event");

            if (event != null) {
                apiCommentaire.getData(event);
                recyclerView.setAdapter(new CommentaireAdapter(new ArrayList<Commentaire>()));

                Button btnCommentaire = (Button) view.findViewById(R.id.btnCommentaire);
                edtCommentaire = (EditText) view.findViewById(R.id.edtCommentaire);

                btnCommentaire.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String commentaire = edtCommentaire.getText().toString();

                        if (commentaire.equals("")) {
                            Toast.makeText(getContext(), getString(R.string.error_commentaire), Toast.LENGTH_LONG).show();
                        } else {
                            sendCommentaire(commentaire, event);
                            edtCommentaire.setText("");
                            progressBarCommentaire.setVisibility(View.VISIBLE);
                        }
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(edtCommentaire.getWindowToken(), 0);
                    }
                });

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
        linearLayoutBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void sendResponse(String response) {
        Log.e("e", "response " + response);
        progressBarCommentaire.setVisibility(View.GONE);
    }

    private void sendCommentaire(String commentaire, Event event) {
        String telephone = utilisateurLocalStore.getUtilisateur().getTelephone();
        apiCommentaire.sendCommentaire(event, commentaire, telephone);
    }
}
