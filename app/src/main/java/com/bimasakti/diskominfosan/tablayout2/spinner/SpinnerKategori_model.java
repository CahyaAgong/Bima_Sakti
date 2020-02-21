package com.bimasakti.diskominfosan.tablayout2.spinner;

import com.google.gson.annotations.SerializedName;

public class SpinnerKategori_model {

    @SerializedName("id_kategori") private int id_kategori;
    @SerializedName("nama_kategori") private String nama_kategori;
    @SerializedName("status") private String status;

    public int getId_kategori() {
        return id_kategori;
    }

    public String getNama_kategori() {
        return nama_kategori;
    }

    public String getStatus() {
        return status;
    }
}
