package net.apkode.matano.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import net.apkode.matano.R;
import net.apkode.matano.fragment.EventPlaceholderFragment;

public class EventSectionsPagerAdapter extends FragmentPagerAdapter {
    private Context context;
    private int icons[] = {R.mipmap.ic_culture, R.mipmap.ic_education, R.mipmap.ic_sport};

    public EventSectionsPagerAdapter(FragmentManager fm, Context ctx) {
        super(fm);
        this.context = ctx;
    }

    public View getTabView(int position) {
        View v = LayoutInflater.from(context).inflate(R.layout.tab_view, null);

        ImageView img = (ImageView) v.findViewById(R.id.tabImage);
        img.setImageResource(icons[position]);
        return v;
    }

    @Override
    public Fragment getItem(int position) {
        return EventPlaceholderFragment.newInstance(position + 1, context);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        Drawable drawable = context.getResources().getDrawable(icons[position]);

        ImageSpan imageSpan = new ImageSpan(drawable);
        SpannableString spannableString = new SpannableString("");
        spannableString.setSpan(imageSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }
}