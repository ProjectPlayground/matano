package net.apkode.matano.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.apkode.matano.R;
import net.apkode.matano.adapter.CommentaireAdapter;
import net.apkode.matano.model.Commentaire;
import net.apkode.matano.model.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brabo on 6/15/16.
 */
public class CommentaireFragment extends Fragment {

    public CommentaireFragment() {
    }

    public static CommentaireFragment newInstance() {
        CommentaireFragment commentaireFragment = new CommentaireFragment();
        return commentaireFragment;
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

        RecyclerView recyclerView;
        List<Commentaire> commentaires = new ArrayList<>();

        Bundle bundle = getArguments();
        if (bundle != null) {
            Event event = (Event) bundle.getSerializable("Event");

            recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            commentaires.add(new Commentaire(1l, "Bachir", "selah zion", "01/02/2016", "8h20", "https://goodpitch.org/uploads/cache/user_image/max_400_400_monifa-bandele-b.jpg", "Merci pour cette nouvelle vidéo; je me pose de plus en plus la question suite aux chroniques de ramadan précédentes, je comprends qu’il est essentiel de resister à son ego, à l’envie de se penser parfaitement pur, angelique, mais dans le monde d’aujourd’hui j’ai des difficultés à appliquer exactement ces principes et j’ai même l’impression qu’ils pourraient être destructeurs si appliqués à la lettre."));
            commentaires.add(new Commentaire(1l, "Awa sow", "Mansour", "11/12/2016", "8h20", "https://pbs.twimg.com/profile_images/1717956431/BP-headshot-fb-profile-photo_400x400.jpg", "Merci pour cette nouvelle vidéo; je me pose de plus en plus la question suite aux chroniques de ramadan précédentes, je comprends qu’il est essentiel de resister à son ego, à l’envie de se penser parfaitement pur, angelique, mais dans le monde d’aujourd’hui j’ai des difficultés à appliquer exactement ces principes et j’ai même l’impression qu’ils pourraient être destructeurs si appliqués à la lettre."));
            commentaires.add(new Commentaire(1l, "Salif keita", "Amine", "01/02/2016", "8h20", "http://cps-static.rovicorp.com/3/JPG_400/MI0003/643/MI0003643950.jpg?partner=allrovi.com", "Merci pour cette nouvelle vidéo; je me pose de plus en plus la question suite aux chroniques de ramadan précédentes, je comprends qu’il est essentiel de resister à son ego, à l’envie de se penser parfaitement pur, angelique, mais dans le monde d’aujourd’hui j’ai des difficultés à appliquer exactement ces principes et j’ai même l’impression qu’ils pourraient être destructeurs si appliqués à la lettre."));
            commentaires.add(new Commentaire(1l, "Nasser Marabout", "Karim", "01/02/2016", "8h20", "https://pbs.twimg.com/profile_images/637722086547587072/g3kWsOVa.jpg", "Merci pour cette nouvelle vidéo; je me pose de plus en plus la question suite aux chroniques de ramadan précédentes, je comprends qu’il est essentiel de resister à son ego, à l’envie de se penser parfaitement pur, angelique, mais dans le monde d’aujourd’hui j’ai des difficultés à appliquer exactement ces principes et j’ai même l’impression qu’ils pourraient être destructeurs si appliqués à la lettre."));
            commentaires.add(new Commentaire(1l, "Moctar", "seini", "01/02/2016", "8h20", "http://servotronicstech.com/wp-content/uploads/2015/03/p-1-400x400.jpg", "Merci pour cette nouvelle vidéo; je me pose de plus en plus la question suite aux chroniques de ramadan précédentes, je comprends qu’il est essentiel de resister à son ego, à l’envie de se penser parfaitement pur, angelique, mais dans le monde d’aujourd’hui j’ai des difficultés à appliquer exactement ces principes et j’ai même l’impression qu’ils pourraient être destructeurs si appliqués à la lettre."));
            recyclerView.setAdapter(new CommentaireAdapter(commentaires));

        }


    }
}
