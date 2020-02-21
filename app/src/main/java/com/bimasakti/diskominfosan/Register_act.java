package com.bimasakti.diskominfosan;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bimasakti.diskominfosan.kecamatan_dan_keluarahan.Kecamatan_act;
import com.bimasakti.diskominfosan.kecamatan_dan_keluarahan.Kelurahan_desa_act;
import com.bimasakti.diskominfosan.retrofit.ApiClient;
import com.bimasakti.diskominfosan.retrofit.ApiInterface;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register_act extends AppCompatActivity {

    private static final int REQUEST_PICK_PHOTO  = 200;
    private static final int REQUEST_PICK_KECAMATAN  = 300;
    private static final int REQUEST_PICK_KELURAHAN  = 400;
    private String mediaPath;
    private String postPath;

    EditText nik, nama_lengkap, password, alamat, kecamatan, kelurahan_desa, notelp, txt_id_kec, txt_id_kel, random_number;
    TextView status_gambar;
    Button pick_image, sign_up, sms, button, kirim_ver;
    private ApiInterface apiInterface;
    String idkecamtan;
    private CheckBox ShowPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_act);


        txt_id_kec      = (EditText) findViewById(R.id.txt_id_kec);
        txt_id_kel      = (EditText) findViewById(R.id.txt_id_kel);

        nik             = (EditText) findViewById(R.id.txt_nik);
        nama_lengkap    = (EditText) findViewById(R.id.txt_nama_lengkap);
        password        = (EditText) findViewById(R.id.txt_password);
        alamat          = (EditText) findViewById(R.id.txt_alamat_lengkap);
        kecamatan       = (EditText) findViewById(R.id.txt_kecamatan);
        kelurahan_desa  = (EditText) findViewById(R.id.txt_kelurahan_desa);
        notelp          = (EditText) findViewById(R.id.txt_no_telpon);

        sign_up         = (Button) findViewById(R.id.btn_signup);
        sms         = (Button) findViewById(R.id.btn_sms);


//        ShowPass = findViewById(R.id.showPass);
//        ShowPass.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(ShowPass.isChecked()){
//                    //Saat Checkbox dalam keadaan Checked, maka password akan di tampilkan
//                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//                    password.setSelection(password.getText().length());
//                }else {
//                    //Jika tidak, maka password akan di sembuyikan
//                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                    password.setSelection(password.getText().length());
//                }
//            }
//        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String get_nik      = nik.getText().toString().trim();
                String get_nama     = nama_lengkap.getText().toString().trim();
                String get_pass     = password.getText().toString().trim();
                String get_alamat   = alamat.getText().toString().trim();
                String get_kec      = kecamatan.getText().toString().trim();
                String get_kelu     = kelurahan_desa.getText().toString().trim();
                String get_telp     = notelp.getText().toString().trim();

                if (get_nik.length() < 16 || get_nama.length() < 1 || get_pass.length() < 1 || get_alamat.length() < 1 || get_kec.length() < 1
                        || get_kelu.length() < 1 || get_telp.length() < 1){
                    Toast.makeText(Register_act.this, "Isi Form Dengan Benar!!", Toast.LENGTH_SHORT).show();
                    Toast.makeText(Register_act.this, "Nomor NIK Anda Tidak Sesuai!", Toast.LENGTH_SHORT).show();

                }else {
                    apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
                    Call<ResponseBody> call = apiInterface.createUser(get_nik, get_nama, get_pass, get_alamat, get_kec, get_kelu, get_telp);

                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            try {
                                String s = response.body().string();
                                Toast.makeText(Register_act.this, "SELAMAT, PENDAFTARAN ANDA BERHASIL, SILAHKAN LOGIN DENGAN NIK DAN PASSWORD YANG ANDA BUAT!", Toast.LENGTH_LONG).show();
                                nik.setText("");
                                nama_lengkap.setText("");
                                password.setText("");
                                alamat.setText("");
                                kecamatan.setText("");
                                kelurahan_desa.setText("");
                                notelp.setText("");
                                finish();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(Register_act.this, t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if(resultCode == RESULT_FIRST_USER){
                String kec = data.getStringExtra("pick_kecamatan");
                String id = data.getStringExtra("pick_id");
                kecamatan.setText(kec);
                txt_id_kec.setText(id);
                idkecamtan      = txt_id_kec.getText().toString();
            }
            if(resultCode == RESULT_OK){
                String kel_des = data.getStringExtra("pick_kelurahan");
                String id = data.getStringExtra("pick_id");
                kelurahan_desa.setText(kel_des);
                txt_id_kel.setText(id);
            }
        }
    }

    public void go_kecamatan(View view) {
        Intent i = new Intent(this, Kecamatan_act.class);
        startActivityForResult(i, 1);
    }

    public void go_kelurahan(View view) {
        if (txt_id_kec.length() < 1){
            Toast.makeText(this, "Pilih Kecamatan Dulu !", Toast.LENGTH_LONG).show();
        } else {
            Intent i = new Intent(this, Kelurahan_desa_act.class);
            i.putExtra("iddesa",idkecamtan);
            startActivityForResult(i, 1);
        }
    }
}
