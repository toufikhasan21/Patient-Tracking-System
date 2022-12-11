package com.example.dochere;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.dochere.adapter.AdapterAppoitment;
import com.example.dochere.adapter.AdapterDocAppointment;
import com.example.dochere.databinding.ActivityDoctorHomeBinding;
import com.example.dochere.databinding.ActivityLoginBinding;
import com.example.dochere.model.ModelAppoitment;
import com.example.dochere.network.ApiClient;
import com.example.dochere.network.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DoctorHomeActivity extends AppCompatActivity {

    ActivityDoctorHomeBinding binding;
    ApiInterface apiInterface;
    AdapterDocAppointment adapterAppoitment;
    MysharedPreferance mysharedPreferance;
    ArrayList<ModelAppoitment> appoitments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoctorHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle("Patient Appointment");
        Retrofit instance = ApiClient.instance();
        apiInterface = instance.create(ApiInterface.class);
        mysharedPreferance = MysharedPreferance.getPreferences(this);

        binding.docName.setText("Hello " + mysharedPreferance.getDocName());

        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(DoctorHomeActivity.this, "Logging out", Toast.LENGTH_LONG).show();
                mysharedPreferance.setSession("none");
                mysharedPreferance.setDocName("none");
                mysharedPreferance.setDocID("none");
                startActivity(new Intent(DoctorHomeActivity.this, AppMoodActivity.class));
                finish();

            }
        });


        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapterAppoitment = new AdapterDocAppointment(appoitments, DoctorHomeActivity.this);

        ModelAppoitment modelAppoitment = new ModelAppoitment();
        modelAppoitment.setDocId(mysharedPreferance.getDocID());
        apiInterface.getdocAppointment(modelAppoitment).enqueue(new Callback<List<ModelAppoitment>>() {
            @Override
            public void onResponse(Call<List<ModelAppoitment>> call, Response<List<ModelAppoitment>> response) {
                binding.progressBar.setVisibility(View.GONE);
                appoitments.addAll(response.body());
                binding.recyclerView.setAdapter(adapterAppoitment);
                adapterAppoitment.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ModelAppoitment>> call, Throwable t) {
                Toast.makeText(DoctorHomeActivity.this, "No appointment yet", Toast.LENGTH_SHORT).show();
            }
        });

    }
}