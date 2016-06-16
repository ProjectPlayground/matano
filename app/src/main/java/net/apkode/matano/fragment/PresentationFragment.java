package net.apkode.matano.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.apkode.matano.R;
import net.apkode.matano.model.Event;

public class PresentationFragment extends Fragment {

    public PresentationFragment() {
    }

    public static PresentationFragment newInstance(){
        PresentationFragment presentationFragment = new PresentationFragment();
        return presentationFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_presentation, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView txtTitre = (TextView) view.findViewById(R.id.txtTitre);
        TextView txtLieu = (TextView) view.findViewById(R.id.txtLieu);
        TextView txtHoraire = (TextView) view.findViewById(R.id.txtHoraire);
        TextView txtTarif = (TextView) view.findViewById(R.id.txtTarif);
        TextView txtPresentation = (TextView) view.findViewById(R.id.txtPresentation);
        TextView txtJour = (TextView) view.findViewById(R.id.txtJour);

        Bundle bundle = getArguments();
        if(bundle != null){
            Event event = (Event) bundle.getSerializable("Event");

            txtTitre.setText(event.getTitre());
            txtLieu.setText(event.getLieu());
            txtHoraire.setText(event.getHoraire());
            txtTarif.setText(event.getTarif());
            txtPresentation.setText(event.getPresentation());
            txtJour.setText(event.getJour());

        }



    }
}
