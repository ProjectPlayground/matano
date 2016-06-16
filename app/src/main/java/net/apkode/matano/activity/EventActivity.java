package net.apkode.matano.activity;

import android.content.Intent;
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
import net.apkode.matano.fragment.ActualiteFragment;
import net.apkode.matano.fragment.CommentaireFragment;
import net.apkode.matano.fragment.ParticipantFragment;
import net.apkode.matano.fragment.PresentationFragment;
import net.apkode.matano.model.Event;


public class EventActivity extends AppCompatActivity {
    private CallbackManager callbackManager;
    private ShareDialog shareDialog;
    private Event event;
    private BottomBar mBottomBar;

    private PresentationFragment presentationFragment;
    private CommentaireFragment commentaireFragment;
    private ParticipantFragment participantFragment;
    private ActualiteFragment actualiteFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        event = (Event) getIntent().getSerializableExtra("Event");

        Bundle bundle = new Bundle();
        bundle.putSerializable("Event", event);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("#" + event.getRubrique());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                } else if (menuItemId == R.id.btn_menu_actualite) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fmlContainer, actualiteFragment).commit();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_share) {
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
            return true;
        }

        return super.onOptionsItemSelected(item);
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


}
