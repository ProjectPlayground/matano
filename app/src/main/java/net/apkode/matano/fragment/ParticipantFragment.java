package net.apkode.matano.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import net.apkode.matano.R;
import net.apkode.matano.adapter.ParticipantAdapter;
import net.apkode.matano.api.APIParticipant;
import net.apkode.matano.helper.UtilisateurLocalStore;
import net.apkode.matano.interfaces.IParticipant;
import net.apkode.matano.model.Evenement;
import net.apkode.matano.model.Participant;
import net.apkode.matano.model.Utilisateur;

import java.util.ArrayList;
import java.util.List;

public class ParticipantFragment extends Fragment implements IParticipant {
    private static boolean isParticipantPassed = false;
    private static Context context;
    private APIParticipant apiParticipant;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ProgressBar progressBarParticipant;
    private UtilisateurLocalStore utilisateurLocalStore;
    private Button btnSendParticipant;
    private LinearLayout linearLayoutBtn;
    private List<Participant> participantsListe = new ArrayList<>();
    private Evenement evenement;
    private Utilisateur utilisateur;
    private ParticipantAdapter participantAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    public ParticipantFragment() {
    }

    public static ParticipantFragment newInstance(Context ctx) {
        isParticipantPassed = true;
        context = ctx;
        ParticipantFragment participantFragment = new ParticipantFragment();
        return participantFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        apiParticipant = new APIParticipant(this, context);
        utilisateurLocalStore = new UtilisateurLocalStore(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_participant, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        progressBar = (ProgressBar) view.findViewById(R.id.loading);
        progressBarParticipant = (ProgressBar) view.findViewById(R.id.progressBarParticipant);

        btnSendParticipant = (Button) view.findViewById(R.id.btnSendParticipant);
        linearLayoutBtn = (LinearLayout) view.findViewById(R.id.btn);

        participantAdapter = new ParticipantAdapter(participantsListe);
        recyclerView.setAdapter(participantAdapter);

        utilisateur = utilisateurLocalStore.getUtilisateur();

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        refreshLayout();
                    }
                }
        );

        Bundle bundle = getArguments();
        if (bundle != null) {
            evenement = (Evenement) bundle.getSerializable("Evenement");

            if (evenement != null) {

                if (isParticipantPassed) {
                    apiParticipant.getData(evenement);
                    isParticipantPassed = false;
                } else {
                    if (participantsListe.size() == 0) {
                        new APIParticipant(this, context).getData(evenement);
                    } else {
                        progressBar.setVisibility(View.GONE);
                        linearLayoutBtn.setVisibility(View.VISIBLE);
                    }
                }

                btnSendParticipant.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String text = btnSendParticipant.getText().toString();
                        progressBarParticipant.setVisibility(View.VISIBLE);
                        if (text.equals("Participer")) {
                            apiParticipant.createPartiticpant(evenement, utilisateur);
                        } else if (text.equals("Ne pas participer")) {
                            apiParticipant.deletePartiticpant(evenement, utilisateur);
                        }
                        progressBarParticipant.setVisibility(View.VISIBLE);


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
    public void getResponse(List<Participant> participants) {
        if (participants == null) {
            new APIParticipant(this, context).getData(evenement);
        } else {
            participantsListe = participants;
            checkIsParticipant(participants);
            try {
                progressBar.setVisibility(View.GONE);
                linearLayoutBtn.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setRefreshing(false);
                recyclerView.setAdapter(new ParticipantAdapter(participantsListe));
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }

    @Override
    public void sendResponseCreateParticipant(String response) {

        try {
            progressBarParticipant.setVisibility(View.GONE);
        } catch (Exception e) {
            e.getMessage();
        }

        if (response == null) {

            try {
                Toast.makeText(getContext(), getString(R.string.error_reseau), Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.getMessage();
            }

        } else {
            if (response.equals("0")) {

                try {
                    Toast.makeText(getContext(), getString(R.string.error_send_create_participant), Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.getMessage();
                }

            } else if (response.equals("1")) {

                Utilisateur utilisateur = utilisateurLocalStore.getUtilisateur();

                try {

                    Toast.makeText(getContext(), getString(R.string.succes_send_create_participant), Toast.LENGTH_LONG).show();
                    participantsListe.add(new Participant(99, utilisateur.getNom(), utilisateur.getPrenom(), utilisateur.getTelephone(), utilisateur.getImage()));
                    participantAdapter.notifyItemInserted(participantsListe.size() - 1);
                    recyclerView.scrollToPosition(participantsListe.size() - 1);
                    btnSendParticipant.setText(getString(R.string.participate0));
                    btnSendParticipant.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                } catch (Exception e) {
                    e.getMessage();
                }

            }
        }
    }

    @Override
    public void sendResponseDeleteParticipant(String response) {
        try {
            progressBarParticipant.setVisibility(View.GONE);
        } catch (Exception e) {
            e.getMessage();
        }

        if (response == null) {

            try {
                Toast.makeText(getContext(), getString(R.string.error_reseau), Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.getMessage();
            }

        } else {
            if (response.equals("0")) {

                try {
                    Toast.makeText(getContext(), getString(R.string.error_send_delete_participant), Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.getMessage();
                }

            } else if (response.equals("1")) {

                try {

                    Toast.makeText(getContext(), getString(R.string.succes_send_delete_participant), Toast.LENGTH_LONG).show();

                    participantsListe.remove(participantsListe.size() - 1);
                    recyclerView.scrollToPosition(participantsListe.size() - 1);

                    btnSendParticipant.setText(getString(R.string.participate1));
                    btnSendParticipant.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                } catch (Exception e) {
                    e.getMessage();
                }

            }
        }
    }

    private void checkIsParticipant(List<Participant> participants) {
        String telephone = utilisateurLocalStore.getUtilisateur().getTelephone();
        String existe = "0";

        for (Participant participant : participants) {
            if (participant.getTelephone().equals(telephone)) {
                existe = "1";
            }
        }

        if (existe.equals("0")) {
            try {
                btnSendParticipant.setText(getString(R.string.participate1));
            } catch (Exception e) {
                e.getMessage();
            }
        } else {
            try {
                btnSendParticipant.setText(getString(R.string.participate0));
                btnSendParticipant.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
            }catch (Exception e){
                e.getMessage();
            }
        }
    }

    private void refreshLayout() {
        new APIParticipant(this, context).getData(evenement);
    }

}
