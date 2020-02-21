package com.bimasakti.diskominfosan.wisataku.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bimasakti.diskominfosan.R;
import com.bimasakti.diskominfosan.wisataku.model.Wisata_model;

import java.util.List;

public class Wisata_adapter extends RecyclerView.Adapter<Wisata_adapter.MyViewHolder> {

    private List<Wisata_model> item;
    private Context context;

    public Wisata_adapter(List<Wisata_model> item, Context context) {
        this.item = item;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wisata_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Wisata_model data = item.get(position);
        holder.nama_kecamatan.setText(data.getNama_wisata());//menampilkan data
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nama_kecamatan, nama_kabupaten, nama_provinsi, id_kecamatan;
        LinearLayout parent;

        public MyViewHolder(View itemView) {
            super(itemView);

            nama_kecamatan = (TextView) itemView.findViewById(R.id.tv_nama_wisata);
//            nama_kabupaten = (TextView) itemView.findViewById(R.id.tv_nama_kabupaten_kec);
//            nama_provinsi = (TextView)itemView.findViewById(R.id.tv_nama_provinsi_kec);
            id_kecamatan = (TextView)itemView.findViewById(R.id.tv_id_wisata);
        }
    }
}
