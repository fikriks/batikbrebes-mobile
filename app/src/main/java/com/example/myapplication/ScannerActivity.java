package com.example.myapplication;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.google.gson.Gson;

public class ScannerActivity extends AppCompatActivity {

    private static final int CAMERA_PERMISSION_REQUEST = 1;
    private CaptureManager capture;
    private DecoratedBarcodeView barcodeView;
    private boolean torchOn = false;
    private boolean isScanned = false;
    private ProgressDialog progressDialog;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        // Inisialisasi API Service
        apiService = ApiClient.getClient().create(ApiService.class);

        barcodeView = findViewById(R.id.camera_preview);
        Button btnClose = findViewById(R.id.btn_close);
        ImageButton btnTorch = findViewById(R.id.btn_torch);

        // Setup Progress Dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Memproses...");
        progressDialog.setCancelable(false);

        barcodeView.decodeSingle(new BarcodeCallback() {
            @Override
            public void barcodeResult(BarcodeResult result) {
                if (result.getText() != null && !isScanned) {
                    isScanned = true;
                    String qrContent = result.getText();

                    // Panggil API dengan QR code yang di-scan
                    fetchProductData(qrContent);
                }
            }
        });

        btnClose.setOnClickListener(v -> finish());

        btnTorch.setOnClickListener(v -> {
            torchOn = !torchOn;
            barcodeView.getBarcodeView().setTorch(torchOn);
            btnTorch.setImageResource(torchOn ? R.drawable.ic_flash_on : R.drawable.ic_flash_off);
        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST);
        } else {
            startScanning();
        }
    }

    private void fetchProductData(String qrCode) {
        progressDialog.show();

        Call<ProdukResponse> call = apiService.getProductByQr(qrCode);
        call.enqueue(new Callback<ProdukResponse>() {
            @Override
            public void onResponse(Call<ProdukResponse> call, Response<ProdukResponse> response) {
                progressDialog.dismiss();

                if (response.isSuccessful() && response.body() != null) {
                    ProdukResponse produkResponse = response.body();

                    if (response.code() == 200 && produkResponse.getStatus() == 200) {
                        // Konversi response ke JSON
                        Gson gson = new Gson();
                        String jsonData = gson.toJson(produkResponse);

                        // Pindah ke ResultActivity dengan membawa data produk
                        Intent intent = new Intent(ScannerActivity.this, ResultActivity.class);
                        intent.putExtra("PRODUCT_DATA", jsonData);
                        startActivity(intent);
                        finish(); // Tutup ScannerActivity jika diperlukan
                    } else {
                        String errorMsg = produkResponse.getMessages() != null ?
                                produkResponse.getMessages().getError() : "Terjadi kesalahan";
                        Toast.makeText(ScannerActivity.this, errorMsg, Toast.LENGTH_LONG).show();
                        isScanned = false; // Reset langsung agar bisa scan lagi
                    }
                } else {
                    if (response.code() == 400) {
                        Toast.makeText(ScannerActivity.this, "Kode QR tidak valid", Toast.LENGTH_LONG).show();
                    } else if (response.code() == 404) {
                        Toast.makeText(ScannerActivity.this, "Produk tidak ditemukan", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(ScannerActivity.this, "Gagal memproses data", Toast.LENGTH_LONG).show();
                    }
                    isScanned = false; // Reset langsung agar bisa scan lagi
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ProdukResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ScannerActivity.this, "Koneksi gagal: " + t.getMessage(), Toast.LENGTH_LONG).show();
                isScanned = false; // Reset langsung agar bisa scan lagi
            }
        });
    }

    private void startScanning() {
        capture = new CaptureManager(this, barcodeView);
        capture.initializeFromIntent(getIntent(), null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startScanning();
            } else {
                Toast.makeText(this, "Izin kamera diperlukan untuk scan QR", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (capture != null) {
            capture.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (capture != null) {
            capture.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (capture != null) {
            capture.onDestroy();
        }
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}