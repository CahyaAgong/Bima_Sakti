package com.bimasakti.diskominfosan.kecamatan_dan_keluarahan.Model;

import com.google.gson.annotations.SerializedName;

public class Kelurahan_model {


    @SerializedName("id_kelurahan") private int id_kelurahan;
    @SerializedName("kelurahan") private String kelurahan;
    @SerializedName("id_kecamatan") private int id_kecamatan;

    public int getId_kelurahan() {
        return id_kelurahan;
    }

    public String getKelurahan() {
        return kelurahan;
    }

    public int getId_kecamatan() {
        return id_kecamatan;
    }
}
