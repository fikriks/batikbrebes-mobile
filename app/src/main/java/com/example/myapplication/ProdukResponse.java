package com.example.myapplication;

public class ProdukResponse {
    private int status;
    private Produk data;
    private Messages messages;

    public int getStatus() {
        return status;
    }

    public Produk getData() {
        return data;
    }

    public Messages getMessages() {
        return messages;
    }

    public static class Produk {
        private String kode_produk;
        private String kode_asli;
        private String motif;
        private String tanggal_produksi;
        private String nama_pembuat;
        private String deskripsi;
        private String harga;

        private String foto_produk_path;

        public String getKode_produk() { return kode_produk; }
        public String getKode_asli() { return kode_asli; }
        public String getMotif() { return motif; }
        public String getTanggal_produksi() { return tanggal_produksi; }
        public String getNama_pembuat() { return nama_pembuat; }
        public String getDeskripsi() { return deskripsi; }
        public String getHarga() { return harga; }

        public String getFoto_produk_path() {
            return foto_produk_path;
        }
    }

    public static class Messages {
        private String error;

        public String getError() {
            return error;
        }
    }
}
