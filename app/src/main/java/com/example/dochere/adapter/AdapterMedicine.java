package com.example.dochere.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dochere.R;
import com.example.dochere.model.ModelAppoitment;
import com.example.dochere.model.ModelMedicine;
import com.example.dochere.network.ApiClient;
import com.example.dochere.network.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AdapterMedicine extends RecyclerView.Adapter<AdapterMedicine.Holder> {


    ApiInterface apiInterface;
    ArrayList<ModelMedicine>medicines;
    Context context;

    public AdapterMedicine(ArrayList<ModelMedicine> medicines, Context context) {
        this.medicines = medicines;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterMedicine.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_medicine, parent, false);
        return new AdapterMedicine.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMedicine.Holder holder, @SuppressLint("RecyclerView") int position) {

        holder.name.setText(medicines.get(position).getMed_name());
        if (medicines.get(position).getMorning().equals("ok")){
            holder.morning.setChecked(true);
        }
        if (medicines.get(position).getDay().equals("ok")){
            holder.day.setChecked(true);
        }
        if (medicines.get(position).getNight().equals("ok")){
            holder.night.setChecked(true);
        }

        Retrofit instance = ApiClient.instance();
        apiInterface = instance.create(ApiInterface.class);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                new AlertDialog.Builder(context)
                        .setTitle(" Delete Medicine ")
                        .setMessage("Are you sure to delete ?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                ProgressDialog dialog1 = ProgressDialog.show(context, "Deleting Medicine", "Please wait...", true);

                                ModelMedicine modelMedicine=new ModelMedicine();
                                modelMedicine.setId(medicines.get(position).getId());
                                apiInterface.deleteMedicine(modelMedicine).enqueue(new Callback<ModelMedicine>() {
                                    @Override
                                    public void onResponse(Call<ModelMedicine> call, Response<ModelMedicine> response) {
                                        dialog1.dismiss();
                                        dialog.dismiss();
                                        medicines.remove(position);
                                        notifyDataSetChanged();
                                        Toast.makeText(context, "Successfully Deleted", Toast.LENGTH_SHORT).show();

                                    }

                                    @Override
                                    public void onFailure(Call<ModelMedicine> call, Throwable t) {
                                        dialog1.dismiss();
                                        dialog.dismiss();
                                        Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show();
                                    }
                                });


                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();


                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return medicines.size();
    }


    public class Holder extends RecyclerView.ViewHolder {

        TextView name;
        CheckBox morning,day,night;

        public Holder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.medName);
            morning=itemView.findViewById(R.id.morning);
            day=itemView.findViewById(R.id.day);
            night=itemView.findViewById(R.id.night);
        }
    }
}
