package matano.apkode.net.matano.config;

import android.app.Application;

import com.bumptech.glide.request.target.ViewTarget;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;
import matano.apkode.net.matano.R;


public class Fb extends Application {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "sEj0jBiSTz80lQPVKAkj5a2BY";
    private static final String TWITTER_SECRET = "J4ZDcAZOeRrJ3e3Is88iKMmSOwdDtcbpM0oVhedXRVypXwOx7B";

    private FirebaseDatabase rootDatabase;
    private DatabaseReference refMessage;

    public Fb(FirebaseDatabase rootDatabase, DatabaseReference refMessage) {
        this.rootDatabase = rootDatabase;
        this.refMessage = refMessage;
    }

    public Fb() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        rootDatabase = FirebaseDatabase.getInstance();
        refMessage = rootDatabase.getReference("message");

        ViewTarget.setTagId(R.id.glide_tag);
    }

    public FirebaseDatabase getRootDatabase() {
        return rootDatabase;
    }

    public void setRootDatabase(FirebaseDatabase rootDatabase) {
        this.rootDatabase = rootDatabase;
    }

    public DatabaseReference getRefMessage() {
        return refMessage;
    }

    public void setRefMessage(DatabaseReference refMessage) {
        this.refMessage = refMessage;
    }
}
