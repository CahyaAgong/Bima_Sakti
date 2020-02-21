package com.bimasakti.diskominfosan.tablayout2.list_komentar;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Adapter_komentar extends RecyclerView.Adapter<Adapter_komentar.Data_BayiViewHolder>{

    private List<Model_komentar> item;
    private Context context;
    private final String URL = "https://kamismart.sukabumikab.go.id/bimasakti/bima_s4kti/";
    private ApiInterface apiInterface;

    public Adapter_komentar(List<Model_komentar> item, Context context) {
        this.item = item;
        this.context = context;
    }

    @Override
    public Data_BayiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_komentar, parent, false);
        return new Data_BayiViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull Data_BayiViewHolder holder, int i) {
        ModelUser user = SharedPrefManager.getInstance(context).getUser();
        final Model_komentar data = item.get(i);
        String id = Integer.toString(data.getId_user());
        holder.id_commentor.setText(id);

        try {
            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
            DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy | HH:mm", Locale.US);
            String d = data.getCreated_date();

            Date date = inputFormat.parse(d);
            String tanggal = outputFormat.format(date);
            Log.e("TANGGAL SEKARANG", tanggal);
            holder.txt_tanggal.setText(tanggal);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.txt_nama.setText(data.getNama_user());
        holder.txt_isi.setText(data.getKomentar());
        holder.id.setText(data.getId_komentar());

        Picasso.get()
                .load(URL + data.getGambar())
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .into(holder.foto_profil);

        holder.foto_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.popup_detail_profil);
                ImageView img = (ImageView) dialog.findViewById(R.id.img_profil);
                TextView nama = (TextView) dialog.findViewById(R.id.txt_nama_detail_profil);
                TextView alamat = (TextView) dialog.findViewById(R.id.txt_add_detail_profil);
                Picasso.get()
                        .load(URL + data.getGambar())
                        .memoryPolicy(MemoryPolicy.NO_CACHE)
                        .networkPolicy(NetworkPolicy.NO_CACHE)
                        .into(img);
                nama.setText(data.getNama_user());
                alamat.setText(data.getKelurahan()+", "+data.getKecamatan());

                dialog.show();
            }
        });


        holder.hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Anda Yakin Ingin Menghapus Komentar Anda?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
                        int id = Integer.parseInt(data.getId_komentar());
                        Call<DefaultResponse> call = apiInterface.deleteKomentar(id);

                        call.enqueue(new Callback<DefaultResponse>() {
                            @Override
                            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                                DefaultResponse defaultResponse = response.body();
                                if (!defaultResponse.isError()) {
                                    Log.e("cui", "Berhasil");
                                    Toast.makeText(context, "Berhasil Menghapus!", Toast.LENGTH_SHORT).show();
                                }
//                                Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailure(Call<DefaultResponse> call, Throwable t) {

                                Log.e("cui", t.getMessage());
                                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
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
        });

        if (data.getId_user() == user.getId()){
            holder.hapus.setVisibility(View.VISIBLE);
//            Toast.makeText(context, "User yang login cocok!", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public class Data_BayiViewHolder extends RecyclerView.ViewHolder{
        private TextView txt_nama, txt_isi, txt_tanggal, id, hapus, id_commentor;
        private ImageView foto_profil;

        public Data_BayiViewHolder(View itemView) {
            super(itemView);
            id_commentor = (TextView) itemView.findViewById(R.id.id_commentor);
            id = (TextView) itemView.findViewById(R.id.id_comment);
            txt_nama = (TextView) itemView.findViewById(R.id.nama_komentator);
            txt_isi = (TextView) itemView.findViewById(R.id.isi_komentar);
            txt_tanggal = (TextView) itemView.findViewById(R.id.tanggal_komentar);
            foto_profil = (ImageView) itemView.findViewById(R.id.profile_image);
            hapus = (TextView) itemView.findViewById(R.id.txt_hapus_komentar);


        }
    }
}
