package com.incture.taguig;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.incture.taguig.models.HospitalModel;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class BookingDetailsActivity extends AppCompatActivity {

    private ImageView imgView;
    private static final int REQUEST_CAPTURE_GALLERY = 101;
    private TextView  tvName,tvSlot,tvAge,tvGender,tvHospitalName,tvHospitalAddress;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);
        imgView = findViewById(R.id.imgView);
        tvName = findViewById(R.id.tvName);
        tvSlot = findViewById(R.id.tvSlot);
        tvAge = findViewById(R.id.tvAge);
        tvGender = findViewById(R.id.tvGender);
        tvHospitalName = findViewById(R.id.tvHospitalName);
        tvHospitalAddress = findViewById(R.id.tvHospitalAddress);


        Intent intent = getIntent();
        String name = intent.getStringExtra(BookAppointmentActivity.PATIENT_NAME);
        tvName.setText(name);
        String slotTime= intent.getStringExtra(BookAppointmentActivity.APPOINTMENT_DAY)+", "+
                intent.getStringExtra(BookAppointmentActivity.PATIENT_SLOTTIME);
        tvSlot.setText(slotTime);
        String age= intent.getStringExtra(BookAppointmentActivity.PATIENT_AGE)+" years";
        tvAge.setText(age);
        tvGender.setText(intent.getStringExtra(BookAppointmentActivity.PATIENT_GENDER));
        HospitalModel h1= (HospitalModel) intent.getSerializableExtra(BookAppointmentActivity.HOSPITAL);
        tvHospitalName.setText(h1.getHospitalName());
        tvHospitalAddress.setText(h1.getHospitalDetail());

        generateQR( name);
    }

    private void generateQR(String name) {

        String text=name;
        if(text!=null  && !text.isEmpty())
        {
            try {
                MultiFormatWriter multiFormatWriter=new MultiFormatWriter();
                BitMatrix bitMatrix=multiFormatWriter.encode(text, BarcodeFormat.QR_CODE,200,200);
                BarcodeEncoder barcodeEncoder=new BarcodeEncoder();
                Bitmap bitmap=barcodeEncoder.createBitmap(bitMatrix);
                imgView.setImageBitmap(bitmap);

            } catch (WriterException e) {
                e.printStackTrace();
            }
        }


}

    public void saveQRCode(View view) {


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CAPTURE_GALLERY);

        }
        else {

            addQRImage();
        }

    }





    public void goToMainActivity(View view) {

        onBackPressed();
    }

    public void addQRImage(){
        BitmapDrawable drawable = (BitmapDrawable) imgView.getDrawable();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getBitmap());
        FileOutputStream fos = null;

        File sdcard = Environment.getExternalStorageDirectory();
        File directory = new File(sdcard.getAbsolutePath() + "/yourFileName");
        directory.mkdir();
        String filename = String.format("%d.jpg", System.currentTimeMillis());
        File outFile = new File(directory, filename);

        try {
            fos = new FileOutputStream(outFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            intent.setData(Uri.fromFile(outFile));
            sendBroadcast(intent);
            Toast.makeText(this, "QR Code Image Saved", Toast.LENGTH_SHORT).show();
            Intent intent1= new Intent(this,MainActivity.class);
            startActivity(intent1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case REQUEST_CAPTURE_GALLERY:
                if (grantResults.length > 0) {
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (storageAccepted) {
                        addQRImage();
                       // openGalleryIntent();
                    } else {
                        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                                new AlertDialog.Builder(this).setMessage("Please allow the app to get the image")
                                        .setPositiveButton("YES",
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                      //  requestReadStoragePermission();
                                                    }
                                                });
                                return;
                            }
                        }*/
                    }
                }
                break;
        }
    }

    public void onback(View view) {
        onBackPressed();
    }
}
