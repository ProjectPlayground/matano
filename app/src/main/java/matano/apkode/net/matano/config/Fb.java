package matano.apkode.net.matano.config;

import android.app.Application;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Fb extends Application {
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
        rootDatabase = FirebaseDatabase.getInstance();
        refMessage = rootDatabase.getReference("message");
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
