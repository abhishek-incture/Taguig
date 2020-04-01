package com.incture.taguig;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

import id.zelory.compressor.Compressor;

import static com.incture.taguig.MainActivity.IMAGE_FILE_PATH;

public class ReportIncidentActivity extends AppCompatActivity {
    private ImageView imgView;
    private EditText et1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_incident);

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

    public void register(View view) {

        Toast.makeText(this, "Incident Reported Succcessfully", Toast.LENGTH_SHORT).show();
        onBackPressed();
    }

    public void goToMainActivity(View view) {
        onBackPressed();
    }


}
