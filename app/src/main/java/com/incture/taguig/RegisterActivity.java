package com.incture.taguig;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class RegisterActivity extends AppCompatActivity  {

    private EditText etFirstName,etLastName,etEmail,etPhoneNo;
    private LoginButton loginButton;
    private Button fb;
    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    List< String > permissionNeeds;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private String[] months;
    private TextView spinner1,spinner2,spinner3;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etPhoneNo = findViewById(R.id.etPhoneNo);
        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
        spinner3 = findViewById(R.id.spinner3);

        months = this.getResources().getStringArray(R.array.months) ;


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

    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode,
                                    Intent data) {
        callbackManager.onActivityResult(requestCode, responseCode, data);

        super.onActivityResult(requestCode, responseCode, data);
    }

    public void facebookLogin(View view) {
        //loginButton.setReadPermissions(permissionNeeds);
        loginButton.performClick();

    }


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
    }


    /*@Override
    public void onClick(View v) {
        if (v == fb) {
            //loginButton.setReadPermissions(permissionNeeds);
            loginButton.performClick();
        }
    }
*/

