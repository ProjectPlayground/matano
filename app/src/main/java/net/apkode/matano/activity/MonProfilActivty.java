package net.apkode.matano.activity;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import net.apkode.matano.R;
import net.apkode.matano.api.APIUtilisateur;
import net.apkode.matano.helper.ApiRestClient;
import net.apkode.matano.helper.UtilisateurLocalStore;
import net.apkode.matano.interfaces.IUtilisateur;
import net.apkode.matano.model.Utilisateur;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import cz.msebera.android.httpclient.Header;

public class MonProfilActivty extends AppCompatActivity implements IUtilisateur {
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int GALERIE_CAPTURE_IMAGE_REQUEST_CODE = 200;
    private UtilisateurLocalStore utilisateurLocalStore;
    private TextView txtUpdateTelephone;
    private TextView txtUpdateNom;
    private TextView txtUpdatePrenom;
    private TextView txtUpdateEmail;
    private TextView txtUpdatePresentation;
    private ImageView imvImageProfil;
    private ProgressDialog progress;
    private APIUtilisateur apiUtilisateur;
    private Utilisateur utilisateur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon_profil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        apiUtilisateur = new APIUtilisateur(this, this);
        utilisateurLocalStore = new UtilisateurLocalStore(this);

        txtUpdateTelephone = (TextView) findViewById(R.id.txtUpdateTelephone);
        txtUpdateNom = (TextView) findViewById(R.id.txtUpdateNom);
        txtUpdatePrenom = (TextView) findViewById(R.id.txtUpdatePrenom);
        txtUpdateEmail = (TextView) findViewById(R.id.txtUpdateEmail);
        txtUpdatePresentation = (TextView) findViewById(R.id.txtUpdatePresentation);

        imvImageProfil = (ImageView) findViewById(R.id.imvImageProfil);

        progress = new ProgressDialog(this);
        progress.setCancelable(true);
        progress.setMessage("Envoie en cours ...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setProgress(0);
        progress.setMax(100);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!utilisateurLocalStore.isLoggedIn()) {
            startActivity(new Intent(getApplicationContext(), ConnexionActivity.class));
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        utilisateur = utilisateurLocalStore.getUtilisateur();

        txtUpdateTelephone.setText(utilisateur.getTelephone());
        txtUpdateNom.setText(utilisateur.getNom());
        txtUpdatePrenom.setText(utilisateur.getPrenom());
        txtUpdateEmail.setText(utilisateur.getEmail());
        txtUpdatePresentation.setText(utilisateur.getPresentation());

        Glide.with(this)
                .load(utilisateur.getImage())
                .crossFade()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imvImageProfil);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    public void startUpdateContact(View view) {
        startActivity(new Intent(getApplicationContext(), UpdateContact.class));
    }

    public void startUpdateProfil(View view) {
        startActivity(new Intent(getApplicationContext(), UpdateProfil.class));
    }

    public void startUpdatePresentation(View view) {
        startActivity(new Intent(getApplicationContext(), UpdatePresentation.class));
    }

    public void startSharePhoto(View view) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("En envoyant votre photo, vous acceptez qu'elle soit publiée sur les réseaux sociaux d'orange ?");

        alertDialogBuilder.setPositiveButton("Partager", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(getTempFile(getApplicationContext())));
                startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
            }
        });

        alertDialogBuilder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    private File getTempFile(Context context) {
        //it will return /sdcard/image.tmp
        final File path = new File(Environment.getExternalStorageDirectory(), context.getPackageName());
        if (!path.exists()) {
            path.mkdir();
        }
        return new File(path, "image.tmp");
    }


    public void startPickPhoto(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("En envoyant votre photo, vous acceptez qu'elle soit publiée sur les réseaux sociaux d'orange ?");

        alertDialogBuilder.setPositiveButton("Partager", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, GALERIE_CAPTURE_IMAGE_REQUEST_CODE);
            }
        });

        alertDialogBuilder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            final File file = getTempFile(this);
            try {
                Bitmap captureBmp = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(file));
                Uri tempUri = getCaptureUri(getApplicationContext(), captureBmp);
                sendDataToServeur(tempUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if (requestCode == GALERIE_CAPTURE_IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            sendDataToServeur(data.getData());
        }
    }

    public Uri getCaptureUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "OrangeNigerEvent", null);
        return Uri.parse(path);
    }


    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(
                this,
                contentUri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        int column_index =
                cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private void sendDataToServeur(final Uri uri) {
        String path = getRealPathFromURI(uri);
        File image = new File(path);
        RequestParams params = new RequestParams();
        try {
            params.put("image", image);
        } catch (FileNotFoundException e) {
        }


        ApiRestClient.post("utilisateurs-images.php", params, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                progress.show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                progress.dismiss();
                Toast.makeText(getApplicationContext(), getString(R.string.error_reseau), Toast.LENGTH_LONG).show();
            }


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String sendReponse = response.get(0).toString();

                    if (sendReponse.equals("0")) {
                        progress.dismiss();
                        Toast.makeText(getApplicationContext(), getString(R.string.error_update_image), Toast.LENGTH_LONG).show();
                    } else {
                        utilisateur.setImage("http://niameyzze.apkode.net/utilisateurs/" + sendReponse);
                        apiUtilisateur.doUpdate(utilisateur);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                progress.dismiss();
            }
        });


    }

    @Override
    public void responseUpdate(String response) {

        if (response == null) {
            try {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_reseau), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.getMessage();
            }
        } else {
            if (response.equals("0")) {
                try {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_update_image), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.getMessage();
                }
            } else if (response.equals("1")) {
                try {
                    utilisateurLocalStore.clearUtilisateur();
                    utilisateurLocalStore.storeUtilisateur(utilisateur);
                    utilisateurLocalStore.setUtilisateurLogin(true);

                    Glide.with(this)
                            .load(utilisateur.getImage())
                            .crossFade()
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(imvImageProfil);

                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.ok_update_image), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.getMessage();
                }
            }
        }
    }

    @Override
    public void responseInscription(String response) {

    }

    @Override
    public void responseConnexion(String response) {

    }

    @Override
    public void responseGetUtilisateur(Utilisateur utilisateur) {

    }

    @Override
    public void responseDeleteCompte(String response) {

    }
}
