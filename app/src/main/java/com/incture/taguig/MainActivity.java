package com.incture.taguig;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.incture.taguig.fragment.ChatFragment;
import com.incture.taguig.fragment.HomeFragment;
import com.incture.taguig.fragment.ProfileFragment;
import com.incture.taguig.fragment.RequestsFragment;
import com.incture.taguig.utils.PathUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import id.zelory.compressor.Compressor;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission_group.CAMERA;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    private static final int REQUEST_CAPTURE_CAMERA = 100;
    private static final int REQUEST_CAPTURE_GALLERY = 101;
    private static final int REQUEST_CAMERA = 200;
    private static final int PICK_FILE_REQUEST = 401;
    public static final String IMAGE_FILE_PATH = "imagePath";
    public static final String CAPTURE_FROM = "capturefrom";
    int PICK_IMAGE_MULTIPLE = 1;

    String imageFilePath;

    boolean doubleBackToExitPressedOnce = false;
    BottomNavigationView navigation;
    ImageView tv_header_comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadFragment(new HomeFragment());

        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        tv_header_comment = findViewById(R.id.tv_header_comment);
        tv_header_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerForContextMenu(view);
                openContextMenu(view);
            }
        });

        //registerForContextMenu(tv_header_comment);

    }


    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }


    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        int seletedItemId = navigation.getSelectedItemId();
        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            // super.onBackPressed();
            //additional code
            if (R.id.navigation_home != seletedItemId) {
                HomeFragment fragment = new HomeFragment();
                loadFragment(fragment);
                setHomeItem(fragment);
            } else {
                this.doubleBackToExitPressedOnce = true;
                Global.showToast(this, "Please click BACK again to exit");

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 2000);
            }
        } else {
            getSupportFragmentManager().popBackStack();
        }


    }


    public void setHomeItem(Fragment fragment) {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.navigation_home:
                fragment = new HomeFragment();
                break;

            case R.id.navigation_requests:
                fragment = new RequestsFragment();
                break;

            case R.id.navigation_chat:
                fragment = new ChatFragment();
                break;

            case R.id.navigation_profile:
                fragment = new ProfileFragment();
                break;
        }

        return loadFragment(fragment);
    }

    public void reportIncident(View view) {
        registerForContextMenu(view);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Attach image from");
        menu.add(0, v.getId(), 0, "Camera");
        menu.add(0, v.getId(), 0, "Gallery");
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle().toString().equals("Camera")) {

            if (checkCameraPermission()) {
                openCameraIntent();
            } else {
                requestCameraPermission();
            }

        } else if (item.getTitle().toString().equals("Gallery")) {

            /*Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(pickPhoto, 1);*/

            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, PICK_IMAGE_MULTIPLE);

        }
        return true;
    }

    public boolean checkCameraPermission() {
        // Boolean b= ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA) == PackageManager.PERMISSION_GRANTED;


        //return (ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA) == PackageManager.PERMISSION_GRANTED);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);

        }
        return true;
    }

    public void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);
    }

    public void requestReadStoragePermission() {
        ActivityCompat.requestPermissions(this, new String[]{READ_EXTERNAL_STORAGE}, REQUEST_CAPTURE_GALLERY);
    }

    public boolean checkReadStoragePermission() {
        return (ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }


    @RequiresPermission(Manifest.permission.CAMERA)
    private void openCameraIntent() {
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", photoFile);
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        photoURI);
                startActivityForResult(pictureIntent,
                        REQUEST_CAPTURE_CAMERA);
            }
        }
    }

    private void openGalleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), REQUEST_CAPTURE_GALLERY);
    }

    private File createImageFile() throws IOException {
        String imageFileName = "IMG_" + UUID.randomUUID().toString() + "_";
        File storageDir =
                getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".png",         /* suffix */
                storageDir      /* directory */
        );

        imageFilePath = image.getAbsolutePath();
        return image;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (checkCameraPermission()) {
                        openCameraIntent();
                    }
                }

                break;
            case REQUEST_CAPTURE_GALLERY:
                if (grantResults.length > 0) {
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (storageAccepted) {
                        openGalleryIntent();
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                                new AlertDialog.Builder(this).setMessage("Please allow the app to get the image")
                                        .setPositiveButton("YES",
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        requestReadStoragePermission();
                                                    }
                                                });
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_MULTIPLE) {
            Uri imageUri = data.getData();
            String selectedImagePath = getPath(imageUri);
           // File f = new File(selectedImagePath);
          //  Bitmap bmp = Compressor.getDefault(this).compressToBitmap(f);
            Intent intent = new Intent(this, ReportIncidentActivity.class);
            intent.putExtra(IMAGE_FILE_PATH, selectedImagePath);
            intent.putExtra(CAPTURE_FROM, "Gallery");
            startActivity(intent);
           // imgvPhoto.setImageBitmap(bmp);
            return;
            }

                if (requestCode == REQUEST_CAPTURE_CAMERA && resultCode == Activity.RESULT_OK) {
                    String attachment = imageFilePath;

                    Intent intent = new Intent(this, ReportIncidentActivity.class);
                    intent.putExtra(IMAGE_FILE_PATH, attachment);
                    intent.putExtra(CAPTURE_FROM, "Camera");
                    startActivity(intent);
                    //  attachmentPaths.add(attachment);
                    // adapter.notifyDataSetChanged();
                } else if (requestCode == REQUEST_CAPTURE_GALLERY && resultCode == Activity.RESULT_OK) {
                    if (data != null && data.getData() != null) {
                        Uri uri = data.getData();
                        String attachment = PathUtils.getRealPath(this, uri);
                        // attachmentPaths.add(attachment);
                        // adapter.notifyDataSetChanged();
                    } else {
                        // showMessage("Failed to get the image");
                    }
                }





    }


    public String getPath(Uri uri) {
        // just some safety built in
        if (uri == null) {
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = {MediaStore.Images.Media.DATA};
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null,
                null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        // this is our fallback here
        return uri.getPath();
    }

}