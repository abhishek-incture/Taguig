package com.incture.taguig;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final int RC_SIGN_IN = 102 ;
    private EditText etFirstName,etLastName,etEmail,etPhoneNo;
    private CheckBox checkBox1;
    private LoginButton loginButton;
    private CardView btnGoogle;
    private Button fb;
    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    List< String > permissionNeeds;
    private int mYear, mMonth, mDay, mHour, mMinute;
    //private String[] months;
    private Spinner spinner1,spinner2,spinner3;
    private List<Integer> days,years;
    private List<String> months;
    ArrayAdapter<String> monthsAdapter;
    ArrayAdapter<Integer> daysAdapter,yearAdapter;
    private GoogleSignInClient mGoogleSignInClient;
    private boolean isLoggedIn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etPhoneNo = findViewById(R.id.etPhoneNo);
        btnGoogle = findViewById(R.id.btnGoogle);
        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
        spinner3 = findViewById(R.id.spinner3);
        checkBox1 =findViewById(R.id.checkbox1);
        isLoggedIn = false;

       // months = this.getResources().getStringArray(R.array.months) ;
        addSpinner1();
        addSpinner2();
        addSpinner3();



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

        callbackManager = CallbackManager.Factory.create();
        //loginButton.setReadPermissions(Arrays.asList("email", "public_profile","mobileNo"));
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

    }




    private void updateUI(GoogleSignInAccount acct) {
        if(acct!=null) {
           // String personName = acct.getDisplayName();
            String firstName = acct.getGivenName();
            String lastName = acct.getFamilyName();
            String email = acct.getEmail();

            etFirstName.setText(firstName);
            etLastName.setText(lastName);
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


    @Override
    protected void onActivityResult(int requestCode, int responseCode,
                                    Intent data) {

        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
        callbackManager.onActivityResult(requestCode, responseCode, data);

        super.onActivityResult(requestCode, responseCode, data);
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
                    String last_name = object.getString("last_name");
                    String email = object.getString("email");

                    etFirstName.setText(first_name);
                    etLastName.setText(last_name);
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
        } else if (etLastName.getText().toString().isEmpty()) {
            showToast(" LastName is mandatory");
        }  else if (etEmail.getText().toString().isEmpty())
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
        else if(!checkBox1.isChecked()){
            showToast(" Please Accept Terms and Condition");

        }

        else {
            Intent intent = new Intent(this,LoginActivity.class);
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
                        etLastName.setText("");
                        etEmail.setText("");
                        isLoggedIn = false;
                    }
                });
    }
}


    /*@Override
    public void onClick(View v) {
        if (v == fb) {
            //loginButton.setReadPermissions(permissionNeeds);
            loginButton.performClick();
        }
    }
*/

