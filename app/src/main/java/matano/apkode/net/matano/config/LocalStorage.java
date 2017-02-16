package matano.apkode.net.matano.config;


import android.content.Context;
import android.content.SharedPreferences;

public class LocalStorage {
    private static final String MyPREFERENCES = "net.apkode.matano.CONTRY_CITY";
    private static final String CONTRY = "country";
    private static final String CITY = "city";
    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public LocalStorage(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void storeCountry(String country) {
        editor.putString(CONTRY, country);
        editor.commit();
    }

    public void storeCity(String city) {
        editor.putString(CITY, city);
        editor.commit();
    }

    public String getCountry() {
        return sharedPreferences.getString(CONTRY, "Niger");
    }

    public String getCity() {
        return sharedPreferences.getString(CITY, "Niamey");
    }

    public boolean isCountryStored() {
        return sharedPreferences.contains(CONTRY);
    }

    public boolean isCityStored() {
        return sharedPreferences.contains(CITY);
    }

    public void clearCountry() {
        editor.remove(CONTRY);
        editor.commit();
    }

    public void clearCity() {
        editor.remove(CITY);
        editor.commit();
    }

    public void clearAll() {
        editor.clear();
        editor.commit();
    }

}
