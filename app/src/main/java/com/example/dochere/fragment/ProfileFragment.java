package com.example.dochere.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dochere.AppMoodActivity;
import com.example.dochere.EditProfileActivity;
import com.example.dochere.MainActivity;
import com.example.dochere.MyMedicineActivity;
import com.example.dochere.MysharedPreferance;
import com.example.dochere.R;
import com.example.dochere.SignUpActivity;
import com.example.dochere.databinding.FragmentHomeBinding;
import com.example.dochere.databinding.FragmentProfileBinding;


public class ProfileFragment extends Fragment {


    MysharedPreferance mysharedPreferance;
    FragmentProfileBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        mysharedPreferance=MysharedPreferance.getPreferences(getContext());

        binding.name.setText(mysharedPreferance.getName());
        binding.email.setText(mysharedPreferance.getemail());
        binding.phone.setText(mysharedPreferance.getPhone());


        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Logging out", Toast.LENGTH_LONG).show();
                mysharedPreferance.setSession("none");
                mysharedPreferance.setName("none");
                mysharedPreferance.setPhone("none");
                mysharedPreferance.setEmail("none");
                mysharedPreferance.setlogin_type("none");
                startActivity(new Intent(getContext(), AppMoodActivity.class));

            }
        });


        binding.updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getContext(), EditProfileActivity.class));
            }
        });


        binding.medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getContext(), MyMedicineActivity.class));
            }
        });

        return view;
    }
}