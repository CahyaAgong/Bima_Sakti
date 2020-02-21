package com.bimasakti.diskominfosan.wisataku;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bimasakti.diskominfosan.R;
import com.bimasakti.diskominfosan.retrofit.ApiClient;
import com.bimasakti.diskominfosan.retrofit.ApiInterface;
import com.bimasakti.diskominfosan.wisataku.adapter.Wisata_adapter;
import com.bimasakti.diskominfosan.wisataku.model.Wisata_model;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Wisataku_act extends AppCompatActivity {

    private final String URL = "https://kamismart.sukabumikab.go.id/bimasakti/bima_s4kti/";
    //with retrofit
    private Wisata_adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private List<Wisata_model> item;
    private ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wisataku_act);

        //with retrofit
        recyclerView = (RecyclerView) findViewById(R.id.list_wisata);
        recyclerView = findViewById(R.id.list_wisata);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        fetchbandara("");

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(getApplicationContext(), new GestureDetector.SimpleOnGestureListener() {

                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });

            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if (child != null && gestureDetector.onTouchEvent(e)) {
                    final int position = rv.getChildAdapterPosition(child);

                    final Dialog dialog = new Dialog(Wisataku_act.this);
                    dialog.setContentView(R.layout.wisata_popup_layout);

                    TextView text1 = (TextView) dialog.findViewById(R.id.tv_nama_wisata_popup);
                    TextView text2 = (TextView) dialog.findViewById(R.id.tv_alamat_wisata_popup);
                    ImageView img  = (ImageView) dialog.findViewById(R.id.foto_wisata);
                    Button button  = (Button) dialog.findViewById(R.id.tutup_popup);
                    Button map  = (Button) dialog.findViewById(R.id.maps_wisata);

                    text1.setText(item.get(position).getNama_wisata());
                    text2.setText(item.get(position).getAlamat_wisata());
                    Picasso.get()
                            .load(item.get(position).getFoto())
//                            .memoryPolicy(MemoryPolicy.NO_CACHE)
//                            .networkPolicy(NetworkPolicy.NO_CACHE)
                            .into(img);

//                    Toast.makeText(getApplicationContext(), item.get(position).getFoto(), Toast.LENGTH_LONG).show();

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    map.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (item.get(position).getLocation().length() < 1 || item.get(position).getLocation().equals(null))
                            {
                                Toast.makeText(dialog.getContext(), "Maps Belum Tersedia!", Toast.LENGTH_LONG).show();
                            }else {
                                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                                        Uri.parse(item.get(position).getLocation()));
                                startActivity(intent);
                            }
                        }
                    });

                    dialog.show();
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
    }

    public void fetchbandara(String key){

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        retrofit2.Call<List<Wisata_model>> call = apiInterface.getWisata(key);
        call.enqueue(new retrofit2.Callback<List<Wisata_model>>() {
            @Override
            public void onResponse(retrofit2.Call<List<Wisata_model>> call, retrofit2.Response<List<Wisata_model>> response) {
                item = response.body();
                adapter = new Wisata_adapter(item, Wisataku_act.this);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(retrofit2.Call<List<Wisata_model>> call, Throwable t) {
                Toast.makeText(Wisataku_act.this, "Error\n"+t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fetchbandara(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fetchbandara(newText);
                return false;
            }
        });
        return true;
    }
}
