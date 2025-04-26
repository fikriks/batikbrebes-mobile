package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

public class ResultActivity extends AppCompatActivity {

    private static final String TAG = "ResultActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Inisialisasi view
        TextView tvKodeAsli = findViewById(R.id.tvKodeAsli);
        TextView tvMotif = findViewById(R.id.tvMotif);
        TextView tvTanggalProduksi = findViewById(R.id.tvTanggalProduksi);
        TextView tvNamaPembuat = findViewById(R.id.tvNamaPembuat);
        TextView tvDeskripsi = findViewById(R.id.tvDeskripsi);
        TextView tvHarga = findViewById(R.id.tvHarga);
        Button btnBack = findViewById(R.id.btnBack);

        // Mendapatkan data dari intent
        String jsonData = getIntent().getStringExtra("PRODUCT_DATA");
        Log.d(TAG, "JSON data: " + jsonData);

        if (jsonData == null || jsonData.isEmpty()) {
            showErrorAndExit("Data produk tidak tersedia");
            return;
        }

        try {
            Gson gson = new Gson();
            ProdukResponse produkResponse = gson.fromJson(jsonData, ProdukResponse.class);

            if (produkResponse.getStatus() == 200 && produkResponse.getData() != null) {
                ProdukResponse.Produk produk = produkResponse.getData();

                // Mengisi TextView dengan data produk
                tvKodeAsli.setText("Kode Produk: " + produk.getKode_asli());
                tvMotif.setText("Motif: " + produk.getMotif());
                tvTanggalProduksi.setText("Tanggal Produksi: " + produk.getTanggal_produksi());
                tvNamaPembuat.setText("Pembuat: " + produk.getNama_pembuat());
                tvDeskripsi.setText("Deskripsi: " + produk.getDeskripsi());
                tvHarga.setText("Harga: Rp " + formatHarga(produk.getHarga()));
            } else {
                showErrorAndExit(produkResponse.getMessages().getError());
            }
        } catch (Exception e) {
            Log.e(TAG, "Parsing error: ", e);
            showErrorAndExit("Gagal memproses data produk");
        }

        // Button kembali
        btnBack.setOnClickListener(v -> finish());
    }

    // Format harga dengan pemisah ribuan
    private String formatHarga(String harga) {
        try {
            double amount = Double.parseDouble(harga);
            return String.format("%,.0f", amount).replace(",", ".");
        } catch (NumberFormatException e) {
            Log.e(TAG, "Format harga error: ", e);
            return harga;
        }
    }

    // Menampilkan error dan keluar dari activity
    private void showErrorAndExit(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        Log.e(TAG, message);
        finish();
    }
}
