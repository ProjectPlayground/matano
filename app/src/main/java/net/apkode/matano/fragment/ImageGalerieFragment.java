package net.apkode.matano.fragment;

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
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import net.apkode.matano.R;
import net.apkode.matano.adapter.ImageGalerieAdapter;
import net.apkode.matano.api.APIImageGalerie;
import net.apkode.matano.helper.ApiRestClient;
import net.apkode.matano.helper.UtilisateurLocalStore;
import net.apkode.matano.interfaces.IImageGalerie;
import net.apkode.matano.model.Evenement;
import net.apkode.matano.model.ImageGalerie;
import net.apkode.matano.model.Utilisateur;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class ImageGalerieFragment extends Fragment implements IImageGalerie {
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int GALERIE_CAPTURE_IMAGE_REQUEST_CODE = 200;
    private static boolean isImageGaleriePassed = false;
    private static Context context;
    private final int REQUEST_IMAGE_CAPTURE = 1;
    private APIImageGalerie apiImageGalerie;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private UtilisateurLocalStore utilisateurLocalStore;
    private LinearLayout linearLayoutBtn;
    private List<ImageGalerie> imageGaleriesListe = new ArrayList<>();
    private Evenement evenement;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressDialog progress;
    private Utilisateur utilisateur;
    private ImageGalerieAdapter imageGalerieAdapter;
    private String imageLien;

    public ImageGalerieFragment() {
    }

    public static ImageGalerieFragment newInstance(Context ctx) {
        isImageGaleriePassed = true;
        context = ctx;
        ImageGalerieFragment imageGalerieFragment = new ImageGalerieFragment();
        return imageGalerieFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        apiImageGalerie = new APIImageGalerie(this, context);
        utilisateurLocalStore = new UtilisateurLocalStore(context);
        utilisateur = utilisateurLocalStore.getUtilisateur();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addOnItemTouchListener(new ImageGalerieAdapter.RecyclerTouchListener(getActivity(), recyclerView, new ImageGalerieAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("ImageGalerie", (Serializable) imageGaleriesListe);
                bundle1.putInt("position", position);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                ImageGalerieSlideshow imageGalerieSlideshow = ImageGalerieSlideshow.newInstance();
                imageGalerieSlideshow.setArguments(bundle1);
                imageGalerieSlideshow.show(fragmentTransaction, "slideshow");
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        imageGalerieAdapter = new ImageGalerieAdapter(imageGaleriesListe);
        recyclerView.setAdapter(imageGalerieAdapter);

        progressBar = (ProgressBar) view.findViewById(R.id.loading);

        progress = new ProgressDialog(getContext());
        progress.setCancelable(true);
        progress.setMessage("Envoie en cours ...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setProgress(0);
        progress.setMax(100);

        linearLayoutBtn = (LinearLayout) view.findViewById(R.id.btn);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        refreshLayout();
                    }
                }
        );

        Bundle bundle = getArguments();
        if (bundle != null) {
            evenement = (Evenement) bundle.getSerializable("Evenement");

            if (evenement != null) {
                if (isImageGaleriePassed) {
                    apiImageGalerie.getData(evenement);
                    isImageGaleriePassed = false;
                } else {
                    if (imageGaleriesListe.size() == 0) {
                        new APIImageGalerie(this, context).getData(evenement);
                    } else {
                        progressBar.setVisibility(View.GONE);
                        linearLayoutBtn.setVisibility(View.VISIBLE);
                    }
                }

            }
        }

        Button btnSendPhoto = (Button) view.findViewById(R.id.btnSendAndSharePhoto);
        btnSendPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setMessage("Partagez votre photo");

                alertDialogBuilder.setPositiveButton(getString(R.string.share_photo), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(getTempFile(getContext())));
                        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                    }
                });

                alertDialogBuilder.setNegativeButton(getString(R.string.pick_photo), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, GALERIE_CAPTURE_IMAGE_REQUEST_CODE);
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public void getResponse(final List<ImageGalerie> imageGaleries) {
        if (imageGaleries == null) {
            new APIImageGalerie(this, context).getData(evenement);
        } else {
            imageGaleriesListe = imageGaleries;
            try {
                progressBar.setVisibility(View.GONE);
                linearLayoutBtn.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setRefreshing(false);
                recyclerView.setAdapter(new ImageGalerieAdapter(imageGaleriesListe));
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }


    private File getTempFile(Context context) {
        //it will return /sdcard/image.tmp
        final File path = new File(Environment.getExternalStorageDirectory(), context.getPackageName());
        if (!path.exists()) {
            path.mkdir();
        }
        return new File(path, "image.tmp");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE && resultCode == getActivity().RESULT_OK) {
            final File file = getTempFile(getContext());
            try {
                Bitmap captureBmp = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), Uri.fromFile(file));
                Uri tempUri = getCaptureUri(getContext(), captureBmp);
                sendDataToServeur(tempUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if (requestCode == GALERIE_CAPTURE_IMAGE_REQUEST_CODE && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {
            sendDataToServeur(data.getData());
        }
    }

    public Uri getCaptureUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Matano", null);
        return Uri.parse(path);
    }


    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(getContext(), contentUri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
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
            e.getMessage();
        }


        ApiRestClient.post("upload-images-galeries.php", params, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                progress.show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                progress.dismiss();
                Toast.makeText(getContext(), getString(R.string.error_reseau), Toast.LENGTH_LONG).show();
            }


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String sendReponse = response.get(0).toString();

                    if (sendReponse.equals("0")) {
                        progress.dismiss();
                        Toast.makeText(getContext(), getString(R.string.error_update_image), Toast.LENGTH_LONG).show();
                    } else {
                        imageLien = "http://niameyzze.apkode.net/image-galeries/" + sendReponse;
                        utilisateur.setImage(imageLien);
                        apiImageGalerie.sendImageGalerie(imageLien, evenement, utilisateur);
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
    public void responseSendImageGalerie(String response) {
        try {
            progress.dismiss();
        } catch (Exception e) {
            e.getMessage();
        }

        if (response == null) {
            try {
                Toast.makeText(getContext(), getResources().getString(R.string.error_reseau), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.getMessage();
            }
        } else {
            if (response.equals("0")) {
                try {
                    Toast.makeText(getContext(), getResources().getString(R.string.error_update_image), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.getMessage();
                }
            } else if (response.equals("1")) {
                try {
                    imageGaleriesListe.add(new ImageGalerie(99, utilisateur.getNom(), utilisateur.getPrenom(), utilisateur.getTelephone(), utilisateur.getImage(), imageLien));
                    imageGalerieAdapter.notifyItemInserted(imageGaleriesListe.size() - 1);
                    recyclerView.scrollToPosition(imageGaleriesListe.size() - 1);

                    Toast.makeText(getContext(), getResources().getString(R.string.ok_update_imagegalerie), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.getMessage();
                }
            }
        }
    }

    private void refreshLayout() {
        new APIImageGalerie(this, context).getData(evenement);
    }

}
