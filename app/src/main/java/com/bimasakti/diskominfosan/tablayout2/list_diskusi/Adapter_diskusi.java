package com.bimasakti.diskominfosan.tablayout2.list_diskusi;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bimasakti.diskominfosan.R;
import com.bimasakti.diskominfosan.tablayout2.list_komentar.Model_komentar;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Adapter_diskusi extends RecyclerView.Adapter<Adapter_diskusi.Data_BayiViewHolder>{

    private List<Model_diskusi> item;
    private List<Model_komentar> item2;
    private Context context;
    private final String URL = "https://kamismart.sukabumikab.go.id/bimasakti/bima_s4kti/gambar_diskusi/";

    public Adapter_diskusi(List<Model_diskusi> item, Context context) {
        this.item = item;
        this.context = context;
    }

    @Override
    public Data_BayiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_diskusi, parent, false);
        return new Data_BayiViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull Data_BayiViewHolder holder, int i) {
        final Model_diskusi data = item.get(i);

        try {
            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
            DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
            String d = data.getCreated_date();

            Date date = inputFormat.parse(d);
            String tanggal = outputFormat.format(date);
            Log.e("DATE SEKARANG", tanggal);
            holder.txt_created_date.setText(tanggal);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (data.getId_kategori() == 1085){
            holder.txt_category.setBackgroundResource(R.color.orange);
        }
        if (data.getId_kategori() == 1086){
            holder.txt_category.setBackgroundResource(R.color.biru_muda);
        }
        if (data.getId_kategori() == 1087){
            holder.txt_category.setBackgroundResource(R.color.colorPurple);
        }
        if (data.getId_kategori() == 1088){
            holder.txt_category.setBackgroundResource(R.color.hijau);
        }
        if (data.getId_kategori() == 1089){
            holder.txt_category.setBackgroundResource(R.color.pink);
        }
        if (data.getId_kategori() == 1090){
            holder.txt_category.setBackgroundResource(R.color.biru_tua);
        }
        if (data.getId_kategori() == 1091){
            holder.txt_category.setBackgroundResource(R.color.red);
        }
//        holder.tx_comment.setCompoundDrawablesWithIntrinsicBounds(R.drawable.comment_icon, 0, 0, 0);
//        holder.tx_comment.setText(item2.size());
        holder.txt_category.setText(data.getNama_kategori());
        holder.txt_title.setText(data.getJudul());
        holder.txt_creator.setText(data.getId_user());
        holder.txt_creator.setCompoundDrawablesWithIntrinsicBounds(R.drawable.pencil_16px, 0, 0, 0);

        Picasso.get()
                .load(URL + data.getGambar())
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .into(holder.gambar_diskusi);

    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public class Data_BayiViewHolder extends RecyclerView.ViewHolder{
        private TextView txt_id, tx_comment, txt_category, txt_created_date, txt_title, txt_isi, txt_creator;
        private ImageView gambar_diskusi;

        public Data_BayiViewHolder(View itemView) {
            super(itemView);
            gambar_diskusi      = (ImageView) itemView.findViewById(R.id.gambar_diskusi_list);
            txt_id = (TextView) itemView.findViewById(R.id.id_diskusi);
            tx_comment = (TextView) itemView.findViewById(R.id.txt_comment);
            txt_category = (TextView) itemView.findViewById(R.id.kategori_diskusi);
            txt_created_date = (TextView) itemView.findViewById(R.id.created_date);
            txt_title = (TextView) itemView.findViewById(R.id.judul_diskusi);
            txt_creator = (TextView) itemView.findViewById(R.id.penulis_diskusi);
        }
    }

}
