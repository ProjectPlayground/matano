package matano.apkode.net.matano.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import matano.apkode.net.matano.R;
import matano.apkode.net.matano.helper.Sleep;
import matano.apkode.net.matano.interfaces.ISleep;

public class SplashscreenActivity extends AppCompatActivity implements ISleep {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //AuthUI.getInstance().signOut(this);
        setContentView(R.layout.activity_splashscreen);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Sleep(this, this).execute();
    }

    @Override
    public void getResponse() {
        startActivity(new Intent(getApplicationContext(), SignInActivity.class));
        finish();
    }
}
