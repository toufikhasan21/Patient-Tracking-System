package com.example.dochere.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dochere.R;
import com.example.dochere.SignUpActivity;
import com.example.dochere.model.ModelAppoitment;
import com.example.dochere.network.ApiClient;
import com.example.dochere.network.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AdapterDocAppointment extends RecyclerView.Adapter<AdapterDocAppointment.Holder> {

    ArrayList<ModelAppoitment> appoitments;
    Context context;
    ApiInterface apiInterface;

    public AdapterDocAppointment(ArrayList<ModelAppoitment> appoitments, Context context) {
        this.appoitments = appoitments;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterDocAppointment.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_appointment, parent, false);
        return new AdapterDocAppointment.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDocAppointment.Holder holder, @SuppressLint("RecyclerView") int position) {
        Retrofit instance = ApiClient.instance();
        apiInterface = instance.create(ApiInterface.class);

        holder.name.setText(appoitments.get(position).getName());
        holder.docname.setText(appoitments.get(position).getDocName());
        holder.age.setText("Age: "+appoitments.get(position).getAge());
        holder.blood.setText("Blood: "+appoitments.get(position).getBlood());
        holder.weight.setText("Weight: "+appoitments.get(position).getWeight());
        holder.status.setText(appoitments.get(position).getStatus());
        holder.comment.setText("Complain : "+appoitments.get(position).getComment());
        holder.date.setText("Date : "+appoitments.get(position).getDate());

        holder.rating.setVisibility(View.GONE);
        if (appoitments.get(position).getStatus().equals("approved")){
            holder.imageView.setImageResource(R.drawable.ic_ok);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(!appoitments.get(position).getStatus().equals("approved")){

                    new AlertDialog.Builder(context)
                            .setTitle(" Update Appointment ")
                            .setMessage("Are you approved this appointment?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    ProgressDialog dialog1 = ProgressDialog.show(context, "Operation Processing", "Please wait...", true);

                                    ModelAppoitment modelAppoitment=new ModelAppoitment();
                                    modelAppoitment.setId(appoitments.get(position).getId());
                                    modelAppoitment.setStatus("approved");
                                    apiInterface.update_status(modelAppoitment).enqueue(new Callback<ModelAppoitment>() {
                                        @Override
                                        public void onResponse(Call<ModelAppoitment> call, Response<ModelAppoitment> response) {
                                            dialog.dismiss();
                                            dialog1.dismiss();
                                            holder.imageView.setImageResource(R.drawable.ic_ok);
                                            holder.status.setText("approved");
                                        }

                                        @Override
                                        public void onFailure(Call<ModelAppoitment> call, Throwable t) {
                                            dialog1.dismiss();
                                        }
                                    });

                                }
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return appoitments.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView name,age,weight,blood,status,docname,comment,date,rating;
        ImageView imageView;

        public Holder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.name);
            imageView=itemView.findViewById(R.id.status_img);
            age=itemView.findViewById(R.id.age);
            weight=itemView.findViewById(R.id.weight);
            blood=itemView.findViewById(R.id.blood);
            docname=itemView.findViewById(R.id.doctor);
            date=itemView.findViewById(R.id.date);
            status=itemView.findViewById(R.id.status);
            comment=itemView.findViewById(R.id.complain);
            rating=itemView.findViewById(R.id.rating);
    }
}}
