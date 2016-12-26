package matano.apkode.net.matano.config;

/**
 * Created by smalllamartin on 12/9/16.
 */

public class fake {

    /*
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
 */

    /*
    <LinearLayout
        android:id="@+id/Lfiltre"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal">

        <Button
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_marginRight="1dp"
            android:layout_marginTop="1dp"
            android:layout_weight="1"
            android:background="@color/colorPrimary"
            android:onClick="showFragmentBtnCategorie"
            android:text="Categorie"
            android:textColor="@android:color/background_light"
            android:textSize="13sp" />

        <Button
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_marginRight="1dp"
            android:layout_marginTop="1dp"
            android:layout_weight="1"
            android:background="@color/colorPrimary"
            android:onClick="showFragmentBtnPays"
            android:text="Pays"
            android:textColor="@android:color/background_light"
            android:textSize="13sp" />

        <Button
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_marginTop="1dp"
            android:layout_weight="1"
            android:background="@color/colorPrimary"
            android:onClick="showFragmentBtnVille"
            android:text="Ville"
            android:textColor="@android:color/background_light"
            android:textSize="13sp" />
    </LinearLayout>
     */

    /*
       // database = FirebaseDatabase.getInstance();
        mRootRef = FirebaseDatabase.getInstance().getReference().getRoot();

        refMessage = mRootRef.child("message");

        refMessage.setValue("Hello, Dosso!");

        // Read from the database
        refMessage.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
     */


    /*
    // adapter = getFilter().filter(newText);
     */
}
