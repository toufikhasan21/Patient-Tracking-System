package com.example.dochere;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.dochere.databinding.ActivityRatingBinding;
import com.example.dochere.databinding.ActivitySignUpBinding;
import com.example.dochere.network.ApiClient;
import com.example.dochere.network.ApiInterface;

import retrofit2.Retrofit;

public class RatingActivity extends AppCompatActivity {


    ActivityRatingBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRatingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle("Doctor Rating");


        binding.textView6.setText(getIntent().getStringExtra("name"));

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(RatingActivity.this, "Thanks For Feedback", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RatingActivity.this,MainActivity.class));
                finish();
                
            }
        });
      /*  Retrofit instance = ApiClient.instance();
        apiInterface = instance.create(ApiInterface.class);
        mysharedPreferance=MysharedPreferance.getPreferences(this);*/
    }
}