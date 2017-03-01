package matano.apkode.net.matano.config;

import android.app.Application;

import com.bumptech.glide.request.target.ViewTarget;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.database.FirebaseDatabase;

import matano.apkode.net.matano.R;


public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Firebase Disk Persistence
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        // Facebook
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        // GLIDE
        ViewTarget.setTagId(R.id.glide_tag);

    }

}
