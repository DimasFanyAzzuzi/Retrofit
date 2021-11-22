package com.example.belajarretrofit.API;

import com.example.belajarretrofit.Model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIRequestData {
    @GET("retrieve.php")
    Call<ResponseModel> ardRetrieveData();

    @FormUrlEncoded
    @POST("create.php")
    Call<ResponseModel> ardCreateData(
            @Field("nis") String nis,
            @Field("nama") String nama,
            @Field("kelas") String kelas,
            @Field("keterangan") String keterangan
    );

    @FormUrlEncoded
    @POST("delete.php")
    Call<ResponseModel> ardDeleteData (
            @Field("nis") String nis
    );

    @FormUrlEncoded
    @POST("get_data.php")
    Call<ResponseModel> ardgetData (
            @Field("nis") String nis
    );

    @FormUrlEncoded
    @POST("update.php")
    Call<ResponseModel> ardUpdateData(
            @Field("nis") String nis,
            @Field("nama") String nama,
            @Field("kelas") String kelas,
            @Field("keterangan") String keterangan
    );
}
