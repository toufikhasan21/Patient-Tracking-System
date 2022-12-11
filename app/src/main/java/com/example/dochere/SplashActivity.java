package com.example.dochere;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    MysharedPreferance mysharedPreferance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mysharedPreferance = MysharedPreferance.getPreferences(getApplicationContext());
        int secondsDelayed = 3;
        new Handler().postDelayed(new Runnable() {
            public void run() {

                if (mysharedPreferance.getlogin_type().equals("patient")){
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }else if(mysharedPreferance.getlogin_type().equals("doc")) {
                    if (!mysharedPreferance.getSession().equals("none")){
                        startActivity(new Intent(SplashActivity.this, DoctorHomeActivity.class));
                    }else {
                        startActivity(new Intent(SplashActivity.this, DoctorLoginActivity.class));
                    }

                    finish();
                }else{
                    startActivity(new Intent(SplashActivity.this, AppMoodActivity.class));
                    finish();
                }

            }
        }, secondsDelayed * 1000);
    }
}