package com.example.dochere.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dochere.MysharedPreferance;
import com.example.dochere.R;
import com.example.dochere.adapter.AdapterAppoitment;
import com.example.dochere.adapter.AdapterDoc;
import com.example.dochere.databinding.FragmentAppointmentBinding;
import com.example.dochere.databinding.FragmentHomeBinding;
import com.example.dochere.model.ModelAppoitment;
import com.example.dochere.network.ApiClient;
import com.example.dochere.network.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class AppointmentFragment extends Fragment {


    ApiInterface apiInterface;
    AdapterAppoitment adapterAppoitment;
    ArrayList<ModelAppoitment>appoitments=new ArrayList<>();
    FragmentAppointmentBinding binding;
    MysharedPreferance mysharedPreferance;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAppointmentBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        Retrofit instance = ApiClient.instance();
        apiInterface = instance.create(ApiInterface.class);
        mysharedPreferance= MysharedPreferance.getPreferences(getContext());

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        adapterAppoitment = new AdapterAppoitment(appoitments,getContext());

        ModelAppoitment modelAppoitment=new ModelAppoitment();
        modelAppoitment.setUserID(mysharedPreferance.getUserID());
        
        apiInterface.getmyAppointment(modelAppoitment).enqueue(new Callback<List<ModelAppoitment>>() {
            @Override
            public void onResponse(Call<List<ModelAppoitment>> call, Response<List<ModelAppoitment>> response) {
                appoitments.clear();
                binding.progressBar2.setVisibility(View.GONE);
                binding.textView19.setVisibility(View.GONE);
                appoitments.addAll(response.body());
                binding.recyclerView.setAdapter(adapterAppoitment);
                adapterAppoitment.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ModelAppoitment>> call, Throwable t) {
                binding.progressBar2.setVisibility(View.GONE);
                binding.textView19.setVisibility(View.VISIBLE);
            }
        });

        return  view;
    }
}