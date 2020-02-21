package com.bimasakti.diskominfosan.tablayout2.list_diskusi;

import com.google.gson.annotations.SerializedName;

public class Model_diskusi {

    @SerializedName("id_komentar") private String id_komentar;
    @SerializedName("id_diskusi") private String id_diskusi;
    @SerializedName("id_kategori") private int id_kategori;
    @SerializedName("nama_kategori") private String nama_kategori;
    @SerializedName("userid") private String userid;
    @SerializedName("id_user") private String id_user;
    @SerializedName("judul") private String judul;
    @SerializedName("deskripsi") private String deskripsi;
    @SerializedName("created_date") private String created_date;
    @SerializedName("foto") private String gambar;
    @SerializedName("gambar") private String foto;

    public String getId_komentar() {
        return id_komentar;
    }

    public String getId_diskusi() {
        return id_diskusi;
    }

    public String getUserid() {
        return userid;
    }

    public int getId_kategori() {
        return id_kategori;
    }

    public String getNama_kategori() {
        return nama_kategori;
    }

    public String getId_user() {
        return id_user;
    }

    public String getJudul() {
        return judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getCreated_date() {
        return created_date;
    }

    public String getGambar() {
        return gambar;
    }

    public String getFoto() {
        return foto;
    }
}
