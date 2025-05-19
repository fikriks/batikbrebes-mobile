package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Sesuaikan dengan nama layout XML Anda

        Button scanButton = findViewById(R.id.scanButton);
        ImageButton usageButton = findViewById(R.id.buttonPetunjuk);
        ImageButton developerButton = findViewById(R.id.buttonDeveloper);
        scanButton.setOnClickListener(v -> {
            // Pindah ke activity scanner
            Intent intent = new Intent(MainActivity.this, ScannerActivity.class);
            startActivity(intent);
            finish();
        });

        usageButton.setOnClickListener(v -> {
            // Pindah ke activity usage
            Intent intent = new Intent(MainActivity.this, UsageActivity.class);
            startActivity(intent);
            finish();
        });

        developerButton.setOnClickListener(v -> {
            // Pindah ke activity author
            Intent intent = new Intent(MainActivity.this, AuthorActivity.class);
            startActivity(intent);
            finish();
        });
    }
}