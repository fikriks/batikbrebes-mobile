package com.example.myapplication;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "https://batikbrebes.web.id/api/";
    private static final String BASE_URL_FOTO_PRODUK = "https://batikbrebes.web.id/";
    private static Retrofit retrofit;

    public static String getBaseUrlFotoProduk() {
        return BASE_URL_FOTO_PRODUK;
    }
    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
