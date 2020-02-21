package com.bimasakti.diskominfosan;

import android.content.Intent;
import android.os.Handler;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bimasakti.diskominfosan.retrofit.ApiClient;
import com.bimasakti.diskominfosan.retrofit.ApiInterface;
import com.bimasakti.diskominfosan.running_text.Running_text_model;
import com.bimasakti.diskominfosan.slider_package.ImageSlider_model;
import com.bimasakti.diskominfosan.slider_package.SlidingImage_Adapter;
import com.bimasakti.diskominfosan.webview_skpd.ELog_dinkes;
import com.bimasakti.diskominfosan.webview_skpd.Rkpd_webview;
import com.bimasakti.diskominfosan.webview_skpd.Siap_webview;
import com.bimasakti.diskominfosan.webview_skpd.Sielok_webview;
import com.bimasakti.diskominfosan.webview_skpd.Sim_pol_pp;
import com.bimasakti.diskominfosan.webview_skpd.Simencrang_webview;
import com.bimasakti.diskominfosan.webview_skpd.Simpeg_webview;
import com.synnapps.carouselview.CarouselView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;

public class Skpd_menu extends AppCompatActivity {
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
        setContentView(R.layout.skpd_menu);

        mPager = (ViewPager) findViewById(R.id.pager2);
        ActionBar menu = getSupportActionBar();
        menu.setDisplayShowHomeEnabled(true);
        menu.setDisplayHomeAsUpEnabled(true);

        slider_online();
        tv = (TextView) this.findViewById(R.id.running_text1);
        getrunningtext();
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

                Toast.makeText(Skpd_menu.this, "Error\n"+t.toString(), Toast.LENGTH_LONG).show();
                Log.e("TAG1", "gagal muat runningtext, cek koneksi anda! " + t.toString());
            }
        });
    }


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
                    mPager.setAdapter(new SlidingImage_Adapter(Skpd_menu.this,image));
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
                Toast.makeText(Skpd_menu.this, "Error\n"+t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void sielok(View view) {
        Intent i = new Intent(Skpd_menu.this, Sielok_webview.class);
        startActivity(i);
    }

    public void siap(View view) {
        Intent i = new Intent(Skpd_menu.this, Siap_webview.class);
        startActivity(i);
    }

    public void simpeg(View view) {
        Intent i = new Intent(Skpd_menu.this, Simpeg_webview.class);
        startActivity(i);
    }

    public void simencrang(View view) {
        Intent i = new Intent(Skpd_menu.this, Simencrang_webview.class);
        startActivity(i);
    }

    public void rkpd(View view) {
        Intent i = new Intent(Skpd_menu.this, Rkpd_webview.class);
        startActivity(i);
    }

    public void simpol(View view) {
        Intent i = new Intent(Skpd_menu.this, Sim_pol_pp.class);
        startActivity(i);
    }

    public void elog_dinkes(View view) {
        Intent i = new Intent(Skpd_menu.this, ELog_dinkes.class);
        startActivity(i);
    }
}
