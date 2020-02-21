package com.bimasakti.diskominfosan.tablayout2.list_komentar;

import com.google.gson.annotations.SerializedName;

public class Model_komentar {

    @SerializedName("id_komentar") private String id_komentar;
    @SerializedName("id_diskusi") private String id_diskusi;
    @SerializedName("id_user") private int id_user;
    @SerializedName("nama_user") private String nama_user;
    @SerializedName("komentar") private String komentar;
    @SerializedName("kecamatan") private String kecamatan;
    @SerializedName("kelurahan") private String kelurahan;
    @SerializedName("created_date") private String created_date;
    @SerializedName("gambar") private String gambar;

    public String getId_komentar() {
        return id_komentar;
    }

    public String getId_diskusi() {
        return id_diskusi;
    }

    public int getId_user() {
        return id_user;
    }

    public String getNama_user() {
        return nama_user;
    }

    public String getKomentar() {
        return komentar;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public String getKelurahan() {
        return kelurahan;
    }

    public String getCreated_date() {
        return created_date;
    }

    public String getGambar() {
        return gambar;
    }
}
