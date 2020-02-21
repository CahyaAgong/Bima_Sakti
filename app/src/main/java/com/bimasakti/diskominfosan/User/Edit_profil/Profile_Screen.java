package com.bimasakti.diskominfosan.User.Edit_profil;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bimasakti.diskominfosan.R;
import com.bimasakti.diskominfosan.User.ModelUser;
import com.bimasakti.diskominfosan.store_session.SharedPrefManager;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class Profile_Screen extends AppCompatActivity {

    EditText nik, alamat, kelurahan, kecamatan, notel;
    ImageView foto_profil;
    TextView nama;
    private final String URL = "https://kamismart.sukabumikab.go.id/bimasakti/bima_s4kti/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__screen);
        nama = findViewById(R.id.nama_user_profil);
        nik = findViewById(R.id.txt_nik_profil);
        alamat = findViewById(R.id.txt_alamat_profil);
        kelurahan = findViewById(R.id.txt_kelurahan_profil);
        kecamatan = findViewById(R.id.txt_kecamatan_profil);
        notel = findViewById(R.id.txt_no_profil);
        foto_profil = findViewById(R.id.profile_image_profil);

        ModelUser user = SharedPrefManager.getInstance(Profile_Screen.this).getUser();
        nama.setText(user.getNama_lengkap());
        nik.setText(user.getNik());
        alamat.setText(user.getAlamat());
        kelurahan.setText(user.getKelurahan_desa());
        kecamatan.setText(user.getKecamatan());
        notel.setText(user.getNohp());

        Picasso.get()
                .load(URL + user.getFoto())
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .into(foto_profil);
    }
}
