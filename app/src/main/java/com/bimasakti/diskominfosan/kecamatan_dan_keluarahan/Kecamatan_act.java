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
import com.bimasakti.diskominfosan.kecamatan_dan_keluarahan.Adapter.Kecamatan_Adapter;
import com.bimasakti.diskominfosan.kecamatan_dan_keluarahan.Model.Kecamatan_model;
import com.bimasakti.diskominfosan.retrofit.ApiClient;
import com.bimasakti.diskominfosan.retrofit.ApiInterface;

import java.util.List;

public class Kecamatan_act extends AppCompatActivity {

    //with retrofit
    private Kecamatan_Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private List<Kecamatan_model> item;
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kecamatan_act);

        //with retrofit
        recyclerView = (RecyclerView) findViewById(R.id.list_kecamatan);
        recyclerView = findViewById(R.id.list_kecamatan);
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
                    int position = rv.getChildAdapterPosition(child);
                    Intent returnIntent = new Intent();
                    int getid = item.get(position).getId_kecamatan();
                    String tostr = Integer.toString(getid);
                    returnIntent.putExtra("pick_kecamatan", item.get(position).getKecamatan());
                    returnIntent.putExtra("pick_id", tostr);
                    setResult(Activity.RESULT_FIRST_USER, returnIntent);
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

    public void fetchbandara(String key){

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        retrofit2.Call<List<Kecamatan_model>> call = apiInterface.getKecamatan(key);
        call.enqueue(new retrofit2.Callback<List<Kecamatan_model>>() {
            @Override
            public void onResponse(retrofit2.Call<List<Kecamatan_model>> call, retrofit2.Response<List<Kecamatan_model>> response) {
                item = response.body();
                adapter = new Kecamatan_Adapter(item, Kecamatan_act.this);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(retrofit2.Call<List<Kecamatan_model>> call, Throwable t) {
//                Toast.makeText(Kecamatan_act.this, "Error\n"+t.toString(), Toast.LENGTH_LONG).show();
                Toast.makeText(Kecamatan_act.this, "Cek Koneksi Anda", Toast.LENGTH_LONG).show();
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
