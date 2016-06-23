package net.apkode.matano.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import net.apkode.matano.R;
import net.apkode.matano.model.Evenement;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class PresentationFragment extends Fragment {

    public PresentationFragment() {
    }

    public static PresentationFragment newInstance() {
        PresentationFragment presentationFragment = new PresentationFragment();
        return presentationFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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

        ImageView cover = (ImageView) view.findViewById(R.id.cover);

        Bundle bundle = getArguments();
        if (bundle != null) {
            Evenement evenement = (Evenement) bundle.getSerializable("Evenement");

            txtTitre.setText(evenement.getTitre());
            txtLieu.setText(evenement.getLieu());
            txtHoraire.setText(evenement.getHoraire());
            txtTarif.setText(evenement.getTarif());
            txtPresentation.setText(evenement.getPresentation());
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
            String dateInString = evenement.getJour();

            try {
                txtJour.setText((CharSequence) formatter.parse(dateInString));
            } catch (ParseException e) {
                e.printStackTrace();
            }


            Glide.with(cover.getContext())
                    .load(evenement.getImage())
                    .crossFade()
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    // .placeholder(view.getResources().getDrawable(R.mipmap.cover1))
                    .into(cover);
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
}
