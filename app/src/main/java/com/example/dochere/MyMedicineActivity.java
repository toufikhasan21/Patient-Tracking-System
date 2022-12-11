package com.example.dochere;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dochere.adapter.AdapterDoc;
import com.example.dochere.adapter.AdapterMedicine;
import com.example.dochere.databinding.ActivityMainBinding;
import com.example.dochere.databinding.ActivityMyMedicineBinding;
import com.example.dochere.model.ModelMedicine;
import com.example.dochere.network.ApiClient;
import com.example.dochere.network.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MyMedicineActivity extends AppCompatActivity {
    AlertDialog dialog;
    MysharedPreferance mysharedPreferance;
    ActivityMyMedicineBinding binding;
    ApiInterface apiInterface;

    ArrayList<ModelMedicine> medicines = new ArrayList();
    AdapterMedicine adapterMedicine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyMedicineBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle("My Medicine");
        Retrofit instance = ApiClient.instance();
        apiInterface = instance.create(ApiInterface.class);
        mysharedPreferance = MysharedPreferance.getPreferences(this);


        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapterMedicine = new AdapterMedicine(medicines, this);


        medList();

        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                TextView name;
                CheckBox morning, day, night;
                Button add;

                AlertDialog.Builder builder = new AlertDialog.Builder(MyMedicineActivity.this);
                final View mview = LayoutInflater.from(MyMedicineActivity.this).inflate(R.layout.dialog_add_medicine, null);
                builder.setView(mview);
                dialog = builder.create();
                dialog.show();
                /* dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));*/
                name = mview.findViewById(R.id.name);
                morning = mview.findViewById(R.id.morningCheck);
                day = mview.findViewById(R.id.dayCheck);
                night = mview.findViewById(R.id.nightCheck);
                add = mview.findViewById(R.id.add);

                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ProgressDialog dialog2 = ProgressDialog.show(MyMedicineActivity.this, "Loading", "Please wait...", true);
                        ModelMedicine modelMedicine = new ModelMedicine();
                        modelMedicine.setName(name.getText().toString());
                        modelMedicine.setUserId(mysharedPreferance.getUserID());

                        if (morning.isChecked()) {
                            modelMedicine.setMorning("ok");
                        } else {
                            modelMedicine.setMorning("no");
                        }
                        if (day.isChecked()) {
                            modelMedicine.setDay("ok");
                        } else {
                            modelMedicine.setDay("no");
                        }
                        if (night.isChecked()) {
                            modelMedicine.setNight("ok");
                        } else {
                            modelMedicine.setNight("no");
                        }
                        apiInterface.addMedicine(modelMedicine).enqueue(new Callback<ModelMedicine>() {
                            @Override
                            public void onResponse(Call<ModelMedicine> call, Response<ModelMedicine> response) {
                                dialog.dismiss();
                                dialog2.dismiss();
                                Toast.makeText(MyMedicineActivity.this, "Successfully added", Toast.LENGTH_SHORT).show();

                                medList();
                            }

                            @Override
                            public void onFailure(Call<ModelMedicine> call, Throwable t) {
                                dialog2.dismiss();
                                Toast.makeText(MyMedicineActivity.this, "Failed", Toast.LENGTH_SHORT).show();

                            }
                        });


                    }
                });


            }
        });

    }

    void medList() {
        ModelMedicine modelMedicine = new ModelMedicine();
        modelMedicine.setUserId(mysharedPreferance.getUserID());
        apiInterface.myMedicine(modelMedicine).enqueue(new Callback<List<ModelMedicine>>() {
            @Override
            public void onResponse(Call<List<ModelMedicine>> call, Response<List<ModelMedicine>> response) {
                medicines.clear();
                binding.progressBar.setVisibility(View.GONE);
                medicines.addAll(response.body());
                binding.recyclerView.setAdapter(adapterMedicine);
                adapterMedicine.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ModelMedicine>> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(MyMedicineActivity.this, "No medicine found!", Toast.LENGTH_SHORT).show();
            }
        });
    }


}