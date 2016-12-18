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
import matano.apkode.net.matano.fragment.event.EventDefaultFragment;
import matano.apkode.net.matano.fragment.event.EventInfoFragment;
import matano.apkode.net.matano.fragment.event.EventNewFragment;
import matano.apkode.net.matano.fragment.event.EventParticipantFragment;
import matano.apkode.net.matano.fragment.event.EventPhotoFragment;
import matano.apkode.net.matano.fragment.event.EventPrivateFragment;


public class EventPagerAdapter extends FragmentPagerAdapter {
    Toolbar toolbar;
    private Context context;
    private String eventKey;
    private int icons[] = {R.mipmap.ic_action_notification_event_note_padding, R.mipmap.ic_action_action_list_padding, R.mipmap.ic_action_image_image_padding, R.mipmap.ic_action_action_account_child_padding, R.mipmap.ic_action_communication_chat_padding};


    public EventPagerAdapter(FragmentManager fm, Context ctx, Toolbar toolbar, String key) {
        super(fm);
        this.context = ctx;
        this.toolbar = toolbar;
        this.eventKey = key;
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
                return new EventInfoFragment().newInstance(context, eventKey);
            case 2:
                return new EventNewFragment().newInstance(context, eventKey);
            case 3:
                return new EventPhotoFragment().newInstance(context, eventKey);
            case 4:
                return new EventParticipantFragment().newInstance(context, eventKey);
            case 5:
                return new EventPrivateFragment().newInstance(context, eventKey);
            default:
                return new EventDefaultFragment().newInstance(context, eventKey);
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
