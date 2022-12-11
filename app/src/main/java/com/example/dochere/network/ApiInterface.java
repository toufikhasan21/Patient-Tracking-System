package com.example.dochere.network;

import com.example.dochere.model.ModelAppoitment;
import com.example.dochere.model.ModelDoc;
import com.example.dochere.model.ModelMedicine;
import com.example.dochere.model.ModelUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("createAccount.php")
    Call<ModelUser> createAccount(@Body ModelUser modelUser);

    @POST("update_status.php")
    Call<ModelAppoitment> update_status(@Body ModelAppoitment modelAppoitment);

    @POST("insertAppointment.php")
    Call<ModelAppoitment> insertAppointment(@Body ModelAppoitment modelUser);

    @POST("login.php")
    Call<ModelUser> login(@Body ModelUser modelUser);

    @POST("docLogin.php")
    Call<ModelDoc> docLogin(@Body ModelDoc modelUser);

    @GET("docList.php")
    Call<List<ModelDoc>> getDoctors();

    @POST("myAppointment.php")
    Call<List<ModelAppoitment>> getmyAppointment(@Body ModelAppoitment modelAppoitment);

    @POST("docAppoinment.php")
    Call<List<ModelAppoitment>> getdocAppointment(@Body ModelAppoitment modelAppoitment);


    @POST("myMedicine.php")
    Call<List<ModelMedicine>> myMedicine(@Body ModelMedicine modelMedicine);

    @POST("search_doctor.php")
    Call<List<ModelDoc>> searchDoc(@Body ModelDoc modelDoc);

    @POST("categorized_doc.php")
    Call<List<ModelDoc>> categorized_doc(@Body ModelDoc modelDoc);
    
    @POST("addMedicine.php")
    Call<ModelMedicine> addMedicine(@Body ModelMedicine modelMedicine);

    @POST("delete_madicine.php")
    Call<ModelMedicine> deleteMedicine(@Body ModelMedicine modelMedicine);
}
