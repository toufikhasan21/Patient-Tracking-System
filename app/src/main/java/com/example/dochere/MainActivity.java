package com.example.dochere;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.dochere.databinding.ActivityAppMoodBinding;
import com.example.dochere.databinding.ActivityMainBinding;
import com.example.dochere.fragment.AppointmentFragment;
import com.example.dochere.fragment.HomeFragment;
import com.example.dochere.fragment.ProfileFragment;
import com.example.dochere.fragment.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;
    MysharedPreferance mysharedPreferance;
    ActivityMainBinding binding;
    HomeFragment homeFragment = new HomeFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    AppointmentFragment appointmentFragment = new AppointmentFragment();
    SearchFragment searchFragment = new SearchFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mysharedPreferance = MysharedPreferance.getPreferences(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
        binding.bottomNev.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                        return true;

                    case R.id.appoinment:
                        setTitle("My appointment");
                        if (mysharedPreferance.getSession().equals("none")) {
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        } else {
                            getSupportFragmentManager().beginTransaction().replace(R.id.container, appointmentFragment).commit();
                        }
                        return true;

                    case R.id.search:
                        setTitle("Search Doctor");
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, searchFragment).commit();
                        return true;

                    case R.id.profile:
                        setTitle("Profile");
                        if (mysharedPreferance.getSession().equals("none")) {
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        } else {
                            getSupportFragmentManager().beginTransaction().replace(R.id.container, profileFragment).commit();
                        }
                        return true;
                }

                return false;
            }
        });


    }

    @Override
    public void onBackPressed() {

        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();
            new AlertDialog.Builder(this)
                    .setTitle(" App Exit ")
                    .setMessage("Are you sure you want to exit?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            return;
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
        }

        mBackPressed = System.currentTimeMillis();



    }
}