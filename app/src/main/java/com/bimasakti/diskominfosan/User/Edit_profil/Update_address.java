package com.bimasakti.diskominfosan.User.Edit_profil;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bimasakti.diskominfosan.R;
import com.bimasakti.diskominfosan.User.ModelUser;
import com.bimasakti.diskominfosan.kecamatan_dan_keluarahan.Kecamatan_act;
import com.bimasakti.diskominfosan.kecamatan_dan_keluarahan.Kelurahan_desa_act;
import com.bimasakti.diskominfosan.retrofit.ApiClient;
import com.bimasakti.diskominfosan.retrofit.ApiInterface;
import com.bimasakti.diskominfosan.store_session.SharedPrefManager;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Update_address extends AppCompatActivity {

    ModelUser user;
    ApiInterface apiInterface;

    private TextView txt_alamat, txt_kecamatan, txt_kelurahan, txt_id_kec, txt_id_kel;
    private Button updateAdress;
    String idkecamtan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_address);
        user = SharedPrefManager.getInstance(this).getUser();

        txt_alamat = findViewById(R.id.txt_alamat_lengkap_update);
        txt_kecamatan = findViewById(R.id.txt_kecamatan_update);
        txt_kelurahan = findViewById(R.id.txt_kelurahan_desa_update);
        txt_id_kec = findViewById(R.id.txt_id_kec_update);
        txt_id_kel = findViewById(R.id.txt_id_kel_update);
        updateAdress = findViewById(R.id.btn_update_address);

        updateAdress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
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
                txt_kecamatan.setText(kec);
                txt_id_kec.setText(id);
                idkecamtan      = txt_id_kec.getText().toString();
            }
            if(resultCode == RESULT_OK){
                String kel_des = data.getStringExtra("pick_kelurahan");
                String id = data.getStringExtra("pick_id");
                txt_kelurahan.setText(kel_des);
                txt_id_kel.setText(id);
            }
        }
    }
    private void update(){
        String getAlamat = txt_alamat.getText().toString().trim();
        String getKecamatan = txt_kecamatan.getText().toString().trim();
        String getKelurahan = txt_kelurahan.getText().toString().trim();

        if (getAlamat.length() < 1 || getKecamatan.length() < 1 || getKelurahan.length() < 1) {
            Toast.makeText(Update_address.this, "Isi Form Dengan Benar", Toast.LENGTH_SHORT).show();
        } else {
            apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
            Call<ResponseBody> call = apiInterface.editprofil(user.getId(), getAlamat, getKecamatan, getKelurahan);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try{
                        Log.d("Edit pRof", "onResponse: berhasil Update");
                        String s = response.body().string();
                        Toast.makeText(Update_address.this, "Berhasil Mengupdate Profil..", Toast.LENGTH_LONG).show();

                        finish();
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }
    }

    public void go_kec(View view) {
        Intent i = new Intent(this, Kecamatan_act.class);
        startActivityForResult(i, 1);
    }

    public void go_kel(View view) {
        if (txt_id_kec.length() < 1){
            Toast.makeText(this, "Pilih Kecamatan Dulu !", Toast.LENGTH_LONG).show();
        } else {
            Intent i = new Intent(this, Kelurahan_desa_act.class);
            i.putExtra("iddesa",idkecamtan);
            startActivityForResult(i, 1);
        }
    }
}
