package matano.apkode.net.matano.config;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;

import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;


public class Share {
    private Context context;
    private ShareDialog shareDialog;
    private Activity activity;

    public Share(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
        shareDialog = new ShareDialog(activity);

    }

    public void shareLink(String title, String description, String hashtag, String url) {
        if (ShareDialog.canShow(ShareLinkContent.class)) {

            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentTitle(title)
                    .setContentDescription(description)
                    .setShareHashtag(new ShareHashtag.Builder().setHashtag(hashtag).build())
                    .setImageUrl(Uri.parse(url))
                    .setContentUrl(Uri.parse(url))
                    .build();

            shareDialog.show(linkContent);
        }

    }

}
