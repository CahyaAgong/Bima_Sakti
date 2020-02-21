package com.bimasakti.diskominfosan.layanan_webview.rs;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.bimasakti.diskominfosan.R;

import java.util.ArrayList;

public class Rumah_sakit extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Adapter_rs adapter;
    private ArrayList<Model_rs> rs;

     WebView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rumah_sakit);

        addData();

        recyclerView = (RecyclerView) findViewById(R.id.rec_rs);
        view    = (WebView) findViewById(R.id.wv_rs);

        adapter = new Adapter_rs(rs);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Rumah_sakit.this);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(getApplicationContext(), new GestureDetector.SimpleOnGestureListener() {

                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });

            @Override
            public boolean onInterceptTouchEvent(final RecyclerView rv, MotionEvent e) {
                final View child = rv.findChildViewUnder(e.getX(), e.getY());
                if (child != null && gestureDetector.onTouchEvent(e)) {
                    final int position = rv.getChildAdapterPosition(child);

                    if (rs.get(position).getStatus().equals("1")){

                        final Dialog dialog = new Dialog(Rumah_sakit.this);
                        dialog.setContentView(R.layout.choose_rs_popup);

                        Button info     = (Button) dialog.findViewById(R.id.info_kami_btn);
                        Button hubungi  = (Button) dialog.findViewById(R.id.hub_kami_btn);

                        info.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int position = rv.getChildAdapterPosition(child);
                                dialog.dismiss();
                                recyclerView.setVisibility(v.GONE);
                                view.setVisibility(v.VISIBLE); view.getSettings().setJavaScriptEnabled(true);
                                view.setWebViewClient(new MyBrowser());
                                view.loadUrl(rs.get(position).getWv());

                            }
                        });

                        hubungi.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int position = rv.getChildAdapterPosition(child);
                                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", rs.get(position).getNotelp(), null));
                                startActivity(intent);
                            }
                        });

                        dialog.show();
                    } else{
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", rs.get(position).getNotelp(), null));
                        startActivity(intent);
                    }
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

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //ketika disentuh tombol back
        if ((keyCode == KeyEvent.KEYCODE_BACK) && view.canGoBack()) {
            view.goBack(); //method goback(),untuk kembali ke halaman sebelumnya
            return true;
        }
        // Jika tidak ada halaman yang pernah dibuka
        // maka akan keluar dari activity (tutup aplikasi)
        return super.onKeyDown(keyCode, event);
    }

    void addData(){
        rs = new ArrayList<>();
        rs.add(new Model_rs("1", "RSUD SEKARWANGI", "Jl. Nasional III No.123, Cibadak, Sukabumi, Jawa Barat, 43351", "0266531261", "1", "https://rsudsekarwangi.sukabumikab.go.id/"));
        rs.add(new Model_rs("2", "RSUD JAMPANG KULON", "Jl. Cibarusan No.1, Tanjung, Jampang Kulon, Sukabumi, Jawa Barat 43178", "0266490009", "1", ""));
        rs.add(new Model_rs("3", "RSUD PELABUHAN RATU", "Jl. Jend Ahmad Yani No.2, Citepus, Pelabuhan Ratu, Sukabumi, Jawa Barat, 43364", "0822 1306 4730", "1", "https://www.rsudpalabuhanratu.sukabumikab.go.id/index.php?module=home"));
        rs.add(new Model_rs("4", "RS Kartika", "Jl. Jendral A. Yani  No. 18 Kota Sukabumi - Jawa Barat", "02666250902", "2",""));
        rs.add(new Model_rs("5", "RS Hermina Sukabumi", "Jl. Raya Sukaraja RT. 003/03, Sukaraja Kota Sukabumi - Jawa Barat", " 02666252525", "2",""));
        rs.add(new Model_rs("6", "RSI Sukabumi", "Jl. Cibolang, Cisaat Kota Sukabumi - Jawa Barat", "026661115", "2",""));
        rs.add(new Model_rs("7", "RSI Assyifa", "Jl. Jend. Sudirman No. 3 Kota Sukabumi - Jawa Barat", "0266222663", "2",""));
        rs.add(new Model_rs("8", "RS Secapa Polri", "Jl. Bhayangkara No.166 Kota Sukabumi - Jawa Barat", "0266229207", "2",""));
        rs.add(new Model_rs("9", "RS Syamsudin SH", "Jl. Rumah Sakit No. 1 Kota Sukabumi - Jawa Barat", "0266225180", "2",""));
        rs.add(new Model_rs("10", "RS Bhakti Medicare", "Jl. Raya Siliwangi No. 186 B Cicurug Kota Sukabumi - Jawa Barat", "0266731555", "2",""));
        rs.add(new Model_rs("11", "RS Ridho Galih", "Jl Gudang Kota Sukabumi - Jawa Barat", "0266221983", "2",""));
        rs.add(new Model_rs("12", "RS Betha Medika", "Jl. Raya Cikukulu, Cikukulu Kabupaten Sukabumi", "0226224128", "2",""));
    }
}
