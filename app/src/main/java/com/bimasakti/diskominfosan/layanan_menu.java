package com.bimasakti.diskominfosan;

import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bimasakti.diskominfosan.e_ambulance.Ambulance_locate;
import com.bimasakti.diskominfosan.e_damkar.damkar_locate;
import com.bimasakti.diskominfosan.layanan_webview.Amdal_webview;
import com.bimasakti.diskominfosan.layanan_webview.Bnn_wv;
import com.bimasakti.diskominfosan.layanan_webview.Bpjs_kerja;
import com.bimasakti.diskominfosan.layanan_webview.Bpjs_kes;
import com.bimasakti.diskominfosan.layanan_webview.Cart_kuning;
import com.bimasakti.diskominfosan.layanan_webview.Dpmptsp_webview;
import com.bimasakti.diskominfosan.layanan_webview.E_ktp;
import com.bimasakti.diskominfosan.layanan_webview.E_lapor_webview;
import com.bimasakti.diskominfosan.layanan_webview.Hallo_praja_wv;
import com.bimasakti.diskominfosan.layanan_webview.IPKB_webview;
import com.bimasakti.diskominfosan.layanan_webview.Jdih_kabsi_wv;
import com.bimasakti.diskominfosan.layanan_webview.Lpse_webview;
import com.bimasakti.diskominfosan.layanan_webview.Ppid_webview;
import com.bimasakti.diskominfosan.layanan_webview.Shohib_webview;
import com.bimasakti.diskominfosan.layanan_webview.Spt_pajak_wv;
import com.bimasakti.diskominfosan.layanan_webview.Suhunan_webview;
import com.bimasakti.diskominfosan.layanan_webview.Umkm_webview;
import com.bimasakti.diskominfosan.layanan_webview.rs.Rumah_sakit;
import com.bimasakti.diskominfosan.retrofit.ApiClient;
import com.bimasakti.diskominfosan.retrofit.ApiInterface;
import com.bimasakti.diskominfosan.running_text.Running_text_model;
import com.bimasakti.diskominfosan.slider_package.ImageSlider_model;
import com.bimasakti.diskominfosan.slider_package.SlidingImage_Adapter;
import com.synnapps.carouselview.CarouselView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;

public class layanan_menu extends AppCompatActivity {
    CarouselView carouselView;
    int[] sampleImages = {R.drawable.banner1, R.drawable.banner2, R.drawable.image_slider_2, R.drawable.image_slider_3, R.drawable.image_slider_4};

    private TextView tv;
    ApiInterface apiInterface;


    private List<ImageSlider_model> item;
    private ArrayList<String> image;
    private static ViewPager mPager;
    private static int NUM_PAGES = 0;
    private static int currentPage = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layanan_menu);

        mPager = (ViewPager) findViewById(R.id.pager1);
        ActionBar menu = getSupportActionBar();
        menu.setDisplayShowHomeEnabled(true);
        menu.setDisplayHomeAsUpEnabled(true);

        slider_online();
        tv = (TextView) this.findViewById(R.id.running_text1);
        getrunningtext();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_main,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.toolbar_main:
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.example.com"));
//
//                startActivity(browserIntent);
//
//                return true;

        }
        return false;
    }

    public void getrunningtext(){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<List<Running_text_model>> call = apiInterface.getRunning();
        call.enqueue(new Callback<List<Running_text_model>>() {
            @Override
            public void onResponse(Call<List<Running_text_model>> call, retrofit2.Response<List<Running_text_model>> response) {
                List<Running_text_model> rt = response.body();
                String Runt = rt.get(1).getRunning();
                Log.e("TAG1", Runt);
                tv.setText(Runt);
                tv.setSelected(true);
            }

            @Override
            public void onFailure(Call<List<Running_text_model>> call, Throwable t) {

                Toast.makeText(layanan_menu.this, "Error\n"+t.toString(), Toast.LENGTH_LONG).show();
                Log.e("TAG1", "gagal muat runningtext, cek koneksi anda! " + t.toString());
            }
        });
    }

//    public void s1(){
//        String[] uri;
//        uri = new String[] {"http://kamismart.sukabumikab.go.id/bmadmin/images/gallery/gallery-1-2019-09-16_074400.jpg",
//                "http://kamismart.sukabumikab.go.id/bmadmin/images/gallery/gallery-2-2019-10-08_222743.jpg",
//                "http://kamismart.sukabumikab.go.id/bmadmin/images/gallery/gallery-3-2019-10-09_074525.png",
//                "http://kamismart.sukabumikab.go.id/bmadmin/images/gallery/gallery-4-2019-10-09_080125.jpg"};
//
//        mPager = (ViewPager) findViewById(R.id.pager1);
//        mPager.setAdapter(new SlidingImage_Adapter(layanan_menu.this,uri));
//
////                CirclePageIndicator indicator1 = (CirclePageIndicator)
////                        findViewById(R.id.indicator1);
////
////                indicator1.setViewPager(mPager);
////
////                final float density = getResources().getDisplayMetrics().density;
////
////                //Set circle indicator radius
////                indicator1.setRadius(5 * density);
//
//        NUM_PAGES = uri.length;
//
//        // Auto start of viewpager
//        final Handler handler = new Handler();
//        final Runnable Update = new Runnable() {
//            public void run() {
//                if (currentPage == NUM_PAGES) {
//                    currentPage = 0;
//                }
//                mPager.setCurrentItem(currentPage++, true);
//            }
//        };
//        Timer swipeTimer = new Timer();
//        swipeTimer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                handler.post(Update);
//            }
//        }, 7000, 7000);
//
//        // Pager listener over indicator
////                indicator1.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
////
////                    @Override
////                    public void onPageSelected(int position) {
////                        currentPage = position;
////
////                    }
////
////                    @Override
////                    public void onPageScrolled(int pos, float arg1, int arg2) {
////
////                    }
////
////                    @Override
////                    public void onPageScrollStateChanged(int pos) {
////
////                    }
////                });
//
//    }

    public void slider_online(){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        retrofit2.Call<List<ImageSlider_model>> call = apiInterface.getSlider();
        call.enqueue(new retrofit2.Callback<List<ImageSlider_model>>() {
            @Override
            public void onResponse(retrofit2.Call<List<ImageSlider_model>> call, retrofit2.Response<List<ImageSlider_model>> response) {

                item = response.body();
                image = new ArrayList<>();
                for (int i = 0; i < item.size(); i++) {
                    image.add("http://kamismart.sukabumikab.go.id/bmadmin/images/gallery/"+item.get(i).getFile());
                    mPager.setAdapter(new SlidingImage_Adapter(layanan_menu.this,image));
                    NUM_PAGES = image.size();
                }
//                CirclePageIndicator indicator1 = (CirclePageIndicator)
//                        findViewById(R.id.indicator1);
//
//                indicator1.setViewPager(mPager);
//
//                final float density = getResources().getDisplayMetrics().density;
//
//                //Set circle indicator radius
//                indicator1.setRadius(5 * density);


                // Auto start of viewpager
                final Handler handler = new Handler();
                final Runnable Update = new Runnable() {
                    public void run() {
                        if (currentPage == NUM_PAGES) {
                            currentPage = 0;
                        }
                        mPager.setCurrentItem(currentPage++, true);
                    }
                };
                Timer swipeTimer = new Timer();
                swipeTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.post(Update);
                    }
                }, 7000, 7000);

                // Pager listener over indicator
//                indicator1.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//
//                    @Override
//                    public void onPageSelected(int position) {
//                        currentPage = position;
//
//                    }
//
//                    @Override
//                    public void onPageScrolled(int pos, float arg1, int arg2) {
//
//                    }
//
//                    @Override
//                    public void onPageScrollStateChanged(int pos) {
//
//                    }
//                });

            }

            @Override
            public void onFailure(retrofit2.Call<List<ImageSlider_model>> call, Throwable t) {
                Toast.makeText(layanan_menu.this, "Error\n"+t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void rs(View view) {
        Intent i = new Intent(layanan_menu.this, Jdih_kabsi_wv.class);
        startActivity(i);
//        final Dialog dialog = new Dialog(layanan_menu.this);
//        dialog.setContentView(R.layout.popup_maintenance);
//
//        Button button  = (Button) dialog.findViewById(R.id.tutup_popup_under_main);
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
//        dialog.show();
    }

    public void ajuan(View view) {
        Intent i = new Intent(layanan_menu.this, Rumah_sakit.class);
        startActivity(i);
    }

    public void bpjs_kes(View view) {
        Intent i = new Intent(layanan_menu.this, Bpjs_kes.class);
        startActivity(i);
    }

    public void amdal(View view) {
        Intent i = new Intent(layanan_menu.this, Amdal_webview.class);
        startActivity(i);
    }

    public void shohib(View view) {
        Intent i = new Intent(layanan_menu.this, Shohib_webview.class);
        startActivity(i);
    }

    public void kamitv(View view) {
        Intent i = new Intent(layanan_menu.this, Bnn_wv.class);
        startActivity(i);
    }

    public void umkm(View view) {
        Intent i = new Intent(layanan_menu.this, Spt_pajak_wv.class);
        startActivity(i);
    }

    public void lpse(View view) {
        Intent i = new Intent(layanan_menu.this, Lpse_webview.class);
        startActivity(i);
    }

    public void suhunan(View view) {
        Intent i = new Intent(layanan_menu.this, Suhunan_webview.class);
        startActivity(i);
    }

    public void h_praja(View view) {
        Intent i = new Intent(layanan_menu.this, Hallo_praja_wv.class);
        startActivity(i);
    }

    public void jdih_kab(View view) {
        Intent i = new Intent(layanan_menu.this, Amdal_webview.class);
        startActivity(i);
//        Intent i = new Intent(layanan_menu.this, Jdih_kabsi_wv.class);
////        startActivity(i);
//        final Dialog dialog = new Dialog(layanan_menu.this);
//        dialog.setContentView(R.layout.popup_maintenance);
//
//        Button button  = (Button) dialog.findViewById(R.id.tutup_popup_under_main);
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
//        dialog.show();
    }

    public void ppid(View view) {
        Intent i = new Intent(layanan_menu.this, Ppid_webview.class);
        startActivity(i);
    }

    public void ektp(View view) {
        Intent i = new Intent(layanan_menu.this, E_ktp.class);
        startActivity(i);
    }

    public void bpjs_ker(View view) {
        Intent i = new Intent(layanan_menu.this, Bpjs_kerja.class);
        startActivity(i);
    }

    public void rcl(View view) {
        Intent i = new Intent(layanan_menu.this, Umkm_webview.class);
        startActivity(i);
    }

    public void sptpajak(View view) {
        Intent i = new Intent(layanan_menu.this, Shohib_webview.class);
        startActivity(i);
//        final Dialog dialog = new Dialog(layanan_menu.this);
//        dialog.setContentView(R.layout.popup_maintenance);
//
//        Button button  = (Button) dialog.findViewById(R.id.tutup_popup_under_main);
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
//        dialog.show();
    }

    public void dmpptsp(View view) {
        Intent i = new Intent(layanan_menu.this, Dpmptsp_webview.class);
        startActivity(i);
    }

    public void elapor(View view) {
        Intent i = new Intent(layanan_menu.this, E_lapor_webview.class);
        startActivity(i);
    }

    public void cart_kuning(View view) {
        Intent i = new Intent(layanan_menu.this, Cart_kuning.class);
        startActivity(i);
    }

    public void ipkb(View view) {
        Intent i = new Intent(layanan_menu.this, IPKB_webview.class);
        startActivity(i);
    }
}
