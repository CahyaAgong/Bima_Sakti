package com.bimasakti.diskominfosan.layanan_webview.rs;

public class Model_rs {
    private String id;
    private String nama_rs;
    private String alamat_rs;
    private String notelp;
    private String status;
    private String wv;

    public Model_rs(String id, String nama_rs, String alamat_rs, String notelp, String status, String wv) {
        this.id = id;
        this.nama_rs = nama_rs;
        this.alamat_rs = alamat_rs;
        this.notelp = notelp;
        this.status = status;
        this.wv = wv;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama_rs() {
        return nama_rs;
    }

    public void setNama_rs(String nama_rs) {
        this.nama_rs = nama_rs;
    }

    public String getAlamat_rs() {
        return alamat_rs;
    }

    public void setAlamat_rs(String alamat_rs) {
        this.alamat_rs = alamat_rs;
    }

    public String getNotelp() {
        return notelp;
    }

    public void setNotelp(String notelp) {
        this.notelp = notelp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWv() {
        return wv;
    }

    public void setWv(String wv) {
        this.wv = wv;
    }
}
