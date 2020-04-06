package com.incture.taguig;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

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
import com.incture.taguig.adapter.SliderAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class Splash2Activity extends AppCompatActivity implements View.OnClickListener {

    ViewPager viewPager;

    SliderAdapter sliderAdapter;
    LinearLayout l1;

    int mCurrentPage;

    private static final int RC_SIGN_IN = 102 ;

    Button login_Button;
    private LoginButton loginButton;
    private CardView btnGoogle;
    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private GoogleSignInClient mGoogleSignInClient;
    private boolean isLoggedIn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);

        viewPager = findViewById(R.id.viewPager);
        l1=findViewById(R.id.l1);

        sliderAdapter = new SliderAdapter(this);
        viewPager.setAdapter(sliderAdapter);
        addDotIndicator(0);
        viewPager.addOnPageChangeListener(listener);


        login_Button = findViewById(R.id.loginButton);


        login_Button.setOnClickListener(this);
        btnGoogle = findViewById(R.id.btnGoogle);
        isLoggedIn = false;
        loginButton = findViewById(R.id.login_button);


        LoginManager.getInstance().logOut();

        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions(Arrays.asList("email", "public_profile"));
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if(currentAccessToken == null)
                {
                   // username.setText("");

                    // Toast.makeText(LoginActivity.this, "User is Logged out", Toast.LENGTH_SHORT).show();
                }
                else{
                    loadUserProfile(currentAccessToken);
                }
            }
        };

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


    public void goToCreateAccount(View view) {

        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginButton:
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void addDotIndicator(int position) {
        //  mdots = new TextView[3];
        l1.removeAllViews();

        for(int i=0;i<3;i++){

            if(i==position){
                View v = new View(this);
                LinearLayout.LayoutParams lay= new LinearLayout.LayoutParams(
                        40,
                        20
                );
                lay.leftMargin=6;
                v.setLayoutParams(lay);
                v.setBackgroundResource(R.drawable.bg_button2dp);
                l1.addView(v);

            }
            else
            {
                View v = new View(this);
                LinearLayout.LayoutParams lay= new LinearLayout.LayoutParams(
                        20,
                        20
                );
                lay.leftMargin=6;
                v.setLayoutParams(lay);

                v.setBackgroundResource(R.drawable.bg_button2dp);

                l1.addView(v);


            }

/*
            mdots[i]= new TextView(this);


            // mdots[i].setText(Html.fromHtml("&#8226;"));
            mdots[i].setTextSize(35);
            mdots[i].setTextColor(getResources().getColor(R.color.transparentwhite));*/



        }

    }


    ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotIndicator(position);

            mCurrentPage = position;

            if(position == 0){
                /*NextButton.setEnabled(true);
                prevButton.setEnabled(false);
                prevButton.setVisibility(View.INVISIBLE);
                NextButton.setText("Next");
                prevButton.setText("");*/

            }

            else if(position == 3-1){
                /*NextButton.setEnabled(true);
                prevButton.setEnabled(true);
                prevButton.setVisibility(View.VISIBLE);
                NextButton.setText("finish");
                prevButton.setText("back");*/
            }

            else{
                /*NextButton.setEnabled(true);
                prevButton.setEnabled(true);
                prevButton.setVisibility(View.VISIBLE);
                NextButton.setText("Next");
                prevButton.setText("back");*/
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


    public static int getPixelValue(Context context, int dimenId) {
        Resources resources = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dimenId,
                resources.getDisplayMetrics()
        );
    }


    private void updateUI(GoogleSignInAccount acct) {
        if(acct!=null) {
            // String personName = acct.getDisplayName();
            String firstName = acct.getGivenName();
            String lastName = acct.getFamilyName();
            // String email = acct.getEmail();

            // etFirstName.setText(firstName);
            // etLastName.setText(lastName);
            // username.setText(email);
            Intent intent = new Intent(Splash2Activity.this,MainActivity.class);
            startActivity(intent);
            Toast.makeText(Splash2Activity.this, "You logged in as "+firstName+" "+lastName, Toast.LENGTH_SHORT).show();

            finish();

            isLoggedIn = true;
            //  String personId = acct.getId();
            //  Uri personPhoto = acct.getPhotoUrl();
        }
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


    public void loadUserProfile(AccessToken newAccessToken){
        GraphRequest request = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String first_name = object.getString("first_name");
                    String last_name = object.getString("last_name");
                    //String email = object.getString("email");

                    // etFirstName.setText(first_name);
                    //etLastName.setText(last_name);
                    // username.setText(email);
                    Intent intent = new Intent(Splash2Activity.this,MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(Splash2Activity.this, "You logged in as "+first_name+" "+last_name, Toast.LENGTH_SHORT).show();
                    finish();

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
                        //username.setText("");
                        isLoggedIn = false;
                    }
                });
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

}
