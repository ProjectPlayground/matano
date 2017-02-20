package matano.apkode.net.matano.config;

import android.app.Application;

import com.bumptech.glide.request.target.ViewTarget;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.database.FirebaseDatabase;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;
import matano.apkode.net.matano.R;


public class App extends Application {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    // private static final String TWITTER_KEY = "4gBq7NEKVe6woifDcyxXzQD6O";
    // private static final String TWITTER_SECRET = "VAvClzixOayRTLcLtdUhVH4zO9Q8JXKrOA4RTT01IavwkoicSK";

    // TODO Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "sEj0jBiSTz80lQPVKAkj5a2BY";
    private static final String TWITTER_SECRET = "J4ZDcAZOeRrJ3e3Is88iKMmSOwdDtcbpM0oVhedXRVypXwOx7B";

    @Override
    public void onCreate() {
        super.onCreate();
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));

        // Firebase Disk Persistence
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        // Facebook
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        // TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        // Fabric.with(this, new Twitter(authConfig));

        // GLIDE
        ViewTarget.setTagId(R.id.glide_tag);


    }

}
