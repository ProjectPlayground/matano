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
import net.apkode.matano.model.Evennement;

import java.util.ArrayList;
import java.util.List;


public class CommentaireFragment extends Fragment implements ICommentaire {
    private static boolean isCommentairePassed = false;
    private APICommentaire apiCommentaire;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ProgressBar progressBarCommentaire;
    private EditText edtCommentaire;
    private UtilisateurLocalStore utilisateurLocalStore;
    private LinearLayout linearLayoutBtn;
    private List<Commentaire> commentairesListe = new ArrayList<>();
    private Evennement evennement;

    public CommentaireFragment() {
    }

    public static CommentaireFragment newInstance() {

        isCommentairePassed = true;
        CommentaireFragment commentaireFragment = new CommentaireFragment();
        return commentaireFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        apiCommentaire = new APICommentaire(this, context);
        utilisateurLocalStore = new UtilisateurLocalStore(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_commentaire, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        progressBar = (ProgressBar) view.findViewById(R.id.loading);
        progressBarCommentaire = (ProgressBar) view.findViewById(R.id.loadingCommentaire);
        recyclerView.setAdapter(new CommentaireAdapter(commentairesListe));

        linearLayoutBtn = (LinearLayout) view.findViewById(R.id.btn);

        Bundle bundle = getArguments();
        if (bundle != null) {
            evennement = (Evennement) bundle.getSerializable("Evennement");

            if (evennement != null) {

                if (isCommentairePassed) {
                    Log.e("e", "first time");
                    apiCommentaire.getData(evennement);
                    isCommentairePassed = false;
                } else {
                    if (commentairesListe.size() == 0) {
                        apiCommentaire.getData(evennement);
                    } else {
                        progressBar.setVisibility(View.GONE);
                        linearLayoutBtn.setVisibility(View.VISIBLE);
                        Log.e("e", "fassed");
                    }

                }

                Button btnCommentaire = (Button) view.findViewById(R.id.btnCommentaire);
                edtCommentaire = (EditText) view.findViewById(R.id.edtCommentaire);

                btnCommentaire.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String commentaire = edtCommentaire.getText().toString();

                        if (commentaire.equals("")) {
                            Toast.makeText(getContext(), getString(R.string.error_commentaire), Toast.LENGTH_LONG).show();
                        } else {
                            sendCommentaire(commentaire, evennement);
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
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void getResponse(List<Commentaire> commentaires) {
        if (commentaires == null) {
            Log.e("e", "actualites == null");
            apiCommentaire.getData(evennement);
            linearLayoutBtn.setVisibility(View.VISIBLE);
        } else {
            if (commentaires.size() == 0) {
                //    Toast.makeText(getActivity().getApplicationContext(), getString(R.string.error_reseau), Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            } else {
                progressBar.setVisibility(View.GONE);
                commentairesListe = commentaires;
                recyclerView.setAdapter(new CommentaireAdapter(commentairesListe));
                linearLayoutBtn.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void sendResponse(String response) {
        progressBarCommentaire.setVisibility(View.GONE);
    }

    private void sendCommentaire(String commentaire, Evennement evennement) {
        String telephone = utilisateurLocalStore.getUtilisateur().getTelephone();
        apiCommentaire.sendCommentaire(evennement, commentaire, telephone);
    }
}
