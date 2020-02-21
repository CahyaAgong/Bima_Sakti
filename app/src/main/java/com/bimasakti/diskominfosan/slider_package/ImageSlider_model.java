package com.bimasakti.diskominfosan.slider_package;

import com.google.gson.annotations.SerializedName;

public class ImageSlider_model {

    @SerializedName("id") private int id;
    @SerializedName("judul") private String judul;
    @SerializedName("kategori") private String kategori;
    @SerializedName("file") private String file;

    public int getId() {
        return id;
    }

    public String getJudul() {
        return judul;
    }

    public String getKategori() {
        return kategori;
    }

    public String getFile() {
        return file;
    }
}
