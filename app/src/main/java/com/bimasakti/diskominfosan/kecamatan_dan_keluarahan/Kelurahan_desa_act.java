package com.bimasakti.diskominfosan.kecamatan_dan_keluarahan;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.bimasakti.diskominfosan.R;
import com.bimasakti.diskominfosan.kecamatan_dan_keluarahan.Adapter.Kelurahan_Adapter;
import com.bimasakti.diskominfosan.kecamatan_dan_keluarahan.Model.Kelurahan_model;
import com.bimasakti.diskominfosan.retrofit.ApiClient;
import com.bimasakti.diskominfosan.retrofit.ApiInterface;

import java.util.List;

public class Kelurahan_desa_act extends AppCompatActivity {

    private Kelurahan_Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private List<Kelurahan_model> item;
    private ApiInterface apiInterface;
    private String id_kecamatan;
    private int getidkecamatan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kelurahan_desa_act);

        Bundle ex = getIntent().getExtras();
        id_kecamatan = ex.getString("iddesa");
        getidkecamatan = Integer.parseInt(id_kecamatan);
        //with retrofit
        recyclerView = (RecyclerView) findViewById(R.id.list_kecamatan);
        recyclerView = findViewById(R.id.list_kelurahan_desa);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        fetchbandara("", getidkecamatan);

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
                    int position = rv.getChildAdapterPosition(child);
                    Intent returnIntent = new Intent();
                    int getid = item.get(position).getId_kelurahan();
                    String tostr = Integer.toString(getid);
                    returnIntent.putExtra("pick_kelurahan", item.get(position).getKelurahan());
                    returnIntent.putExtra("pick_id", tostr);
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();


//                 Toast.makeText(getApplicationContext(), "Kecamatan : " + item.get(position).getKecamatan(), Toast.LENGTH_SHORT).show();
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

    public void fetchbandara(String key, int id){

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        retrofit2.Call<List<Kelurahan_model>> call = apiInterface.getKelurahan(key, getidkecamatan);
        call.enqueue(new retrofit2.Callback<List<Kelurahan_model>>() {
            @Override
            public void onResponse(retrofit2.Call<List<Kelurahan_model>> call, retrofit2.Response<List<Kelurahan_model>> response) {
                item = response.body();
                adapter = new Kelurahan_Adapter(item, Kelurahan_desa_act.this);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(retrofit2.Call<List<Kelurahan_model>> call, Throwable t) {
                Toast.makeText(Kelurahan_desa_act.this, "Error\n"+t.toString(), Toast.LENGTH_LONG).show();
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
                fetchbandara(query, getidkecamatan);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fetchbandara(newText, getidkecamatan);
                return false;
            }
        });
        return true;
    }
}
