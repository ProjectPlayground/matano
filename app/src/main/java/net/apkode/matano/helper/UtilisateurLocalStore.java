package net.apkode.matano.helper;

import android.content.Context;
import android.content.SharedPreferences;

import net.apkode.matano.model.Utilisateur;


public class UtilisateurLocalStore {
    public static final String SP_NAME = "utilisateur";

    public static final String NOM = "nom";
    public static final String PRENOM = "prenom";
    public static final String TELEPHONE = "telephone";
    public static final String PASSWORD = "password";
    public static final String SEXE = "sexe";
    public static final String EMAIL = "email";
    public static final String PRESENTATION = "presentation";

    public static final String IS_LOGGED_IN = "isLogged";

    SharedPreferences sharedPreferences;

    public UtilisateurLocalStore(Context context) {
        sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    public void storeUtilisateur(Utilisateur utilisateur) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(NOM, utilisateur.getNom());
        editor.putString(PRENOM, utilisateur.getPrenom());
        editor.putString(TELEPHONE, utilisateur.getTelephone());
        editor.putString(PASSWORD, utilisateur.getPassword());
        editor.putString(SEXE, utilisateur.getSexe());
        editor.putString(EMAIL, utilisateur.getEmail());
        editor.putString(PRESENTATION, utilisateur.getPresentation());
        editor.commit();

    }


    public Utilisateur getUtilisateur() {
        String nom = sharedPreferences.getString(NOM, "");
        String prenom = sharedPreferences.getString(PRENOM, "");
        String telephone = sharedPreferences.getString(TELEPHONE, "");
        String password = sharedPreferences.getString(PASSWORD, "");
        String sexe = sharedPreferences.getString(SEXE, "");
        String email = sharedPreferences.getString(EMAIL, "");
        String presentation = sharedPreferences.getString(PRESENTATION, "");

        return new Utilisateur(nom, prenom, telephone, password, sexe, email, presentation);
    }

    public void clearUtilisateur() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    public void setUtilisateurLogin(boolean loggedIn) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_LOGGED_IN, loggedIn);
        editor.commit();
    }


    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }


}
