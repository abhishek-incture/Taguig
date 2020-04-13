package com.incture.taguig;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.incture.taguig.adapter.FileAdapter;
import com.incture.taguig.utils.PathUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import id.zelory.compressor.Compressor;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static com.incture.taguig.MainActivity.IMAGE_FILE_PATH;

public class Register2Activity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener {
    private static final int REQUEST_CAPTURE_GALLERY = 101;
    int PICK_IMAGE_MULTIPLE = 1;
     boolean backpress=true;
    private static final int RC_SIGN_IN = 102 ;
    private static final int FILE_REQUEST = 101;
    private EditText etFirstName,etLastName,etEmail,etPhoneNo,etPassword;
    private TextView tvfile1,tvfile2,tvfile3;
    private CheckBox checkBox1;
    private LoginButton loginButton;
    private LinearLayout btnGoogle;
    private Button fb;
    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    List< String > permissionNeeds;
    private int mYear, mMonth, mDay, mHour, mMinute;
    //private String[] months;
    private Spinner spinner1,spinner2,spinner3,spinneMarital,spinneEmployee;
    private List<Integer> days,years;
    private List<String> months,maritalStatuses,employmentStatuses;
    ArrayAdapter<String> monthsAdapter,maritalStatusAdapter,employeeStatusAdapter;
    ArrayAdapter<Integer> daysAdapter,yearAdapter;
    private GoogleSignInClient mGoogleSignInClient;
    private boolean isLoggedIn;

    public static int noOfFile= 1;
    private RecyclerView recyclerView;
    private FileAdapter adapter;
    private List<String> fileNameList;
    private TextView tvPersonalInfo,tvAttach,tvMale,tvFemale,tvOther;
    private View linePersonalInfo,lineAttaach;
    private LinearLayout l2Attach,layoutPersonalInfo;
    private ImageView profilePic,imgViewMale,imgViewFemale;
    private  LinearLayout btnMale,btnFemale,btnOther;

    ImageView closeRegistration;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        closeRegistration = (ImageView) findViewById(R.id.closeRegistration);
        tvPersonalInfo = findViewById(R.id.tvPersonalInfo);
        tvAttach = findViewById(R.id.tvAttach);
        linePersonalInfo = findViewById(R.id.linePersonalInfo);
        lineAttaach = findViewById(R.id.lineAttach);
        l2Attach = findViewById(R.id.l2Attach);
        layoutPersonalInfo = findViewById(R.id.layoutPersonalInfo);
        profilePic = findViewById(R.id.profilePic);
        btnMale= findViewById(R.id.btnMale);
        btnFemale= findViewById(R.id.btnFemale);
        btnOther=findViewById(R.id.btnOther);
        imgViewMale= findViewById(R.id.imgViewMale);
        imgViewFemale= findViewById(R.id.imgViewFemale);

        tvMale= findViewById(R.id.tvMale);
        tvFemale= findViewById(R.id.tvFemale);
        tvOther= findViewById(R.id.tvOther);


        btnMale.setOnClickListener(this);
        btnFemale.setOnClickListener(this);
        btnOther.setOnClickListener(this);


        fileNameList = new ArrayList<>();
        recyclerView= findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter= new FileAdapter(this,fileNameList);
        recyclerView.setAdapter(adapter);

        etFirstName = findViewById(R.id.etFirstName);
      //  etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etPhoneNo = findViewById(R.id.etPhoneNo);
        //etPassword = findViewById(R.id.etPassword);
        btnGoogle = findViewById(R.id.btnGoogle);
        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
        spinner3 = findViewById(R.id.spinner3);
        spinneMarital= findViewById(R.id.spinneMarital);

        spinneEmployee= findViewById(R.id.spinneEmployee);
        checkBox1 =findViewById(R.id.checkbox1);
        tvfile1 = findViewById(R.id.tvfile1);
        tvfile2 = findViewById(R.id.tvfile2);
        tvfile3 = findViewById(R.id.tvfile3);

        isLoggedIn = false;

       // months = this.getResources().getStringArray(R.array.months) ;
        addSpinner1();
        addSpinner2();
        addSpinner3();
        addSpinner4();
        addSpinner5();




        //callbackManager = CallbackManager.Factory.create();

      //  fb =  findViewById(R.id.fb);
        //fb.setOnClickListener(this);
        loginButton = findViewById(R.id.login_button);

        /* permissionNeeds = Arrays.asList("user_photos", "email",
                "user_birthday", "public_profile", "AccessToken");

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if(currentAccessToken == null)
                {
                    etFirstName.setText("");
                    etLastName.setText("");
                    etEmail.setText("");
                    Toast.makeText(RegisterActivity.this, "User is Logged out", Toast.LENGTH_SHORT).show();
                }
                else{
                   // loadUserProfile(currentAccessToken);
                }
            }
        };
        loginButton.registerCallback(callbackManager,
                new FacebookCallback < LoginResult > () {@Override
                public void onSuccess(LoginResult loginResult) {

                    System.out.println("onSuccess");

                    String accessToken = loginResult.getAccessToken()
                            .getToken();

                    GraphRequest request = GraphRequest.newMeRequest(
                            loginResult.getAccessToken(),
                            new GraphRequest.GraphJSONObjectCallback() {@Override
                            public void onCompleted(JSONObject object,
                                                    GraphResponse response) {

                                Log.i("LoginActivity",
                                        response.toString());
                                try {
                                    String name = object.getString("name");
                                    String email = object.getString("email");
                                   // String gender = object.getString("gender");
                                   // String birthday = object.getString("user_birthday");
                                    String[] names= name.split(" ");
                                    etFirstName.setText(names[0]);
                                    etLastName.setText(names[1]);
                                    etEmail.setText(email);
                                    //etLastName.setText(gender);
                                  // etPhoneNo.setText(birthday);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                            });

                    Bundle parameters = new Bundle();
                    parameters.putString("fields",
                            "id,name,email");
                    request.setParameters(parameters);
                    request.executeAsync();
                }

                    @Override
                    public void onCancel() {
                        System.out.println("onCancel");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        System.out.println("onError");
                        Log.v("LoginActivity", exception.getCause().toString());
                    }
                });

*/

        LoginManager.getInstance().logOut();
        if(isLoggedIn){
            signOut();
        }

        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions(Arrays.asList("email", "public_profile"));
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if(currentAccessToken == null)
                {
                    etFirstName.setText("");
                    //etLastName.setText("");
                    etEmail.setText("");

                 //   Toast.makeText(RegisterActivity.this, "User is Logged out", Toast.LENGTH_SHORT).show();
                }
                else{
                    loadUserProfile(currentAccessToken);
                }
            }
        };
       // checkLoginStatus();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });



        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        //mGoogleSignInClient.silentSignIn();

        closeRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }




    private void updateUI(GoogleSignInAccount acct) {
        if(acct!=null) {
           // String personName = acct.getDisplayName();
            String firstName = acct.getGivenName();
            String lastName = acct.getFamilyName();
            String email = acct.getEmail();

            etFirstName.setText(firstName+" " +lastName);
           // etLastName.setText(lastName);
            etEmail.setText(email);

            isLoggedIn = true;
          //  String personId = acct.getId();
          //  Uri personPhoto = acct.getPhotoUrl();
        }
    }

    private void addSpinner1() {

        days = new ArrayList<>();
        for(int i=1;i<=31;i++){
            days.add(i);
        }
        daysAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, days);

        daysAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner1.setAdapter(daysAdapter);
    }

    private void addSpinner2() {

        months = new ArrayList<>();
        months= Arrays.asList(new DateFormatSymbols().getMonths());

        monthsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, months);

        monthsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner2.setAdapter(monthsAdapter);
    }

    private void addSpinner3() {

        years = new ArrayList<>();
        for(int i=1975;i<=2020;i++){
            years.add(i);
        }
        yearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, years);

        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner3.setAdapter(yearAdapter);
    }
    private void addSpinner4() {

        maritalStatuses = new ArrayList<>();
        maritalStatuses.add("Select");
        maritalStatuses.add("Single");
        maritalStatuses.add("Married");
        maritalStatuses.add("Divorced");
        maritalStatuses.add("Other");


        maritalStatusAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, maritalStatuses);

        maritalStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinneMarital.setAdapter(maritalStatusAdapter);
    }
    private void addSpinner5() {

        employmentStatuses = new ArrayList<>();
        employmentStatuses.add("Select");
        employmentStatuses.add("Unemployed");
        employmentStatuses.add("Self Employed");
        employmentStatuses.add("Permanent Employee");
        employmentStatuses.add("Retired");
        employmentStatuses.add("Other");




        employeeStatusAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, employmentStatuses);

        employeeStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinneEmployee.setAdapter(employeeStatusAdapter);
    }


    @Override
    protected void onActivityResult(int requestCode, int responseCode,
                                    Intent data) {
        super.onActivityResult(requestCode, responseCode, data);

        if (requestCode == PICK_IMAGE_MULTIPLE && responseCode==RESULT_OK) {
            Uri imageUri = data.getData();
            String selectedImagePath = getPath(imageUri);
            // File f = new File(selectedImagePath);
            //  Bitmap bmp = Compressor.getDefault(this).compressToBitmap(f);

            // imgvPhoto.setImageBitmap(bmp);


            File f = new File(selectedImagePath);
            Bitmap bmp = Compressor.getDefault(this).compressToBitmap(f);
            profilePic.setImageBitmap(bmp);
            return;
        }

        switch (requestCode) {
            case RC_SIGN_IN: {
                // The Task returned from this call is always completed, no need to attach
                // a listener.
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                handleSignInResult(task);
                return;
            }
            case FILE_REQUEST:
                if(responseCode== RESULT_OK){
                    String path = data.getData().getLastPathSegment();
                    String[] paths=path.split("/");
                    path = paths[paths.length-1];

                    /*if(noOfFile==1) {
                        tvfile1.setVisibility(View.VISIBLE);
                        tvfile1.setText("1. "+path);
                    }
                    if(noOfFile==2) {
                        tvfile2.setVisibility(View.VISIBLE);

                        tvfile2.setText("2. "+path);
                    }
                    if(noOfFile==3){
                        tvfile3.setVisibility(View.VISIBLE);

                        tvfile3.setText("3. "+path);

                    }*/
                    fileNameList.add(path);
                    adapter.notifyDataSetChanged();
                    noOfFile++;
                }
        }


        callbackManager.onActivityResult(requestCode, responseCode, data);

    }


    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
           // Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    public void facebookLogin(View view) {
        //loginButton.setReadPermissions(permissionNeeds);
        loginButton.performClick();

    }


/*
    public void showDatePicker(View v){
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        spinner1.setText(""+dayOfMonth);
                           spinner2.setText(months[monthOfYear]);
                           spinner3.setText(""+year);
                       // txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
*/








    public void loadUserProfile(AccessToken newAccessToken){
        GraphRequest request = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String first_name = object.getString("first_name");
                   // etFirstName.setText(first_name);
                    String last_name = object.getString("last_name");
                    etFirstName.setText(first_name+" "+last_name);

                    String email = object.getString("email");


                    etEmail.setText(email);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });

        Bundle parameters= new Bundle();
        parameters.putString("fields","first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();
    }

    public void checkLoginStatus(){
        if(AccessToken.getCurrentAccessToken()!=null){
            loadUserProfile(AccessToken.getCurrentAccessToken());
        }
    }

    public void register(View view) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


        if (etFirstName.getText().toString().isEmpty()) {
            showToast(" FirstName is mandatory");
        }   else if (etEmail.getText().toString().isEmpty())
            showToast(" Email is mandatory");
        else if ((!etEmail.getText().toString().matches(emailPattern))) {
            showToast(" please Enter Valid Email");
        }
        else if (etPhoneNo.getText().toString().isEmpty()) {
            showToast(" phone No is mandatory");
        }
        else if (etPhoneNo.getText().toString().length() < 10) {
            showToast(" Please Enter valid 10 digit number");
        }


        else {
            Intent intent = new Intent(this,Splash2Activity.class);
            startActivity(intent);
        }


        }


    void showToast(String mandatory) {
        Toast.makeText(this, "" + mandatory, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rbMale:
                if (checked)
                    // Pirates are the best
                    break;
            case R.id.rbFeMale:
                if (checked)
                    // Ninjas rule
                    break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        LoginManager.getInstance().logOut();


    }

    public void signIn(View view) {
        if(!isLoggedIn) {
            LoginManager.getInstance().logOut();

            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        }
        else
        {
            signOut();
        }

        //GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
       // updateUI(account);
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                        etFirstName.setText("");
                        //etLastName.setText("");
                        etEmail.setText("");
                        isLoggedIn = false;
                    }
                });
    }


    public void upLoadFiles(View view) {
        if(noOfFile<4) {
            Intent mFileIntent = new Intent(Intent.ACTION_GET_CONTENT);
            mFileIntent.setType("*/*");
            startActivityForResult(mFileIntent, FILE_REQUEST);
        }
        else{
            Toast.makeText(this, "Only 3 files Allowed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        accessTokenTracker.stopTracking();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        accessTokenTracker.startTracking();
    }

    @Override
    public void onBackPressed() {
        noOfFile =1;
        if(!backpress)
        {
            backpress=true;
            RelativeLayout r2=findViewById(R.id.r2);
            r2.performClick();
        }
        else {
            super.onBackPressed();

        }
    }

    public void spinnerClick(View view) {
        spinneEmployee.performClick();
    }

    public void onPersonalInfoClick(View view) {
        tvPersonalInfo.setTextColor(getResources().getColor(R.color.tvPersonalInfoColor));
        tvAttach.setTextColor(getResources().getColor(R.color.tvAttachColor));
        lineAttaach.setVisibility(View.GONE);
        linePersonalInfo.setVisibility(View.VISIBLE);

        layoutPersonalInfo.setVisibility(View.VISIBLE);
        l2Attach.setVisibility(View.GONE);

    }

    public void onAttachInfoClick(View view) {

        tvAttach.setTextColor(getResources().getColor(R.color.tvPersonalInfoColor));
        tvPersonalInfo.setTextColor(getResources().getColor(R.color.tvAttachColor));
        linePersonalInfo.setVisibility(View.GONE);
        lineAttaach.setVisibility(View.VISIBLE);
        l2Attach.setVisibility(View.VISIBLE);
        layoutPersonalInfo.setVisibility(View.GONE);
        backpress=false;
    }

    public void UploadPhoto(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CAPTURE_GALLERY);

        }
        else{
            openGalleryIntent();
        }
    }


    private void openGalleryIntent() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, PICK_IMAGE_MULTIPLE);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

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

    public void requestReadStoragePermission() {
        ActivityCompat.requestPermissions(this, new String[]{READ_EXTERNAL_STORAGE}, REQUEST_CAPTURE_GALLERY);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnMale:
                btnMale.setBackgroundResource(R.drawable.bg_editgreyselected);
                btnFemale.setBackgroundResource(R.drawable.bg_editgrey);
                btnOther.setBackgroundResource(R.drawable.bg_editgrey);
                imgViewMale.setImageResource(R.drawable.maleselected);
                imgViewFemale.setImageResource(R.drawable.female);
                tvMale.setTextColor(getResources().getColor(R.color.tvblue));
                tvFemale.setTextColor(getResources().getColor(R.color.colorBlack));
                tvOther.setTextColor(getResources().getColor(R.color.colorBlack));


                break;

            case R.id.btnFemale:
                btnFemale.setBackgroundResource(R.drawable.bg_editgreyselected);
                btnMale.setBackgroundResource(R.drawable.bg_editgrey);
                btnOther.setBackgroundResource(R.drawable.bg_editgrey);
                imgViewMale.setImageResource(R.drawable.male);
                imgViewFemale.setImageResource(R.drawable.femaleselected);
                tvMale.setTextColor(getResources().getColor(R.color.colorBlack));
                tvFemale.setTextColor(getResources().getColor(R.color.tvblue));
                tvOther.setTextColor(getResources().getColor(R.color.colorBlack));

                break;
                case R.id.btnOther:
                    btnOther.setBackgroundResource(R.drawable.bg_editgreyselected);
                btnFemale.setBackgroundResource(R.drawable.bg_editgrey);
                    btnMale.setBackgroundResource(R.drawable.bg_editgrey);
                    imgViewMale.setImageResource(R.drawable.male);
                    imgViewFemale.setImageResource(R.drawable.female);
                    tvMale.setTextColor(getResources().getColor(R.color.colorBlack));
                    tvFemale.setTextColor(getResources().getColor(R.color.colorBlack));
                    tvOther.setTextColor(getResources().getColor(R.color.tvblue));


                    break;
        }
    }





/*
    public void showPasswordCheckListener(View view) {

            boolean checked = ((CheckBox) view).isChecked();
            switch (view.getId()) {
                case R.id.etPassword:
                    if (!checked)
                       etPassword .setTransformationMethod(new PasswordTransformationMethod());  //hide the password from the edit text
                    else
                        etPassword.setTransformationMethod(null); //show the password from the edit text
                    break;

            }

    }
*/
}


    /*@Override
    public void onClick(View v) {
        if (v == fb) {
            //loginButton.setReadPermissions(permissionNeeds);
            loginButton.performClick();
        }
    }
*/

