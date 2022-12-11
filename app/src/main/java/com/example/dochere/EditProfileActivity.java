package com.example.dochere;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.dochere.databinding.ActivityEditProfileBinding;
import com.example.dochere.databinding.ActivityLoginBinding;
import com.example.dochere.databinding.ActivityMainBinding;
import com.example.dochere.network.ApiClient;
import com.example.dochere.network.ApiInterface;

import retrofit2.Retrofit;

public class EditProfileActivity extends AppCompatActivity {

    ApiInterface apiInterface;
    MysharedPreferance sharedPreferance;
    ActivityEditProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle("Update Profile");
        Retrofit instance = ApiClient.instance();
        apiInterface = instance.create(ApiInterface.class);
        sharedPreferance = MysharedPreferance.getPreferences(this);

        binding.name.setText(sharedPreferance.getName());
        binding.email.setText(sharedPreferance.getemail());
        binding.phone.setText(sharedPreferance.getPhone());

        binding.create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog dialog = ProgressDialog.show(EditProfileActivity.this, "Updating Information..", "Please wait...", true);
                sharedPreferance.setName(binding.name.getText().toString());
                sharedPreferance.setEmail(binding.email.getText().toString());
                sharedPreferance.setPhone(binding.phone.getText().toString());
                Toast.makeText(EditProfileActivity.this, "Successfully edited", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                startActivity(new Intent(EditProfileActivity.this,MainActivity.class));
                finish();
            }
        });

    }
}