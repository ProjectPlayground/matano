package matano.apkode.net.matano;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import matano.apkode.net.matano.config.LocalStorage;
import matano.apkode.net.matano.config.Utils;
import matano.apkode.net.matano.fragment.event.EventInfoFragment;
import matano.apkode.net.matano.fragment.event.EventParticipantFragment;
import matano.apkode.net.matano.fragment.event.EventPhotoFragment;
import matano.apkode.net.matano.fragment.event.EventPrivateFragment;
import matano.apkode.net.matano.fragment.event.EventTimelineFragment;


public class EventActivity extends AppCompatActivity {
    private String eventUid;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;
    private String currentUserUid;
    private String currentUserContry;
    private String currentUserCity;
    private LocalStorage localStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        eventUid = getIntent().getStringExtra(Utils.ARG_EVENT_UID);

        localStorage = new LocalStorage(this);
        currentUserContry = localStorage.getContry();
        currentUserCity = localStorage.getCity();

        if (eventUid == null) {
            finishActivity();
        }

        if (!localStorage.isContryStored() || currentUserContry == null) {
            goContryActivity();
        }

        if (!localStorage.isCityStored() || currentUserCity == null) {
            goCityActivity();
        }

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    finishActivity();
                } else {
                    currentUserUid = user.getUid();
                }
            }
        };

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_action_navigation_arrow_back_padding);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        EventPagerAdapter eventPagerAdapter = new EventPagerAdapter(getSupportFragmentManager());

        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(eventPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(eventPagerAdapter.getTabView(i));
        }


    }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAuth != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void goContryActivity() {
        Intent intent = new Intent(this, ContryActivity.class);
        startActivity(intent);
        finishActivity();
    }

    private void goCityActivity() {
        Intent intent = new Intent(this, CityActivity.class);
        startActivity(intent);
        finishActivity();
    }

    private void finishActivity() {
        finish();
    }


    /**
     * FragmentPagerAdapter
     */
    public class EventPagerAdapter extends FragmentPagerAdapter {
        private int icons[] = {R.mipmap.ic_action_notification_event_note_padding, R.mipmap.ic_action_action_list_padding, R.mipmap.ic_action_image_image_padding, R.mipmap.ic_action_action_account_child_padding, R.mipmap.ic_action_communication_chat_padding};


        public EventPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public View getTabView(int position) {
            View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.tab_view, null);

            ImageView img = (ImageView) v.findViewById(R.id.tabImage);
            img.setImageResource(icons[position]);
            return v;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position + 1) {

                case 1:
                    return EventInfoFragment.newInstance(eventUid);
                case 2:
                    return EventTimelineFragment.newInstance(eventUid);
                case 3:
                    return EventPhotoFragment.newInstance(eventUid);
                case 4:
                    return EventParticipantFragment.newInstance(eventUid);
                case 5:
                    return EventPrivateFragment.newInstance(eventUid);
                default:
                    return EventInfoFragment.newInstance(eventUid);
            }

        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            Drawable drawable = getResources().getDrawable(icons[position]);

            ImageSpan imageSpan = new ImageSpan(drawable);
            SpannableString spannableString = new SpannableString("");
            spannableString.setSpan(imageSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            return spannableString;
        }
    }


}