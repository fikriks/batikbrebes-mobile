# Batik Brebes

Aplikasi Android untuk melakukan scan QR code pada produk Batik Brebes dan menampilkan detail produk secara langsung.

## Fitur Utama

- Scan QR code produk batik menggunakan kamera.
- Mendapatkan detail produk batik (kode, motif, tanggal produksi, pembuat, deskripsi, harga) dari server.
- Tampilan hasil scan yang informatif.
- Dukungan flashlight saat scan di kondisi gelap.

## Struktur Proyek

- `app/src/main/java/com/example/myapplication/ScannerActivity.java`  
  Aktivitas utama untuk scan QR code.
- `app/src/main/java/com/example/myapplication/ResultActivity.java`  
  Menampilkan detail produk setelah scan berhasil.
- `app/src/main/res/drawable/`  
  Berisi aset gambar dan ikon aplikasi.
- `app/src/main/res/values/`  
  Berisi resource seperti warna dan string aplikasi.

## Cara Build & Jalankan

1. Buka proyek ini di Android Studio.
2. Pastikan koneksi internet aktif untuk mengakses API produk.
3. Jalankan aplikasi pada perangkat atau emulator Android.
4. Arahkan kamera ke QR code produk batik untuk mulai scan.

## Konfigurasi

- Pastikan permission kamera sudah diberikan pada aplikasi.
- Endpoint API dapat dikonfigurasi pada bagian `ApiClient` dan `ApiService`.

## Kontribusi

Kontribusi sangat terbuka! Silakan fork repository ini dan buat pull request untuk perbaikan atau penambahan fitur.

## Lisensi

Proyek ini menggunakan lisensi Apache 2.0. Lihat file `LICENSE` untuk detail lebih lanjut.