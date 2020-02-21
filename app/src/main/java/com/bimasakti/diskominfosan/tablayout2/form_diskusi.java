package com.bimasakti.diskominfosan.tablayout2;

import android.os.Bundle;
import android.os.Handler;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.widget.Toast;

import com.bimasakti.diskominfosan.R;
import com.bimasakti.diskominfosan.retrofit.ApiClient;
import com.bimasakti.diskominfosan.retrofit.ApiInterface;
import com.bimasakti.diskominfosan.slider_package.ImageSlider_model;
import com.bimasakti.diskominfosan.slider_package.SlidingImage_Adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class form_diskusi extends AppCompatActivity {

    Toolbar toolbar;
    ViewPager pager;
    ViewPagerAdapter2 adapter;
    SlidingTabLayout2 tabs;
    String Titles[]={"HOME","POLITIK", "KESEHATAN", "PENDIDIKAN", "DESAKU", "JUAL BELI", "LOKER", "PEMKAB"};
    int Numboftabs = 8;


    ApiInterface apiInterface;
    private List<ImageSlider_model> item;
    private ArrayList<String> image;

    private static ViewPager mPager;
    private static int NUM_PAGES = 0;
    private static int currentPage = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_diskusi);
        // Creating The Toolbar and setting it as the Toolbar for the activity


        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        ActionBar menu = getSupportActionBar();
        menu.setDisplayShowHomeEnabled(true);
        menu.setDisplayHomeAsUpEnabled(true);

        mPager = (ViewPager) findViewById(R.id.pager4);

        slider_online();
        // Creating The ViewPagerAdapter2 and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new ViewPagerAdapter2(getSupportFragmentManager(),Titles,Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager_diskusi);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout2) findViewById(R.id.tabs_diskusi);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout2.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.colorPrimary);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);


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
                    mPager.setAdapter(new SlidingImage_Adapter(form_diskusi.this,image));
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
                Toast.makeText(form_diskusi.this, "Error\n"+t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
