package com.bimasakti.diskominfosan.wisataku.model;

import com.google.gson.annotations.SerializedName;

public class Wisata_model {

    @SerializedName("id_wisata") private int id_wisata;
    @SerializedName("nama_wisata") private String nama_wisata;
    @SerializedName("alamat_wisata") private String alamat_wisata;
    @SerializedName("foto") private String foto;
    @SerializedName("location") private String location;

    public int getId_wisata() {
        return id_wisata;
    }

    public String getNama_wisata() {
        return nama_wisata;
    }

    public String getAlamat_wisata() {
        return alamat_wisata;
    }

    public String getFoto() {
        return foto;
    }

    public String getLocation() {
        return location;
    }
}
