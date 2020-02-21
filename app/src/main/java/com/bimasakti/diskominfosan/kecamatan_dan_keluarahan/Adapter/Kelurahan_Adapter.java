package com.bimasakti.diskominfosan.kecamatan_dan_keluarahan.Adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bimasakti.diskominfosan.R;
import com.bimasakti.diskominfosan.kecamatan_dan_keluarahan.Model.Kelurahan_model;

import java.util.List;

public class Kelurahan_Adapter extends RecyclerView.Adapter<Kelurahan_Adapter.MyViewHolder> {

    private List<Kelurahan_model> item;
    private Context context;

    public Kelurahan_Adapter(List<Kelurahan_model> item, Context context) {
        this.item = item;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kelurahan_desa_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Kelurahan_model data = item.get(position);
        holder.nama_kelurahan.setText(data.getKelurahan());//menampilkan data

        int getid = data.getId_kecamatan();
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nama_kelurahan, nama_kabupaten, nama_provinsi, id_kecamatan;
        LinearLayout parent;

        public MyViewHolder(View itemView) {
            super(itemView);

            nama_kelurahan = (TextView) itemView.findViewById(R.id.tv_nama_kelurahan_desa);
            nama_provinsi = (TextView)itemView.findViewById(R.id.tv_nama_provinsi);
            id_kecamatan = (TextView)itemView.findViewById(R.id.tv_id_kecamatan);
        }
    }
}
