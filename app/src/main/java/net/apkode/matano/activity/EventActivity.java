package net.apkode.matano.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import net.apkode.matano.R;
import net.apkode.matano.adapter.ActualiteAdapter;
import net.apkode.matano.adapter.CommentaireAdapter;
import net.apkode.matano.adapter.ParticipantAdapter;
import net.apkode.matano.model.Actualite;
import net.apkode.matano.model.Commentaire;
import net.apkode.matano.model.Event;
import net.apkode.matano.model.Participant;

import java.util.ArrayList;
import java.util.List;

public class EventActivity extends AppCompatActivity {
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    private Event event;
    private BottomBar mBottomBar;
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewCommentaire;
    private RecyclerView recyclerViewActualite;
    private List<Participant> participants = new ArrayList<>();
    private List<Commentaire> commentaires = new ArrayList<>();
    private List<Actualite> actualites = new ArrayList<>();

    private FrameLayout fmlDetail;
    private FrameLayout fmlCommentaire;
    private FrameLayout fmlParticipant;
    private FrameLayout fmlActualite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        fmlDetail = (FrameLayout)findViewById(R.id.fmlDetail);
        fmlCommentaire = (FrameLayout)findViewById(R.id.fmlCommentaire);
        fmlParticipant = (FrameLayout)findViewById(R.id.fmlParticipant);
        fmlActualite = (FrameLayout)findViewById(R.id.fmlActualite);


        event = (Event) getIntent().getSerializableExtra("Event");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        TextView txtTitre = (TextView) findViewById(R.id.txtTitre);
        TextView txtLieu = (TextView) findViewById(R.id.txtLieu);
        TextView txtHoraire = (TextView) findViewById(R.id.txtHoraire);
        TextView txtTarif = (TextView) findViewById(R.id.txtTarif);
        TextView txtPresentation = (TextView) findViewById(R.id.txtPresentation);
        TextView txtJour = (TextView) findViewById(R.id.txtJour);

        ImageView imageView = (ImageView) findViewById(R.id.main_backdrop);

        assert toolbar != null;
        toolbar.setTitle("#" + event.getRubrique());

        assert txtTitre != null;
        txtTitre.setText(event.getTitre());
        assert txtLieu != null;
        txtLieu.setText(event.getLieu());
        assert txtHoraire != null;
        txtHoraire.setText(event.getHoraire());
        assert txtTarif != null;
        txtTarif.setText(event.getTarif());
        assert txtPresentation != null;
        txtPresentation.setText(event.getPresentation());
        assert txtJour != null;
        txtJour.setText(event.getJour());

        assert imageView != null;
        Glide.with(this)
                .load(event.getImage())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);

        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        participants.add(new Participant(1L, "Awa sow", "https://goodpitch.org/uploads/cache/user_image/max_400_400_monifa-bandele-b.jpg"));
        participants.add(new Participant(1L, "Bachir Selah Zion ", "https://pbs.twimg.com/profile_images/1717956431/BP-headshot-fb-profile-photo_400x400.jpg"));
        participants.add(new Participant(2L, "Bachir Rabo", "http://cps-static.rovicorp.com/3/JPG_400/MI0003/643/MI0003643950.jpg?partner=allrovi.com"));
        participants.add(new Participant(2L, "Bachir Rabo", "https://pbs.twimg.com/profile_images/637722086547587072/g3kWsOVa.jpg"));
        participants.add(new Participant(2L, "Bachir Rabo", "http://servotronicstech.com/wp-content/uploads/2015/03/p-1-400x400.jpg"));
        participants.add(new Participant(1L, "Awa sow", "https://goodpitch.org/uploads/cache/user_image/max_400_400_monifa-bandele-b.jpg"));
        participants.add(new Participant(1L, "Bachir Selah Zion ", "https://pbs.twimg.com/profile_images/1717956431/BP-headshot-fb-profile-photo_400x400.jpg"));
        participants.add(new Participant(2L, "Bachir Rabo", "http://cps-static.rovicorp.com/3/JPG_400/MI0003/643/MI0003643950.jpg?partner=allrovi.com"));
        participants.add(new Participant(2L, "Bachir Rabo", "https://pbs.twimg.com/profile_images/637722086547587072/g3kWsOVa.jpg"));
        participants.add(new Participant(2L, "Bachir Rabo", "http://servotronicstech.com/wp-content/uploads/2015/03/p-1-400x400.jpg"));
        recyclerView.setAdapter(new ParticipantAdapter(participants));

        recyclerViewCommentaire = (RecyclerView) findViewById(R.id.recyclerViewCommentaire);
        recyclerViewCommentaire.setHasFixedSize(true);
        recyclerViewCommentaire.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        commentaires.add(new Commentaire(1l, "Bachir selah zion", "01/02/2016", "8h20", "https://goodpitch.org/uploads/cache/user_image/max_400_400_monifa-bandele-b.jpg","Merci pour cette nouvelle vidéo; je me pose de plus en plus la question suite aux chroniques de ramadan précédentes, je comprends qu’il est essentiel de resister à son ego, à l’envie de se penser parfaitement pur, angelique, mais dans le monde d’aujourd’hui j’ai des difficultés à appliquer exactement ces principes et j’ai même l’impression qu’ils pourraient être destructeurs si appliqués à la lettre."));
        commentaires.add(new Commentaire(1l, "Awa sow", "11/12/2016", "8h20", "https://pbs.twimg.com/profile_images/1717956431/BP-headshot-fb-profile-photo_400x400.jpg","Merci pour cette nouvelle vidéo; je me pose de plus en plus la question suite aux chroniques de ramadan précédentes, je comprends qu’il est essentiel de resister à son ego, à l’envie de se penser parfaitement pur, angelique, mais dans le monde d’aujourd’hui j’ai des difficultés à appliquer exactement ces principes et j’ai même l’impression qu’ils pourraient être destructeurs si appliqués à la lettre."));
        commentaires.add(new Commentaire(1l, "Salif keita", "01/02/2016", "8h20", "http://cps-static.rovicorp.com/3/JPG_400/MI0003/643/MI0003643950.jpg?partner=allrovi.com","Merci pour cette nouvelle vidéo; je me pose de plus en plus la question suite aux chroniques de ramadan précédentes, je comprends qu’il est essentiel de resister à son ego, à l’envie de se penser parfaitement pur, angelique, mais dans le monde d’aujourd’hui j’ai des difficultés à appliquer exactement ces principes et j’ai même l’impression qu’ils pourraient être destructeurs si appliqués à la lettre."));
        commentaires.add(new Commentaire(1l, "Nasser Marabout", "01/02/2016", "8h20", "https://pbs.twimg.com/profile_images/637722086547587072/g3kWsOVa.jpg","Merci pour cette nouvelle vidéo; je me pose de plus en plus la question suite aux chroniques de ramadan précédentes, je comprends qu’il est essentiel de resister à son ego, à l’envie de se penser parfaitement pur, angelique, mais dans le monde d’aujourd’hui j’ai des difficultés à appliquer exactement ces principes et j’ai même l’impression qu’ils pourraient être destructeurs si appliqués à la lettre."));
        commentaires.add(new Commentaire(1l, "Moctar seini", "01/02/2016", "8h20", "http://servotronicstech.com/wp-content/uploads/2015/03/p-1-400x400.jpg","Merci pour cette nouvelle vidéo; je me pose de plus en plus la question suite aux chroniques de ramadan précédentes, je comprends qu’il est essentiel de resister à son ego, à l’envie de se penser parfaitement pur, angelique, mais dans le monde d’aujourd’hui j’ai des difficultés à appliquer exactement ces principes et j’ai même l’impression qu’ils pourraient être destructeurs si appliqués à la lettre."));
        recyclerViewCommentaire.setAdapter(new CommentaireAdapter(commentaires));


        recyclerViewActualite = (RecyclerView) findViewById(R.id.recyclerViewActualite);
        recyclerViewActualite.setHasFixedSize(true);
        recyclerViewActualite.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        actualites.add(new Actualite(1l, "08/11/2016", "11H10", "Then the activity that hosts the fragment implements the OnArticleSelectedListener interface and overrides onArticleSelected() to notify fragment B of the event from fragment"));
        actualites.add(new Actualite(2l, "12/03/2016", "9H42", "o ensure that the host activity implements this interface, fragment A's onAttach() callback method (which the system calls when adding the fragment to the activity) instantiates an instance of OnArticleSelectedListener by casting the Activity that is passed into onAttach"));
        actualites.add(new Actualite(3l, "02/08/2016", "14H17", "When the activity receives a callback through the interface, it can share the information with other fragments in the layout as necessary."));
        actualites.add(new Actualite(4l, "10/06/2016", "23H33", "Although a Fragment is implemented as an object that's independent from an Activity and can be used inside multiple activities, a given instance of a fragment is directly tied to the activity that contains it."));
        recyclerViewActualite.setAdapter(new ActualiteAdapter(actualites));


        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar.setItems(R.menu.bottombar_menu);
        mBottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                if(menuItemId == R.id.btn_menu_description){
                    hideFrameLayout();
                    fmlDetail.setVisibility(View.VISIBLE);
                 //   santeObject.clear();
                //    santeObject.add(new SanteObject("Manger bouger", "Conseil simple, voire u00e9vident, me direz-", "la vie active amu00e8ne certains ", "http://www.africa21.org/wp/wp-content/uploads/2012/11/Sant%C3%A9-en-Afrique.jpg"));
                //    recyclerView.setAdapter(new SanteAdapter(santeObject));

                }else if (menuItemId == R.id.btn_menu_commentaire){
                    hideFrameLayout();
                    fmlCommentaire.setVisibility(View.VISIBLE);
                //    santeObject2.clear();
                //    santeObject2.add(new SanteObject("Fin ebola", "Maladie santé", "la vie active amu00e8ne certains ", "http://www.moda-international.org/wp-content/uploads/2016/01/MODA-sant%C3%A9-afrique.jpg"));
                //    recyclerView.setAdapter(new SanteAdapter(santeObject2));
                }else if(menuItemId == R.id.btn_menu_participant){
                    hideFrameLayout();
                    fmlParticipant.setVisibility(View.VISIBLE);

                }else if(menuItemId == R.id.btn_menu_actualite){
                    hideFrameLayout();
                    fmlActualite.setVisibility(View.VISIBLE);
                }

                //   mMessageView.setText(TabMessage.get(menuItemId, false));

            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                //  Log.e("e", "onMenuTabReSelected menuItemId "+menuItemId);
                // Toast.makeText(getApplicationContext(), TabMessage.get(menuItemId, true), Toast.LENGTH_SHORT).show();
            }
        });

        // Setting colors for different tabs when there's more than three of them.
        // You can set colors for tabs in three different ways as shown below.
        /*mBottomBar.mapColorForTab(0, ContextCompat.getColor(this, R.color.colorAccent));
        mBottomBar.mapColorForTab(1, 0xFF5D4037);
        mBottomBar.mapColorForTab(2, "#7B1FA2");
        mBottomBar.mapColorForTab(3, "#FF5252");
        mBottomBar.mapColorForTab(4, "#FF9800");*/

    }

    private void hideFrameLayout(){
        fmlDetail.setVisibility(View.GONE);
        fmlCommentaire.setVisibility(View.GONE);
        fmlParticipant.setVisibility(View.GONE);
        fmlActualite.setVisibility(View.GONE);
    }

    public void startEventPhoto(View view) {
        Intent intent = new Intent(getApplicationContext(), EventPhoto.class);
        intent.putExtra("photo", event.getImagefull());
        startActivity(intent);

    }

    public void startEventVideo(View view) {
        Intent intent = new Intent(getApplicationContext(), EventVideo.class);
        intent.putExtra("video", event.getVideo());
        startActivity(intent);

    }

    public void startEventShareFacebook(View view) {

        if (event != null) {
            String presentation = event.getPresentation();
            String titre = event.getTitre();
            String categorie = event.getCategorie();
            String rubrique = event.getRubrique();
            String image = event.getImage();
            String imagefull = event.getImagefull();
            String horaire = event.getHoraire();
            String lieu = event.getLieu();
            String tarif = event.getTarif();

            String description = "Categorie : " + categorie + "\n" + "Rubrique : " + rubrique + "\n" + "Lieu : " + lieu + "\n" + "Horaire : " + horaire + "\n" + "Tarif : +" + tarif + "\n" + "Detail : " + presentation;

            if (ShareDialog.canShow(ShareLinkContent.class)) {
                ShareLinkContent content = new ShareLinkContent.Builder()
                        .setContentUrl(Uri.parse("https://www.facebook.com/Niameyzze227"))
                        .setImageUrl(Uri.parse(imagefull))
                        .setContentDescription(description)
                        .setContentTitle(titre)
                        .build();
                shareDialog.show(content);
            }
        }
    }

}
