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
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import matano.apkode.net.matano.config.App;
import matano.apkode.net.matano.config.Db;
import matano.apkode.net.matano.config.Utils;
import matano.apkode.net.matano.fragment.user.UserEventFragment;
import matano.apkode.net.matano.fragment.user.UserFriendFragment;
import matano.apkode.net.matano.fragment.user.UserInfoFragment;
import matano.apkode.net.matano.fragment.user.UserTicketFragment;
import matano.apkode.net.matano.fragment.user.UserTimelineFragment;

public class UserActivity extends AppCompatActivity {
    private App app;
    private String incomeUserUid;
    private Db db;

    private ViewPager mViewPager;
    private TextView textViewToolbarTitle;
    private int countPage = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        app = (App) getApplicationContext();

        incomeUserUid = getIntent().getStringExtra(Utils.ARG_USER_UID);

        if (incomeUserUid == null) {
            finishActivity();
        }


        if (incomeUserUid.equals(app.getCurrentUserUid())) {
            countPage = 5;
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_action_navigation_arrow_back_padding);

        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Drawable upArrow = ContextCompat.getDrawable(this, R.mipmap.ic_action_navigation_arrow_back_padding);
        upArrow.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        textViewToolbarTitle = (TextView) findViewById(R.id.textViewToolbarTitle);
        textViewToolbarTitle.setText(getResources().getString(R.string.page_profil_info));

        ProfilPagerAdapter profilPagerAdapter = new ProfilPagerAdapter(getSupportFragmentManager());

        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(profilPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(profilPagerAdapter.getTabView(i));
        }

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position + 1) {
                    case 1:
                        textViewToolbarTitle.setText(getResources().getString(R.string.page_profil_info));
                        break;
                    case 2:
                        textViewToolbarTitle.setText(getResources().getString(R.string.page_profil_timeline));
                        break;
                    case 3:
                        textViewToolbarTitle.setText(getResources().getString(R.string.page_profil_event));
                        break;
                    case 4:
                        textViewToolbarTitle.setText(getResources().getString(R.string.page_profil_tchat));
                        break;
                    case 5:
                        textViewToolbarTitle.setText(getResources().getString(R.string.page_profil_ticket));
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
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_profil, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_logout:
                FirebaseAuth.getInstance().signOut();
                return true;
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void finishActivity() {
        finish();
    }


    /**
     * FragmentPagerAdapter
     */
    public class ProfilPagerAdapter extends FragmentPagerAdapter {
        private int icons[] = {R.mipmap.ic_action_action_account_box_padding, R.mipmap.ic_action_image_image_padding, R.mipmap.ic_action_notification_event_note_padding, R.mipmap.ic_action_action_account_child_padding, R.mipmap.ic_action_action_account_balance_wallet_padding};

        public ProfilPagerAdapter(FragmentManager fm) {
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
                    return UserInfoFragment.newInstance(incomeUserUid);
                case 2:
                    return UserTimelineFragment.newInstance(incomeUserUid);
                case 3:
                    return UserEventFragment.newInstance(incomeUserUid);
                case 4:
                    return UserFriendFragment.newInstance(incomeUserUid);
                case 5:
                    return UserTicketFragment.newInstance(incomeUserUid);
                default:
                    return null;
            }

        }

        @Override
        public int getCount() {
            return countPage;
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
