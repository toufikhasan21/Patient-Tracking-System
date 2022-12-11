package com.example.dochere;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Toast;

import com.example.dochere.databinding.ActivityLoginBinding;
import com.example.dochere.databinding.ActivityMainBinding;
import com.example.dochere.model.ModelUser;
import com.example.dochere.network.ApiClient;
import com.example.dochere.network.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    ApiInterface apiInterface;
    ActivityLoginBinding binding;
    MysharedPreferance sharedPreferance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle("Patient Login");
        Retrofit instance = ApiClient.instance();
        apiInterface = instance.create(ApiInterface.class);
        sharedPreferance = MysharedPreferance.getPreferences(this);


        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.email.getText().toString().isEmpty()) {
                    binding.email.setError("required");
                } else if (binding.password.getText().toString().isEmpty()) {
                    binding.password.setError("required");
                } else {
                    ProgressDialog dialog = ProgressDialog.show(LoginActivity.this, "Loading", "Please wait...", true);
                    ModelUser modelUser = new ModelUser();
                    modelUser.setEmail(binding.email.getText().toString());
                    modelUser.setPassword(binding.password.getText().toString());

                    apiInterface.login(modelUser).enqueue(new Callback<ModelUser>() {
                        @Override
                        public void onResponse(Call<ModelUser> call, Response<ModelUser> response) {

                            if (response.body().getResponse().equals("ok")) {
                                dialog.dismiss();
                                sharedPreferance.setSession("logged");
                                sharedPreferance.setName(response.body().getName());
                                sharedPreferance.setPhone(response.body().getPhone());
                                sharedPreferance.setEmail(response.body().getEmail());
                                sharedPreferance.setUserID(response.body().getId());
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            } else {
                                dialog.dismiss();
                                Toast.makeText(LoginActivity.this, "Invalid Email or Password ! Try again", Toast.LENGTH_SHORT).show();
                            }


                        }

                        @Override
                        public void onFailure(Call<ModelUser> call, Throwable t) {
                            dialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Login failed ! Try again", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


        binding.createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
    }
}