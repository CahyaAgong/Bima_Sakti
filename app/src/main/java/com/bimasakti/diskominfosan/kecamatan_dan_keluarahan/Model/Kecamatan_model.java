package com.bimasakti.diskominfosan.kecamatan_dan_keluarahan.Model;

import com.google.gson.annotations.SerializedName;

public class Kecamatan_model {


    @SerializedName("id_kecamatan") private int id_kecamatan;
    @SerializedName("kecamatan") private String kecamatan;

    public int getId_kecamatan() {
        return id_kecamatan;
    }

    public String getKecamatan() {
        return kecamatan;
    }

}
