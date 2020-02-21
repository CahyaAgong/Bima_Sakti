package com.bimasakti.diskominfosan.layanan_webview.rs;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bimasakti.diskominfosan.R;

import java.util.ArrayList;

public class Adapter_rs extends RecyclerView.Adapter<Adapter_rs.Data_BayiViewHolder>{
    private ArrayList<Model_rs> dataList;

    public Adapter_rs(ArrayList<Model_rs> dataList) {
        this.dataList = dataList;
    }

    @Override
    public Data_BayiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_rs, parent, false);
        return new Data_BayiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Data_BayiViewHolder holder, int i) {
//        holder.txtid.setText("ID Bayi : " +dataList.get(i).getId());
        holder.txt_nama_rs.setText(dataList.get(i).getNama_rs());
        holder.txt_alamat_rs.setText(dataList.get(i).getAlamat_rs());
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class Data_BayiViewHolder extends RecyclerView.ViewHolder{
        private TextView txt_nama_rs, txt_alamat_rs, txt_telp_rs, txtid;

        public Data_BayiViewHolder(View itemView) {
            super(itemView);
            txtid = (TextView) itemView.findViewById(R.id.txt_id_rs);
            txt_nama_rs = (TextView) itemView.findViewById(R.id.txt_nama_rs);
            txt_alamat_rs = (TextView) itemView.findViewById(R.id.txt_alamat_rs);
            txt_telp_rs = (TextView) itemView.findViewById(R.id.txt_telp_rs);
        }
    }

}
