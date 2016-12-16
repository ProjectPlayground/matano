package matano.apkode.net.matano.fragmentpageradapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.Toolbar;

import matano.apkode.net.matano.fragment.MainEventFragment;


public class MainPagerAdapter extends FragmentPagerAdapter {
    Toolbar toolbar;
    private Context context;

    public MainPagerAdapter(FragmentManager fm, Context context, Toolbar toolbar) {
        super(fm);
        this.context = context;
        this.toolbar = toolbar;
    }


    @Override
    public Fragment getItem(int position) {
        return MainEventFragment.newInstance(position + 1, context);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Culture";
            case 1:
                return "Education";
            case 2:
                return "Sport";
        }
        return null;
    }

}