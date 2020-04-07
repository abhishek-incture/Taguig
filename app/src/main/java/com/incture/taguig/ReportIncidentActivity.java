package com.incture.taguig;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import id.zelory.compressor.Compressor;

import static com.incture.taguig.MainActivity.IMAGE_FILE_PATH;

public class ReportIncidentActivity extends AppCompatActivity {

    private AppCompatAutoCompleteTextView input_search;
    PlacesClient placesClient;
    String apiKey="AIzaSyCA38Gb8gGAcLw2h7PcWYWe28l6Pqr24i0";
    private ImageView imgView;
    private EditText et1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_incident);

        input_search = findViewById(R.id.input_search);
        String apiKey="AIzaSyAFygveTAd69YP_eZi8jUdoV0eY1QlKWYw";
        Places.initialize(getApplicationContext(), apiKey);
        placesClient = Places.createClient(this);
        init();

        imgView = findViewById(R.id.imgView);
        et1 = findViewById(R.id.et1);

        Intent intent = getIntent();
        if(intent.getStringExtra(MainActivity.CAPTURE_FROM).equals("Camera")) {
            String filename = intent.getStringExtra(IMAGE_FILE_PATH);
            Glide.with(this)
                    .load(new File(filename))
                    .into(imgView);
        }

        else {
           /* ArrayList<Uri> uri = (ArrayList<Uri>) intent.getSerializableExtra(MainActivity.IMAGE_FILE_PATH);

            imgView.setImageURI(uri.get(0));*/
            String filename = intent.getStringExtra(IMAGE_FILE_PATH);

            File f = new File(filename);
            Bitmap bmp = Compressor.getDefault(this).compressToBitmap(f);
           imgView.setImageBitmap(bmp);
        }


    }

    public void init(){
        Places.initialize(getApplicationContext(), apiKey);

        placesClient = Places.createClient(this);

        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

// Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

// Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i("place", "Place: " + place.getName() + ", " + place.getId()+place.getAddress());
                input_search.setText(place.getName()+place.getAddress());
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("place", "An error occurred: " + status);
            }
        });

    }


    public void register(View view) {

        Toast.makeText(this, "Incident Reported Succcessfully", Toast.LENGTH_SHORT).show();
        onBackPressed();
    }

    public void goToMainActivity(View view) {
        onBackPressed();
    }


}
