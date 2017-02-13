package matano.apkode.net.matano.config;

import android.app.Application;

import com.bumptech.glide.request.target.ViewTarget;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import matano.apkode.net.matano.R;


public class App extends Application {
    // TODO Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "sEj0jBiSTz80lQPVKAkj5a2BY";
    private static final String TWITTER_SECRET = "J4ZDcAZOeRrJ3e3Is88iKMmSOwdDtcbpM0oVhedXRVypXwOx7B";

    @Override
    public void onCreate() {
        super.onCreate();

        // Facebook
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        // TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        // Fabric.with(this, new Twitter(authConfig));

        // GLIDE
        ViewTarget.setTagId(R.id.glide_tag);


    }

}
