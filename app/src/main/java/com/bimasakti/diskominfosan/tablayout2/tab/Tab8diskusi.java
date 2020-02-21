package com.bimasakti.diskominfosan.tablayout2.tab;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bimasakti.diskominfosan.R;
import com.bimasakti.diskominfosan.retrofit.ApiClient;
import com.bimasakti.diskominfosan.retrofit.ApiInterface;
import com.bimasakti.diskominfosan.tablayout2.Detail_diskusi;
import com.bimasakti.diskominfosan.tablayout2.list_diskusi.Adapter_diskusi;
import com.bimasakti.diskominfosan.tablayout2.list_diskusi.Model_diskusi;

import java.util.List;

import static android.app.Activity.RESULT_FIRST_USER;

public class Tab8diskusi extends Fragment {

    private RecyclerView recyclerView;
    private Adapter_diskusi adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Model_diskusi> ds;
    private ApiInterface apiInterface;
    public int counter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.tab_8_diskusi,container,false);

        recyclerView = (RecyclerView) v.findViewById(R.id.tab8diskusi_rec);

//        adapter = new Adapter_diskusi(ds);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        fetch_diskusi("1091");
        new CountDownTimer(10000, 500){
            public void onTick(long millisUntilFinished){
                counter++;
            }
            public  void onFinish(){
                fetch_diskusi("1091");
                start();
            }
        }.start();
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {

                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });

            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if (child != null && gestureDetector.onTouchEvent(e)) {
                    final int position = rv.getChildAdapterPosition(child);

                    Intent data_diskusi = new Intent(getContext(), Detail_diskusi.class);
                    data_diskusi.putExtra("id_diskusi", ds.get(position).getId_diskusi());
                    data_diskusi.putExtra("judul_diskusi", ds.get(position).getJudul());
                    data_diskusi.putExtra("kategori_diskusi_id", ds.get(position).getId_kategori());
                    data_diskusi.putExtra("kategori_diskusi", ds.get(position).getNama_kategori());
                    data_diskusi.putExtra("tanggal_diskusi", ds.get(position).getCreated_date());
                    data_diskusi.putExtra("id_penulis", ds.get(position).getUserid());
                    data_diskusi.putExtra("penulis_diskusi", ds.get(position).getId_user());
                    data_diskusi.putExtra("isi_diskusi", ds.get(position).getDeskripsi());
                    data_diskusi.putExtra("nama_gambar", ds.get(position).getGambar());
                    startActivityForResult(data_diskusi, 3);
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_FIRST_USER){
            if (data.getBooleanExtra("delete", true)){
                fetch_diskusi("7");
            }
        }
    }
    public void fetch_diskusi(String key){

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        retrofit2.Call<List<Model_diskusi>> call = apiInterface.getDiskusi(key);
        call.enqueue(new retrofit2.Callback<List<Model_diskusi>>() {
            @Override
            public void onResponse(retrofit2.Call<List<Model_diskusi>> call, retrofit2.Response<List<Model_diskusi>> response) {
                ds = response.body();
                adapter = new Adapter_diskusi(ds, getContext());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
//                Toast.makeText(getActivity(), "Success", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(retrofit2.Call<List<Model_diskusi>> call, Throwable t) {
                Toast.makeText(getContext(), "Error\n"+t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}