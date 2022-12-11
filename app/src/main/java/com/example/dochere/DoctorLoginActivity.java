package com.example.dochere;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.dochere.databinding.ActivityDoctorDatilesBinding;
import com.example.dochere.databinding.ActivityDoctorLoginBinding;
import com.example.dochere.model.ModelDoc;
import com.example.dochere.network.ApiClient;
import com.example.dochere.network.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DoctorLoginActivity extends AppCompatActivity {

    ApiInterface apiInterface;
    MysharedPreferance mysharedPreferance;
    ActivityDoctorLoginBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoctorLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle("Doctor Login");

        Retrofit instance = ApiClient.instance();
        apiInterface = instance.create(ApiInterface.class);
        mysharedPreferance = MysharedPreferance.getPreferences(this);


        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!binding.email.getText().toString().isEmpty()|| !binding.password.getText().toString().isEmpty()){
                    ProgressDialog dialog = ProgressDialog.show(DoctorLoginActivity.this, "Authenticating...", "Please wait...", true);
                    ModelDoc modelDoc=new ModelDoc();
                    modelDoc.setEmail(binding.email.getText().toString());
                    modelDoc.setPassword(binding.password.getText().toString());

                    apiInterface.docLogin(modelDoc).enqueue(new Callback<ModelDoc>() {
                        @Override
                        public void onResponse(Call<ModelDoc> call, Response<ModelDoc> response) {
                            if (response.body().getResponse().equals("ok")) {
                                dialog.dismiss();
                                mysharedPreferance.setSession("logged");
                                mysharedPreferance.setDocID(response.body().getId());
                                mysharedPreferance.setDocName(response.body().getName());
                                startActivity(new Intent(DoctorLoginActivity.this, DoctorHomeActivity.class));
                                finish();
                            } else {
                                dialog.dismiss();
                                Toast.makeText(DoctorLoginActivity.this, "Invalid Email or Password ! Try again", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<ModelDoc> call, Throwable t) {
                            Toast.makeText(DoctorLoginActivity.this, "Invalid Email or Password ! Try again", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });

    }
}