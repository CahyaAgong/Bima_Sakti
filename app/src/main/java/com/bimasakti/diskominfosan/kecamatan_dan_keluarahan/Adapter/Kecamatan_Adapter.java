package com.bimasakti.diskominfosan.kecamatan_dan_keluarahan.Adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bimasakti.diskominfosan.R;
import com.bimasakti.diskominfosan.kecamatan_dan_keluarahan.Model.Kecamatan_model;

import java.util.List;

public class Kecamatan_Adapter extends RecyclerView.Adapter<Kecamatan_Adapter.MyViewHolder> {

    private List<Kecamatan_model> item;
    private Context context;

    public Kecamatan_Adapter(List<Kecamatan_model> item, Context context) {
        this.item = item;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kecamatan_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Kecamatan_model data = item.get(position);
        holder.nama_kecamatan.setText(data.getKecamatan());//menampilkan data

//        int getid = data.getId_kecamatan();

//        holder.id_kecamatan.setText(getid);
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

            nama_kecamatan = (TextView) itemView.findViewById(R.id.tv_nama_kecamatan_kec);
//            nama_kabupaten = (TextView) itemView.findViewById(R.id.tv_nama_kabupaten_kec);
//            nama_provinsi = (TextView)itemView.findViewById(R.id.tv_nama_provinsi_kec);
            id_kecamatan = (TextView)itemView.findViewById(R.id.tv_id_kecamatan);
        }
    }
}
