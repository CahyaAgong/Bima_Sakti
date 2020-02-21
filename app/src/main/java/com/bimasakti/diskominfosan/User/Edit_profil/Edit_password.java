package com.bimasakti.diskominfosan.User.Edit_profil;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bimasakti.diskominfosan.R;
import com.bimasakti.diskominfosan.User.ModelUser;
import com.bimasakti.diskominfosan.retrofit.ApiClient;
import com.bimasakti.diskominfosan.retrofit.ApiInterface;
import com.bimasakti.diskominfosan.store_session.SharedPrefManager;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Edit_password extends AppCompatActivity {

    EditText pass_lama, pass_baru, ver_pass_baru;
    Button update;
    ApiInterface apiInterface;
    ModelUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);

        pass_lama = findViewById(R.id.txt_pass_lama);
        pass_baru = findViewById(R.id.txt_pass_baru);
        ver_pass_baru = findViewById(R.id.txt_ver_pass_baru);
        update = findViewById(R.id.btn_update_pass);
        user = SharedPrefManager.getInstance(this).getUser();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });

    }

    private void update(){
        String getPassLama = pass_lama.getText().toString().trim();
        String getPassBaru = pass_baru.getText().toString().trim();
        String verPassBaru = ver_pass_baru.getText().toString().trim();

        if ( getPassBaru.length() < 1 || verPassBaru.length() < 1) {
            Toast.makeText(Edit_password.this, "Isi Form Dengan Benar", Toast.LENGTH_SHORT).show();
        }
        else if(!getPassBaru.equals(verPassBaru)){
            Toast.makeText(Edit_password.this, "Password Yang Anda Masukkan Tidak Cocok!", Toast.LENGTH_SHORT).show();
        }
        else {
            apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
            Call<ResponseBody> call = apiInterface.editpass(user.getId(), getPassBaru);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try{
                        Log.d("Edit pRof", "onResponse: berhasil Update");
                        String s = response.body().string();
                        Toast.makeText(Edit_password.this, "Berhasil Mengupdate Password..", Toast.LENGTH_LONG).show();
                        finish();
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(Edit_password.this, "Koneksi Gagal..", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
