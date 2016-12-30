package matano.apkode.net.matano;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
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
import android.widget.TextView;

import com.bumptech.glide.Glide;

import matano.apkode.net.matano.config.App;
import matano.apkode.net.matano.config.Db;
import matano.apkode.net.matano.config.Utils;
import matano.apkode.net.matano.fragment.event.EventInfoFragment;
import matano.apkode.net.matano.fragment.event.EventParticipantFragment;
import matano.apkode.net.matano.fragment.event.EventPrivateFragment;
import matano.apkode.net.matano.fragment.event.EventTimelineFragment;


public class EventActivity extends AppCompatActivity {
    private App app;
    private String incomeEventUid;
    private Db db;

    private TextView textViewToolbarTitle;
    private ImageView imageViewCover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        app = (App) getApplicationContext();
        db = new Db(this);

        incomeEventUid = getIntent().getStringExtra(Utils.ARG_EVENT_UID);

        if (incomeEventUid == null) {
            finishActivity();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_action_navigation_arrow_back_padding);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Drawable upArrow = ContextCompat.getDrawable(this, R.mipmap.ic_action_navigation_arrow_back_padding);
        upArrow.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        imageViewCover = (ImageView) findViewById(R.id.imageViewCover);
        textViewToolbarTitle = (TextView) findViewById(R.id.textViewToolbarTitle);
        textViewToolbarTitle.setText(getResources().getString(R.string.page_event_info));

        Glide.with(this).load("http://2.bp.blogspot.com/-WPu45Bfj0gc/Tyq68PDD6wI/AAAAAAACDfU/bwIKOTt_PAA/s1600/Grand+finale+Mustafa+Hassanali+collection+at+FIMA+in+Niamey+in+Niger+on+26+November+2011+(2).JPG").into(imageViewCover);


        EventPagerAdapter eventPagerAdapter = new EventPagerAdapter(getSupportFragmentManager());

        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(eventPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            assert tab != null;
            tab.setCustomView(eventPagerAdapter.getTabView(i));
        }

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position + 1) {

                    case 1:
                        textViewToolbarTitle.setText(getResources().getString(R.string.page_event_info));
                        break;
                    case 2:
                        textViewToolbarTitle.setText(getResources().getString(R.string.page_event_timeline));
                        break;
                    case 3:
                        textViewToolbarTitle.setText(getResources().getString(R.string.page_event_participant));
                        break;
                    case 4:
                        textViewToolbarTitle.setText(getResources().getString(R.string.page_event_tchat));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
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

    private void finishActivity() {
        finish();
    }


    /**
     * FragmentPagerAdapter
     */
    public class EventPagerAdapter extends FragmentPagerAdapter {
        private int icons[] = {R.mipmap.ic_action_notification_event_note_padding, R.mipmap.ic_action_action_list_padding, R.mipmap.ic_action_action_account_child_padding, R.mipmap.ic_action_communication_chat_padding};


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
                    return EventInfoFragment.newInstance(incomeEventUid);
                case 2:
                    return EventTimelineFragment.newInstance(incomeEventUid);
                case 3:
                    return EventParticipantFragment.newInstance(incomeEventUid);
                case 4:
                    return EventPrivateFragment.newInstance(incomeEventUid);
                default:
                    return null;
            }

        }

        @Override
        public int getCount() {
            return 4;
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
