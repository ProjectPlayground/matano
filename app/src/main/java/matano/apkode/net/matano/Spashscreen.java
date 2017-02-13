package matano.apkode.net.matano;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Spashscreen extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spashscreen);

        SpashscreenPagerAdapter pagerAdapter = new SpashscreenPagerAdapter(getSupportFragmentManager());

        ViewPager viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(pagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public static class SpashscreenFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";
        private TextView textView;

        public static SpashscreenFragment newInstance(int position) {
            SpashscreenFragment spashscreenFragment = new SpashscreenFragment();
            Bundle args = new Bundle();
            args.putString(ARG_SECTION_NUMBER, position + "");
            spashscreenFragment.setArguments(args);
            return spashscreenFragment;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_splashscreen, container, false);

            textView = (TextView) view.findViewById(R.id.textView);

            return view;
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            textView.setText(getArguments().getString(ARG_SECTION_NUMBER));
        }

    }

    public class SpashscreenPagerAdapter extends FragmentPagerAdapter {
        private static final int NUM_ITEMS = 3;

        public SpashscreenPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return SpashscreenFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }
    }


}
