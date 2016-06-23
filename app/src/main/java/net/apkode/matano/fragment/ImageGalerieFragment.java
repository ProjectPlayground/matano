package net.apkode.matano.fragment;

import android.content.Context;
import android.content.CursorLoader;
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

import net.apkode.matano.R;
import net.apkode.matano.adapter.ImageGalerieAdapter;
import net.apkode.matano.api.APIImageGalerie;
import net.apkode.matano.helper.UtilisateurLocalStore;
import net.apkode.matano.interfaces.IImageGalerie;
import net.apkode.matano.model.Evenement;
import net.apkode.matano.model.ImageGalerie;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ImageGalerieFragment extends Fragment implements IImageGalerie {
    private static boolean isImageGaleriePassed = false;
    private final int REQUEST_IMAGE_CAPTURE = 1;
    private APIImageGalerie apiImageGalerie;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private UtilisateurLocalStore utilisateurLocalStore;
    private LinearLayout linearLayoutBtn;
    private List<ImageGalerie> imageGaleriesListe = new ArrayList<>();
    private Evenement evenement;

    public ImageGalerieFragment() {
    }

    public static ImageGalerieFragment newInstance() {
        isImageGaleriePassed = true;

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


        recyclerView.setAdapter(new ImageGalerieAdapter(imageGaleriesListe));

        progressBar = (ProgressBar) view.findViewById(R.id.loading);

        linearLayoutBtn = (LinearLayout) view.findViewById(R.id.btn);

        Bundle bundle = getArguments();
        if (bundle != null) {
            evenement = (Evenement) bundle.getSerializable("Evenement");

            if (evenement != null) {
                if (isImageGaleriePassed) {
                    apiImageGalerie.getData(evenement);
                    isImageGaleriePassed = false;
                } else {
                    if (imageGaleriesListe.size() == 0) {
                        apiImageGalerie.getData(evenement);
                    } else {
                        progressBar.setVisibility(View.GONE);
                        linearLayoutBtn.setVisibility(View.VISIBLE);
                    }
                }

            }
        }

        Button btnSendPhoto = (Button) view.findViewById(R.id.btnSendPhoto);
        btnSendPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });
    }

    @Override
    public void getResponse(final List<ImageGalerie> imageGaleries) {
        if (imageGaleries == null) {
            apiImageGalerie.getData(evenement);
        } else {
            if (imageGaleries.size() == 0) {
                try {
                    Toast.makeText(getActivity().getApplicationContext(), getString(R.string.error_reseau), Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                    linearLayoutBtn.setVisibility(View.VISIBLE);
                }catch (Exception e){
                    e.getMessage();
                }
            } else {
                try {
                    progressBar.setVisibility(View.GONE);
                    linearLayoutBtn.setVisibility(View.VISIBLE);
                    imageGaleriesListe = imageGaleries;
                    recyclerView.setAdapter(new ImageGalerieAdapter(imageGaleriesListe));
                }catch (Exception e){
                    e.getMessage();
                }
            }
        }

    }

    @Override
    public void sendResponse(String response) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == 1) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            final File file = getTempFile(getContext());
            try {
                Bitmap captureBmp = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), Uri.fromFile(file));
                Uri tempUri = getCaptureUri(getActivity().getApplicationContext(), captureBmp);
                sendDataToServeur(tempUri);
            } catch (IOException e) {
                e.printStackTrace();
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

    private void sendDataToServeur(Uri uri) {
        String path = getRealPathFromURI(uri);
        File image = new File(path);

        String telephone = utilisateurLocalStore.getUtilisateur().getTelephone();
        apiImageGalerie.sendImage(image, telephone);
    }

}
