package com.example.dochere;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.example.dochere.adapter.AdapterDoc;
import com.example.dochere.databinding.ActivityCategorizedDoctoorBinding;
import com.example.dochere.databinding.ActivityEditProfileBinding;
import com.example.dochere.model.ModelDoc;
import com.example.dochere.network.ApiClient;
import com.example.dochere.network.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CategorizedDoctoorActivity extends AppCompatActivity {

    ActivityCategorizedDoctoorBinding binding;
    ApiInterface apiInterface;
    ArrayList<ModelDoc> docs=new ArrayList<>();
    AdapterDoc adapterDoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategorizedDoctoorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle(getIntent().getStringExtra("category"));

        Retrofit instance = ApiClient.instance();
        apiInterface = instance.create(ApiInterface.class);


        binding.recyclerView.setLayoutManager(new LinearLayoutManager(CategorizedDoctoorActivity.this,LinearLayoutManager.VERTICAL,false));
        adapterDoc = new AdapterDoc(docs,CategorizedDoctoorActivity.this);

        ModelDoc modelDoc = new ModelDoc();
        modelDoc.setCategory(getIntent().getStringExtra("category"));
        ProgressDialog dialog1 = ProgressDialog.show(CategorizedDoctoorActivity.this, "Searching...", "Please wait...", true);
        apiInterface.categorized_doc(modelDoc).enqueue(new Callback<List<ModelDoc>>() {
            @Override
            public void onResponse(Call<List<ModelDoc>> call, Response<List<ModelDoc>> response) {
                dialog1.dismiss();
                docs.clear();
                docs.addAll(response.body());
                binding.recyclerView.setAdapter(adapterDoc);
                adapterDoc.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ModelDoc>> call, Throwable t) {
                dialog1.dismiss();
                Toast.makeText(CategorizedDoctoorActivity.this, "Doctor not found", Toast.LENGTH_SHORT).show();
            }
        });

    }
}