package matano.apkode.net.matano.helper;

import android.content.Context;
import android.os.AsyncTask;

import matano.apkode.net.matano.interfaces.ISleep;

public class Sleep extends AsyncTask {
    private static final int SPLASH_TIME = 1000;
    private Context context;
    private ISleep iSleep;

    public Sleep(ISleep reference, Context ctx) {
        iSleep = reference;
        context = ctx;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Object doInBackground(Object[] params) {
        try {
            Thread.sleep(SPLASH_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        iSleep.getResponse();

    }
}