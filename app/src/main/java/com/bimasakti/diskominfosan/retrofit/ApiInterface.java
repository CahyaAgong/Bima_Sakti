package com.bimasakti.diskominfosan.retrofit;

import com.bimasakti.diskominfosan.kecamatan_dan_keluarahan.Model.Kecamatan_model;
import com.bimasakti.diskominfosan.kecamatan_dan_keluarahan.Model.Kelurahan_model;
import com.bimasakti.diskominfosan.response.ResponseRoute;
import com.bimasakti.diskominfosan.response.ServerResponse;
import com.bimasakti.diskominfosan.response.loginResponse;
import com.bimasakti.diskominfosan.retrofit.response.DefaultResponse;
import com.bimasakti.diskominfosan.running_text.Running_text_model;
import com.bimasakti.diskominfosan.slider_package.ImageSlider_model;
import com.bimasakti.diskominfosan.tablayout2.list_diskusi.Model_diskusi;
import com.bimasakti.diskominfosan.tablayout2.list_komentar.Model_komentar;
import com.bimasakti.diskominfosan.tablayout2.spinner.SpinnerKategori_model;
import com.bimasakti.diskominfosan.wisataku.model.Wisata_model;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface ApiInterface {


    @GET("damkar/curl_directions.php")
    Call<ResponseRoute> request_route(
            @Query("format") String format,
            @Query("origin") String origin,
            @Query("destination") String destination,
            @Query("key") String api_key
    );

    @GET("getKecamatanWithId.php")
    Call<List<Kecamatan_model>> getKecamatan(
            @Query("key") String keyword);

    @GET("getDesaWithId.php")
    Call<List<Kelurahan_model>> getKelurahan(
            @Query("key") String keyword,
            @Query("id") int id
            );

    @GET("getWisata.php")
    Call<List<Wisata_model>> getWisata(
            @Query("key") String keyword);


    @GET("getDiskusi.php")
    Call<List<Model_diskusi>> getDiskusi(
            @Query("key") String keyword);


    @GET("getImageSlider.php")
    Call<List<ImageSlider_model>> getSlider();


    @GET("getKategori.php")
    Call<List<SpinnerKategori_model>> getCategory();

    @GET("getKomentar.php")
    Call<List<Model_komentar>> getComment(
            @Query("key") String keyword
    );


    @GET("getRunningtext.php")
    Call<List<Running_text_model>> getRunning();

    @FormUrlEncoded
    @POST("createUser.php")
    Call<ResponseBody>createUser(
            @Field("nik") String nik,
            @Field("nama_lengkap") String nama_lengkap,
            @Field("password") String password,
            @Field("alamat") String alamat,
            @Field("kecamatan") String kecamatan,
            @Field("kelurahan_desa") String kelurahan_desa,
            @Field("no_telpon") String no_telpon
    );

    @FormUrlEncoded
    @POST("makeTokenDevice.php")
    Call<ResponseBody>createToken(
            @Field("token") String token,
            @Field("device") String device
    );

    @FormUrlEncoded
    @POST("editProfil.php")
    Call<ResponseBody>editprofil(
            @Field("id_user") int id_user,
            @Field("alamat") String alamat,
            @Field("kecamatan") String kecamatan,
            @Field("kelurahan_desa") String kelurahan_desa
    );

    @FormUrlEncoded
    @POST("editPass.php")
    Call<ResponseBody>editpass(
            @Field("id_user") int id_user,
            @Field("pass") String pass
    );

    @FormUrlEncoded
    @POST("createDiskusi.php")
    Call<ResponseBody>createDiskusi(
            @Field("kodedata") int kategori,
            @Field("id_user") int id_user,
            @Field("judul") String judul,
            @Field("deskripsi") String deskripsi,
            @Field("create_date") String create_date,
            @Field("foto") String foto
    );

    @FormUrlEncoded
    @POST("createKomentar.php")
    Call<ResponseBody>createComment(
            @Field("id_diskusi") String id_diskusi,
            @Field("id_user") int id_user,
            @Field("komentar") String komentar,
            @Field("created_date") String created_date
    );

    @FormUrlEncoded
    @POST("login_api.php")
    Call<loginResponse> login(
            @Field("nik") String username,
            @Field("password") String password
    );

    @Multipart
    @POST("updateImage.php")
    Call<ServerResponse> updateImage(
            @Header("Authorization") String authorization,
            @PartMap Map<String, RequestBody> map
    );

    @Multipart
    @POST("upDiskusiImage.php")
    Call<ServerResponse> uploadImgDiskusi(
            @Header("Authorization") String authorization,
            @PartMap Map<String, RequestBody> map
    );

    @FormUrlEncoded
    @POST("updateImageName.php")
    Call<ResponseBody> updateImageName(
            @Field("id") int id,
            @Field("nama_gambar") String nama_gambar
    );

    @GET("fcm_bimasakti.php")
    Call<ResponseBody> sendNotif();

    @FormUrlEncoded
    @POST("driver/php_firebase/fcm_bimasakti.php")
    Call<ResponseBody> sendAmbulanNotif(
            @Field("id") int id,
            @Field("id_pesanan") String id_pesanan,
            @Field("pemesan") String pemesan,
            @Field("notelepon") String notelepon,
            @Field("lokasi_jemput") String lokasi_jemput,
            @Field("waktu") String waktu,
            @Field("foto") String foto
    );

    @FormUrlEncoded
    @POST("damkar/php_firebase/fcm_bimasakti.php")
    Call<ResponseBody> sendDamkarNotif(
            @Field("id") int id,
            @Field("id_pesanan") String id_pesanan,
            @Field("pemesan") String pemesan,
            @Field("notelepon") String notelepon,
            @Field("lokasi_jemput") String lokasi_jemput,
            @Field("waktu") String waktu,
            @Field("foto") String foto,
            @Field("lat_caller") double lat_caller,
            @Field("lng_caller") double lng_caller,
            @Field("tipe_caller") String tipe_caller
    );

    @GET("json")
    Call<ResponseRoute> request_route(
            @Query("origin") String origin,
            @Query("destination") String destination,
            @Query("api_key") String api_key
    );

    @FormUrlEncoded
    @POST("deleteDiskusi.php/{id_diskusi}")
    Call<DefaultResponse>deleteDiskusi(
            @Field("id_diskusi") int id_diskusi
    );


    @FormUrlEncoded
    @POST("deleteKomentar.php/{id_komentar}")
    Call<DefaultResponse>deleteKomentar(
            @Field("id_komentar") int id_komentar
    );

}
