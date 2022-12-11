package com.example.dochere.fragment;

import android.app.ProgressDialog;
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
import com.example.dochere.databinding.FragmentSearchBinding;
import com.example.dochere.model.ModelDoc;
import com.example.dochere.network.ApiClient;
import com.example.dochere.network.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class SearchFragment extends Fragment {
    ArrayList<ModelDoc> docs=new ArrayList<>();
    FragmentSearchBinding binding;

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

        binding = FragmentSearchBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        Retrofit instance = ApiClient.instance();
        apiInterface = instance.create(ApiInterface.class);


        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        adapterDoc = new AdapterDoc(docs,getContext());
        apiInterface.getDoctors().enqueue(new Callback<List<ModelDoc>>() {
            @Override
            public void onResponse(Call<List<ModelDoc>> call, Response<List<ModelDoc>> response) {
                docs.clear();
                docs.addAll(response.body());
                binding.recyclerView.setAdapter(adapterDoc);
                adapterDoc.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ModelDoc>> call, Throwable t) {
                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();

            }
        });


        binding.searchBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ModelDoc modelDoc=new ModelDoc();
                modelDoc.setName(binding.editText.getText().toString());
                ProgressDialog dialog1 = ProgressDialog.show(getContext(), "Searching...", "Please wait...", true);
                apiInterface.searchDoc(modelDoc).enqueue(new Callback<List<ModelDoc>>() {
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
                        Toast.makeText(getContext(), "Doctor not found", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });



        return  view;
    }
}