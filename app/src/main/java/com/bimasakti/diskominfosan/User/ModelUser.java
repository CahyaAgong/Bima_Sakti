package com.bimasakti.diskominfosan.User;

public class ModelUser {
    private int id;
    private String nik, nama_lengkap, password, foto, nohp, alamat, kecamatan, kelurahan_desa;

    public ModelUser(int id, String nik, String nama_lengkap, String password, String foto, String nohp, String alamat, String kecamatan, String kelurahan_desa) {
        this.id = id;
        this.nik = nik;
        this.nama_lengkap = nama_lengkap;
        this.password = password;
        this.foto = foto;
        this.nohp = nohp;
        this.alamat = alamat;
        this.kecamatan = kecamatan;
        this.kelurahan_desa = kelurahan_desa;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public String getKelurahan_desa() {
        return kelurahan_desa;
    }

    public int getId() {
        return id;
    }

    public String getNik() {
        return nik;
    }

    public String getNama_lengkap() {
        return nama_lengkap;
    }

    public String getPassword() {
        return password;
    }

    public String getFoto() {
        return foto;
    }

    public String getNohp() {
        return nohp;
    }
}
