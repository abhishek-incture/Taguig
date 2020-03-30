package com.incture.taguig;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.incture.taguig.fragment.ChatFragment;
import com.incture.taguig.fragment.HomeFragment;
import com.incture.taguig.fragment.ProfileFragment;
import com.incture.taguig.fragment.RequestsFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    boolean doubleBackToExitPressedOnce = false;
    BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadFragment(new HomeFragment());

        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
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
            }else{
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
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigation);
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
}