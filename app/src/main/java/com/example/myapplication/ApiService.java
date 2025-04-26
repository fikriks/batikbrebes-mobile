package com.example.myapplication;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {
    @FormUrlEncoded
    @POST("scan") // Ganti dengan endpoint yang sesuai
    Call<ProdukResponse> getProductByQr(
            @Field("kode_qr") String qrCode
    );
}
