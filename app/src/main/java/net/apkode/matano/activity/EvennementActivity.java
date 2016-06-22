package net.apkode.matano.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import net.apkode.matano.R;
import net.apkode.matano.db.DBActualite;
import net.apkode.matano.db.DBCommentaire;
import net.apkode.matano.db.DBImageGalerie;
import net.apkode.matano.db.DBParticipant;
import net.apkode.matano.fragment.ActualiteFragment;
import net.apkode.matano.fragment.CommentaireFragment;
import net.apkode.matano.fragment.ImageGalerieFragment;
import net.apkode.matano.fragment.ParticipantFragment;
import net.apkode.matano.fragment.PresentationFragment;
import net.apkode.matano.model.Evennement;


public class EvennementActivity extends AppCompatActivity {
    private CallbackManager callbackManager;
    private ShareDialog shareDialog;
    private Evennement evennement;
    private BottomBar mBottomBar;

    private PresentationFragment presentationFragment;
    private CommentaireFragment commentaireFragment;
    private ParticipantFragment participantFragment;
    private ImageGalerieFragment imageGalerieFragment;
    private ActualiteFragment actualiteFragment;

    private DBCommentaire dbCommentaire;
    private DBParticipant dbParticipant;
    private DBImageGalerie dbImageGalerie;
    private DBActualite dbActualite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evennement);

        evennement = (Evennement) getIntent().getSerializableExtra("Evennement");

        Bundle bundle = new Bundle();
        bundle.putSerializable("Evennement", evennement);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("#" + evennement.getRubrique());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbCommentaire = new DBCommentaire(this);
        dbParticipant = new DBParticipant(this);
        dbImageGalerie = new DBImageGalerie(this);
        dbActualite = new DBActualite(this);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

        presentationFragment = PresentationFragment.newInstance();
        presentationFragment.setArguments(bundle);

        commentaireFragment = CommentaireFragment.newInstance();
        commentaireFragment.setArguments(bundle);

        participantFragment = ParticipantFragment.newInstance();
        participantFragment.setArguments(bundle);

        imageGalerieFragment = ImageGalerieFragment.newInstance();
        imageGalerieFragment.setArguments(bundle);

        actualiteFragment = ActualiteFragment.newInstance();
        actualiteFragment.setArguments(bundle);

        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar.setItems(R.menu.bottom_bar);
        mBottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.btn_menu_description) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fmlContainer, presentationFragment).commit();
                } else if (menuItemId == R.id.btn_menu_commentaire) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fmlContainer, commentaireFragment).commit();
                } else if (menuItemId == R.id.btn_menu_participant) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fmlContainer, participantFragment).commit();
                } else if (menuItemId == R.id.btn_menu_image) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fmlContainer, imageGalerieFragment).commit();
                } else if (menuItemId == R.id.btn_menu_actualite) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fmlContainer, actualiteFragment).commit();
                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                //  Log.e("e", "onMenuTabReSelected menuItemId "+menuItemId);
                // Toast.makeText(getApplicationContext(), TabMessage.get(menuItemId, true), Toast.LENGTH_SHORT).show();
            }
        });

        int redColor = Color.parseColor("#FF0000");

       /* BottomBarBadge bottomBarBadgeCommentaire = mBottomBar.makeBadgeForTabAt(1, redColor, 5);
        BottomBarBadge bottomBarBadgeParticipant = mBottomBar.makeBadgeForTabAt(2, redColor, 10);

        bottomBarBadgeCommentaire.setAutoShowAfterUnSelection(true);
        bottomBarBadgeParticipant.setAutoShowAfterUnSelection(true);*/

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Necessary to restore the BottomBar's state, otherwise we would
        // lose the current tab on orientation change.
        mBottomBar.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.evennement, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_share) {
            if (evennement != null) {
                String presentation = evennement.getPresentation();
                String titre = evennement.getTitre();
                String categorie = evennement.getCategorie();
                String rubrique = evennement.getRubrique();
                String image = evennement.getImage();
                String imagefull = evennement.getImagefull();
                String horaire = evennement.getHoraire();
                String lieu = evennement.getLieu();
                String tarif = evennement.getTarif();

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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void startEventPhoto(View view) {
        Intent intent = new Intent(getApplicationContext(), EvennementPhoto.class);
        intent.putExtra("photo", evennement.getImagefull());
        startActivity(intent);

    }

    public void startEventVideo(View view) {
        Intent intent = new Intent(getApplicationContext(), EvennementVideo.class);
        intent.putExtra("video", evennement.getVideo());
        startActivity(intent);

    }
}