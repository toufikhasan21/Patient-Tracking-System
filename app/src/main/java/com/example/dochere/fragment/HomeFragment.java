package com.example.dochere.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dochere.R;
import com.example.dochere.adapter.AdapterCategory;
import com.example.dochere.adapter.AdapterDoc;
import com.example.dochere.databinding.FragmentHomeBinding;
import com.example.dochere.model.ModelCategory;
import com.example.dochere.model.ModelDoc;
import com.example.dochere.network.ApiClient;
import com.example.dochere.network.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class HomeFragment extends Fragment {

    ArrayList<ModelCategory> categories=new ArrayList<>();
    ArrayList<ModelDoc> docs=new ArrayList<>();
    FragmentHomeBinding binding;

    ApiInterface apiInterface;
    AdapterCategory adapter;
    AdapterDoc adapterDoc;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        Retrofit instance = ApiClient.instance();
        apiInterface = instance.create(ApiInterface.class);

        categories.clear();
        categories.add(new ModelCategory(R.drawable.img6,"Cardiologist"));
        categories.add(new ModelCategory(R.drawable.image7,"Orthopaedic"));
        categories.add(new ModelCategory(R.drawable.img8,"Dentist"));
        categories.add(new ModelCategory(R.drawable.surgery_room,"Tumor & Cancer surgery"));
        categories.add(new ModelCategory(R.drawable.medicine,"Medicine"));

        adapter = new AdapterCategory(categories, getContext());
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.recyclerView.setLayoutManager(horizontalLayoutManagaer);
        binding.recyclerView.setAdapter(adapter);



        binding.docRecycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        adapterDoc = new AdapterDoc(docs,getContext());
        apiInterface.getDoctors().enqueue(new Callback<List<ModelDoc>>() {
            @Override
            public void onResponse(Call<List<ModelDoc>> call, Response<List<ModelDoc>> response) {

                docs.clear();
                binding.progressBar.setVisibility(View.GONE);
                docs.addAll(response.body());
                binding.docRecycler.setAdapter(adapterDoc);
                adapterDoc.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ModelDoc>> call, Throwable t) {
                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                binding.progressBar.setVisibility(View.GONE);
            }
        });



        return view;
    }
}