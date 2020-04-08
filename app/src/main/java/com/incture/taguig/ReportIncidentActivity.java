package com.incture.taguig;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import id.zelory.compressor.Compressor;

import static com.incture.taguig.MainActivity.IMAGE_FILE_PATH;

public class ReportIncidentActivity extends AppCompatActivity {

    private AppCompatAutoCompleteTextView input_search;
    PlacesClient placesClient;
    String apiKey="AIzaSyCA38Gb8gGAcLw2h7PcWYWe28l6Pqr24i0";
    private ImageView imgView;
    private EditText et1;
    private Button editLocation;
    private TextView tvLocation;
    AutocompleteSupportFragment autocompleteFragment;
    int AUTOCOMPLETE_REQUEST_CODE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_incident);

        //input_search = findViewById(R.id.input_search);
        final String apiKey="AIzaSyAFygveTAd69YP_eZi8jUdoV0eY1QlKWYw";
        Places.initialize(getApplicationContext(), apiKey);
        placesClient = Places.createClient(this);
        init();

        imgView = findViewById(R.id.imgView);
        et1 = findViewById(R.id.et1);
        editLocation = findViewById(R.id.editLocation);
        tvLocation = findViewById(R.id.tvLocation);
        editLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int AUTOCOMPLETE_REQUEST_CODE = 1;
// Set the fields to specify which types of place data to
// return after the user has made a selection.
                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME);

// Start the autocomplete intent.
                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.FULLSCREEN, fields)
                        .build(ReportIncidentActivity.this);
                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
            }
        });

        Intent intent = getIntent();
        if(intent.getStringExtra(MainActivity.CAPTURE_FROM).equals("Camera")) {
            String filename = intent.getStringExtra(IMAGE_FILE_PATH);
            Glide.with(this)
                    .load(new File(filename))
                    .into(imgView);
        }
        else if(intent.getStringExtra(MainActivity.CAPTURE_FROM).equals("WithoutPhoto")) {


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
         autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

// Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

// Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i("place", "Place: " + place.getName() + ", " + place.getId()+place.getAddress());
                input_search.setText(place.getName());
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                tvLocation.setText(place.getName());
                // Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                //  Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }


}
