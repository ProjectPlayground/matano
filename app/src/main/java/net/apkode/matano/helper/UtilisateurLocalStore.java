package net.apkode.matano.helper;

import android.content.Context;
import android.content.SharedPreferences;

import net.apkode.matano.model.Utilisateur;


public class UtilisateurLocalStore {
    public static final String SP_NAME = "utilisateur";
    public static final String TELEPHONE = "telephone";
    public static final String PASSWORD = "password";
    public static final String IS_LOGGED_IN = "isLogged";

    SharedPreferences sharedPreferences;

    public UtilisateurLocalStore(Context context) {
        sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    public void storeUtilisateur(Utilisateur utilisateur){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TELEPHONE, utilisateur.getTelephone());
        editor.putString(PASSWORD, utilisateur.getPassword());
        editor.commit();

    }

    public Utilisateur getUtilisateur(){
        String telephone = sharedPreferences.getString(TELEPHONE, "");
        String password = sharedPreferences.getString(PASSWORD, "");

        return  new Utilisateur(telephone, password);
    }

    public void clearUtilisateur(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    public void setUtilisateurLogin(boolean loggedIn){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_LOGGED_IN, loggedIn);
        editor.commit();
    }


    public boolean isLoggedIn(){
        if (sharedPreferences.getBoolean(IS_LOGGED_IN, false)){
            return true;
        }else {
            return false;
        }
    }


}
