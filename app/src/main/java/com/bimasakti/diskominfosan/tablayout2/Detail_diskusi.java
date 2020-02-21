package com.bimasakti.diskominfosan.tablayout2;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bimasakti.diskominfosan.R;
import com.bimasakti.diskominfosan.User.ModelUser;
import com.bimasakti.diskominfosan.retrofit.ApiClient;
import com.bimasakti.diskominfosan.retrofit.ApiInterface;
import com.bimasakti.diskominfosan.retrofit.response.DefaultResponse;
import com.bimasakti.diskominfosan.store_session.SharedPrefManager;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Detail_diskusi extends AppCompatActivity {

    private TextView judul, tanggal, kategori, kategoriID, penulis, isi;
    private Button komentar;
    private ImageView Image;
    private Button btn_komen, btn_hapus;
    private final String URL = "https://kamismart.sukabumikab.go.id/bimasakti/bima_s4kti/gambar_diskusi/";
    String DisscussId, creatorID;
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_diskusi);

        ActionBar menu = getSupportActionBar();
        menu.setDisplayShowHomeEnabled(true);
        menu.setDisplayHomeAsUpEnabled(true);

        judul   = (TextView) findViewById(R.id.txt_detail_judul);
        tanggal   = (TextView) findViewById(R.id.txt_detail_tanggal);
        kategori   = (TextView) findViewById(R.id.txt_detail_kategori);
        penulis   = (TextView) findViewById(R.id.txt_detail_creator);
        isi   = (TextView) findViewById(R.id.txt_detail_deskripsi);
        Image   = (ImageView) findViewById(R.id.gambar_diskusi);
        btn_komen = (Button) findViewById(R.id.btn_comment);
        btn_hapus = (Button) findViewById(R.id.btn_hapus_diskusi);

        Bundle extra = getIntent().getExtras();
        DisscussId = extra.getString("id_diskusi");
        String title = extra.getString("judul_diskusi");
        String date = extra.getString("tanggal_diskusi");
        String category = extra.getString("kategori_diskusi");
        int categoryId = extra.getInt("kategori_diskusi_id");
        creatorID = extra.getString("id_penulis");
        String creator = extra.getString("penulis_diskusi");
        String core = extra.getString("isi_diskusi");
        String img = extra.getString("nama_gambar");


        ModelUser user = SharedPrefManager.getInstance(this).getUser();
        int userid = Integer.parseInt(creatorID);
        if (userid == user.getId()){
            btn_hapus.setVisibility(View.VISIBLE);
//            Toast.makeText(Detail_diskusi.this, "User yang login cocok!", Toast.LENGTH_LONG).show();
        }

            Picasso.get()
                    .load(URL + img)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .into(Image);
        try {
            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
            DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
            String d = date;
            Date date_first = inputFormat.parse(d);
            String date_fix = outputFormat.format(date_first);
            tanggal.setText(date_fix);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        judul.setText(title);
        kategori.setText(category);
        penulis.setText(creator);
        isi.setText(core);

        int idcat = categoryId;

        if (idcat == 1085){
            kategori.setBackgroundResource(R.color.orange);
        }
        if (idcat == 1086){
            kategori.setBackgroundResource(R.color.biru_muda);
        }
        if (idcat == 1087){
            kategori.setBackgroundResource(R.color.colorPurple);
        }
        if (idcat == 1088){
            kategori.setBackgroundResource(R.color.hijau);
        }
        if (idcat == 1089){
            kategori.setBackgroundResource(R.color.pink);
        }
        if (idcat == 1090){
            kategori.setBackgroundResource(R.color.biru_tua);
        }
        if (idcat == 1091){
            kategori.setBackgroundResource(R.color.red);
        }

        penulis.setCompoundDrawablesWithIntrinsicBounds(R.drawable.pencil_16px, 0, 0, 0);

        btn_komen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Detail_diskusi.this, Komentar_form.class);

                i.putExtra("id_diskusi", DisscussId);
                i.putExtra("id_penulis", creatorID);
                startActivity(i);
                }
            });

        btn_hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDiskusi();
            }
        });
    }

    private void deleteDiskusi(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure?");
        builder.setMessage("Diskusi Anda Akan Hilang!...");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

                int id = Integer.parseInt(DisscussId);
                Call<DefaultResponse> call = apiInterface.deleteDiskusi(id);

                call.enqueue(new Callback<DefaultResponse>() {
                    @Override
                    public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                        DefaultResponse defaultResponse = response.body();
                        if (!defaultResponse.isError()) {
                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("delete", true);
                            setResult(Activity.RESULT_FIRST_USER, returnIntent);
                            finish();
                            Log.e("cui", "Berhasil");
                        }
                        Toast.makeText(Detail_diskusi.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<DefaultResponse> call, Throwable t) {

                        Log.e("cui", t.getMessage());
                        Toast.makeText(Detail_diskusi.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog ad = builder.create();
        ad.show();
    }
}
