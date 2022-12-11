package com.example.dochere;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.dochere.databinding.ActivitySignUpBinding;
import com.example.dochere.model.ModelUser;
import com.example.dochere.network.ApiClient;
import com.example.dochere.network.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignUpActivity extends AppCompatActivity {

    MysharedPreferance mysharedPreferance;
    ApiInterface apiInterface;
    ActivitySignUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle("Sign Up");
        Retrofit instance = ApiClient.instance();
        apiInterface = instance.create(ApiInterface.class);
        mysharedPreferance=MysharedPreferance.getPreferences(this);

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
            }
        });


        binding.create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.name.getText().toString().isEmpty()){
                    binding.name.setError("required");
                }else if(binding.phone.getText().toString().isEmpty()){
                    binding.name.setError("required");
                }else if(binding.email.getText().toString().isEmpty()){
                    binding.name.setError("required");
                }else if(binding.password.getText().toString().isEmpty()){
                    binding.name.setError("required");
                }else if(!binding.male.isChecked() && !binding.female.isChecked()){
                    Toast.makeText(SignUpActivity.this, "select gender", Toast.LENGTH_SHORT).show();
                }else {
                    ProgressDialog dialog = ProgressDialog.show(SignUpActivity.this, "Loading", "Please wait...", true);
                    ModelUser modelUser=new ModelUser();
                    modelUser.setName(binding.name.getText().toString());
                    modelUser.setPhone(binding.phone.getText().toString());
                    modelUser.setEmail(binding.email.getText().toString());
                    modelUser.setPassword(binding.password.getText().toString());
                    if (binding.male.isChecked()){
                        modelUser.setGender("male");

                    }else {
                        modelUser.setGender("female");

                    }
                    apiInterface.createAccount(modelUser).enqueue(new Callback<ModelUser>() {
                        @Override
                        public void onResponse(Call<ModelUser> call, Response<ModelUser> response) {
                            dialog.dismiss();
                            Toast.makeText(SignUpActivity.this, "Successfully Signup", Toast.LENGTH_SHORT).show();
                           /* mysharedPreferance.setName(modelUser.getName());
                            mysharedPreferance.setPhone(modelUser.getPhone());
                            mysharedPreferance.setEmail(modelUser.getEmail());
                            mysharedPreferance.setGender(modelUser.getGender());
                            mysharedPreferance.setSession("logged");*/
                            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                            finish();
                        }

                        @Override
                        public void onFailure(Call<ModelUser> call, Throwable t) {
                            dialog.dismiss();
                            Toast.makeText(SignUpActivity.this, "Failed, Try again", Toast.LENGTH_SHORT).show();

                        }
                    });

                }
            }
        });

    }
}