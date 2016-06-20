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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import net.apkode.matano.R;
import net.apkode.matano.adapter.ParticipantAdapter;
import net.apkode.matano.api.APIParticipant;
import net.apkode.matano.helper.UtilisateurLocalStore;
import net.apkode.matano.interfaces.IParticipant;
import net.apkode.matano.model.Event;
import net.apkode.matano.model.Participant;

import java.util.ArrayList;
import java.util.List;

public class ParticipantFragment extends Fragment implements IParticipant {
    private APIParticipant apiParticipant;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ProgressBar progressBarParticipant;
    private UtilisateurLocalStore utilisateurLocalStore;
    private Button btnSendParticipant;
    private LinearLayout linearLayoutBtn;

    public ParticipantFragment() {
    }

    public static ParticipantFragment newInstance() {
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

        Bundle bundle = getArguments();
        if (bundle != null) {
            final Event event = (Event) bundle.getSerializable("Event");

            if (event != null) {
                apiParticipant.getData(event);
                recyclerView.setAdapter(new ParticipantAdapter(new ArrayList<Participant>()));

                btnSendParticipant.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String text = btnSendParticipant.getText().toString();

                        if (text.equals("Participer")) {
                            sendPartiticpant(event, "suppression");
                        } else if (text.equals("Ne pas participer")) {
                            sendPartiticpant(event, "ajout");
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
        progressBar.setVisibility(View.GONE);
        linearLayoutBtn.setVisibility(View.VISIBLE);
        recyclerView.setAdapter(new ParticipantAdapter(participants));
        checkIsParticipant(participants);
    }

    @Override
    public void sendResponse(String response) {
        Log.e("e", "response " + response);
        progressBarParticipant.setVisibility(View.GONE);
    }

    private void sendPartiticpant(Event event, String status) {
        String telephone = utilisateurLocalStore.getUtilisateur().getTelephone();
        apiParticipant.sendParticipant(event, telephone, status);
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
            btnSendParticipant.setText(getString(R.string.participate1));
        } else {
            btnSendParticipant.setText(getString(R.string.participate0));
            btnSendParticipant.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
        }
    }

}
