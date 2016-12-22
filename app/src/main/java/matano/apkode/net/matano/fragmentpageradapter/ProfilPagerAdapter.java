package matano.apkode.net.matano.fragmentpageradapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import matano.apkode.net.matano.R;
import matano.apkode.net.matano.fragment.profil.ProfilEventFragment;
import matano.apkode.net.matano.fragment.profil.ProfilFriendFragment;
import matano.apkode.net.matano.fragment.profil.ProfilInfoFragment;
import matano.apkode.net.matano.fragment.profil.ProfilPhotoFragment;
import matano.apkode.net.matano.fragment.profil.ProfilTicketFragment;

public class ProfilPagerAdapter extends FragmentPagerAdapter {
    private static String ARG_USER_UID = "userUid";
    Toolbar toolbar;
    private Context context;
    private int icons[] = {R.mipmap.ic_action_action_account_box_padding, R.mipmap.ic_action_image_image_padding, R.mipmap.ic_action_notification_event_note_padding, R.mipmap.ic_action_action_account_balance_wallet_padding, R.mipmap.ic_action_action_account_child_padding};

    public ProfilPagerAdapter(FragmentManager fm, Context ctx, Toolbar toolbar, String userUid) {
        super(fm);
        this.context = ctx;
        this.toolbar = toolbar;
        ARG_USER_UID = userUid;
    }

    public View getTabView(int position) {
        View v = LayoutInflater.from(context).inflate(R.layout.tab_view, null);

        ImageView img = (ImageView) v.findViewById(R.id.tabImage);
        img.setImageResource(icons[position]);
        return v;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position + 1) {

            case 1:
                return new ProfilInfoFragment().newInstance(context, ARG_USER_UID);
            case 2:
                return new ProfilPhotoFragment().newInstance(context, ARG_USER_UID);
            case 3:
                return new ProfilEventFragment().newInstance(context, ARG_USER_UID);
            case 4:
                return new ProfilTicketFragment().newInstance(context, ARG_USER_UID);
            case 5:
                return new ProfilFriendFragment().newInstance(context, ARG_USER_UID);
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 5;
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
