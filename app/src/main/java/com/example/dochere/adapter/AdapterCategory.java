package com.example.dochere.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dochere.CategorizedDoctoorActivity;
import com.example.dochere.R;
import com.example.dochere.model.ModelCategory;

import java.util.ArrayList;

public class AdapterCategory extends RecyclerView.Adapter<AdapterCategory.Holder> {

   ArrayList<ModelCategory> categories;
   Context context;

    public AdapterCategory(ArrayList<ModelCategory> categories, Context context) {
        this.categories = categories;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterCategory.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCategory.Holder holder, @SuppressLint("RecyclerView") int position) {

      holder.imageView.setImageResource(categories.get(position).getImage());
      holder.category.setText(categories.get(position).getCategory());

      holder.imageView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {

              Intent intent=new Intent(context, CategorizedDoctoorActivity.class);
              intent.putExtra("category",categories.get(position).getCategory());
              context.startActivity(intent);

          }
      });

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }



    public class Holder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView category;
        public Holder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageView);
            category=itemView.findViewById(R.id.category_name);

        }
    }
}
