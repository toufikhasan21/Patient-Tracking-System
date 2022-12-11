package com.example.dochere;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.dochere.databinding.ActivityDoctorDatilesBinding;

public class DoctorDatilesActivity extends AppCompatActivity {

    ActivityDoctorDatilesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoctorDatilesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle("About Doctor");

        if (getIntent().getStringExtra("gender").equals("male")){
            binding.avater.setImageResource(R.drawable.doctor);
        }else {
            binding.avater.setImageResource(R.drawable.doctor_frmale);
        }
        binding.name.setText(getIntent().getStringExtra("name"));
        binding.category.setText(getIntent().getStringExtra("category"));
        binding.price.setText(getIntent().getStringExtra("visit"));
        binding.time.setText(getIntent().getStringExtra("time"));
        binding.rating.setText(getIntent().getStringExtra("rating"));
        binding.degree.setText(getIntent().getStringExtra("degree"));
        getIntent().getStringExtra("id");

        binding.apointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DoctorDatilesActivity.this, AppoinmentActivity.class);
                intent.putExtra("name",getIntent().getStringExtra("name"));
                intent.putExtra("category",getIntent().getStringExtra("category"));
                intent.putExtra("visit",getIntent().getStringExtra("visit"));
                intent.putExtra("rating",getIntent().getStringExtra("rating"));
                intent.putExtra("id",getIntent().getStringExtra("id"));
                intent.putExtra("gender",getIntent().getStringExtra("gender"));
                startActivity(intent);
            }
        });

    }
}