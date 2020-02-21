package com.bimasakti.diskominfosan.e_ambulance.order;

public class data_order {
    private String key;
    private String pemesan;
    private String driver;
    private String no_driver;
    private String no_pemesan;
    private String lokasi_awal;
    private String lokasi_akhir;
    private String jarak;
    private String status;
    private String plat;
    private String instansi;

    public data_order(String pemesan, String driver, String no_driver, String no_pemesan, String lokasi_awal, String lokasi_akhir, String jarak, String status, String plat, String instansi) {
        this.pemesan = pemesan;
        this.driver = driver;
        this.no_driver = no_driver;
        this.no_pemesan = no_pemesan;
        this.lokasi_awal = lokasi_awal;
        this.lokasi_akhir = lokasi_akhir;
        this.jarak = jarak;
        this.status = status;
        this.plat = plat;
        this.instansi = instansi;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPemesan() {
        return pemesan;
    }

    public void setPemesan(String pemesan) {
        this.pemesan = pemesan;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getNo_driver() {
        return no_driver;
    }

    public void setNo_driver(String no_driver) {
        this.no_driver = no_driver;
    }

    public String getNo_pemesan() {
        return no_pemesan;
    }

    public void setNo_pemesan(String no_pemesan) {
        this.no_pemesan = no_pemesan;
    }

    public String getLokasi_awal() {
        return lokasi_awal;
    }

    public void setLokasi_awal(String lokasi_awal) {
        this.lokasi_awal = lokasi_awal;
    }

    public String getLokasi_akhir() {
        return lokasi_akhir;
    }

    public void setLokasi_akhir(String lokasi_akhir) {
        this.lokasi_akhir = lokasi_akhir;
    }

    public String getJarak() {
        return jarak;
    }

    public void setJarak(String jarak) {
        this.jarak = jarak;
    }

    public String getPlat() {
        return plat;
    }

    public void setPlat(String plat) {
        this.plat = plat;
    }

    public String getInstansi() {
        return instansi;
    }

    public void setInstansi(String instansi) {
        this.instansi = instansi;
    }
}
