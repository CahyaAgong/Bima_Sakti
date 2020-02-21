package com.bimasakti.diskominfosan;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bimasakti.diskominfosan.User.Edit_profil.Edit_password;
import com.bimasakti.diskominfosan.User.Edit_profil.Profile_Screen;
import com.bimasakti.diskominfosan.User.Edit_profil.Update_address;
import com.bimasakti.diskominfosan.User.Edit_profil.Update_profile_act;
import com.bimasakti.diskominfosan.User.ModelUser;
import com.bimasakti.diskominfosan.e_ambulance.Ambulance_locate;
import com.bimasakti.diskominfosan.e_damkar.damkar_locate;
import com.bimasakti.diskominfosan.layanan_webview.E_lapor_webview;
import com.bimasakti.diskominfosan.layanan_webview.Jdih_kabsi_wv;
import com.bimasakti.diskominfosan.layanan_webview.Kami_tv;
import com.bimasakti.diskominfosan.retrofit.ApiClient;
import com.bimasakti.diskominfosan.retrofit.ApiInterface;
import com.bimasakti.diskominfosan.running_text.Running_text_model;
import com.bimasakti.diskominfosan.slider_package.ImageSlider_model;
import com.bimasakti.diskominfosan.slider_package.SlidingImage_Adapter;
import com.bimasakti.diskominfosan.store_session.SharedPrefManager;
import com.bimasakti.diskominfosan.tablayout2.form_diskusi;
import com.bimasakti.diskominfosan.wisataku.Wisataku_act;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private final String URL = "https://kamismart.sukabumikab.go.id/bimasakti/bima_s4kti/";

    CarouselView carouselView;
    int[] sampleImages = {R.drawable.banner1, R.drawable.banner2, R.drawable.image_slider_1, R.drawable.image_slider_3, R.drawable.image_slider_4};

    private TextView tv, nama_user;
    private CircleImageView foto_profil;

    ApiInterface apiInterface;
    private List<ImageSlider_model> item;
    private ArrayList<String> image;

    private static ViewPager mPager;
    private static int NUM_PAGES = 0;
    private static int currentPage = 0;
    ImageView setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPager = (ViewPager) findViewById(R.id.pager);
        slider_online();
        tv = (TextView) this.findViewById(R.id.running_text1);
        getrunningtext();

        nama_user = (TextView) this.findViewById(R.id.txt_nama_user);
        foto_profil = findViewById(R.id.profile_image);
        setting = findViewById(R.id.set);

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Anda Ingin Mengupdate Profil ?");
                alert.setIcon(R.drawable.pencil_24px)
                        .setPositiveButton("Update Password ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(MainActivity.this, Edit_password.class);
                            startActivity(i);
                            }
                        })
                        .setNegativeButton("Update Alamat", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(MainActivity.this, Update_address.class);
                                startActivity(i);
                            }
                        });
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
            }
        });

        ModelUser user = SharedPrefManager.getInstance(this).getUser();
        nama_user.setText(user.getNama_lengkap());

        Picasso.get()
                .load(URL + user.getFoto())
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .into(foto_profil);

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (task.isSuccessful()){
                            String token = task.getResult().getToken();
                            Log.e("TOKEN NYA IS : ", token);

                            apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
                            Call<ResponseBody> call = apiInterface.createToken(token, Build.BRAND + " " + Build.MODEL);

                            call.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    try {
                                        String s = response.body().string();
                                        Log.d("Status : ", "Token Saved!");
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });

                        } else{
                            Log.e("Gagal Get Token : ", task.getException().getMessage());
                        }
                    }
                });

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(this, Login_act.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
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

                Toast.makeText(MainActivity.this, "Error\n"+t.toString(), Toast.LENGTH_LONG).show();
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
                    mPager.setAdapter(new SlidingImage_Adapter(MainActivity.this,image));
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
                Toast.makeText(MainActivity.this, "Error\n"+t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void layanan(View view) {
        Intent i = new Intent(MainActivity.this, layanan_menu.class);
        startActivity(i);
    }

    public void diskusi(View view) {
        Intent i = new Intent(MainActivity.this, form_diskusi.class);
        startActivity(i);
    }

    public void infokami(View view) {
        Intent i = new Intent(MainActivity.this, Webview.class);
        startActivity(i);
    }

    public void skpd(View view) {
        Intent i = new Intent(MainActivity.this, Skpd_menu.class);
        startActivity(i);
    }

    public void ambulance(View view) {
        Intent i = new Intent(MainActivity.this, Kami_tv.class);
        startActivity(i);
    }

    public void damkar(View view) {
        Intent i = new Intent(MainActivity.this, form_diskusi.class);
        startActivity(i);
//        final Dialog dialog = new Dialog(MainActivity.this);
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

    public void praja(View view) {
        Intent i = new Intent(MainActivity.this, damkar_locate.class);
        startActivity(i);
    }

    public void cek_pbb(View view) {
        Intent i = new Intent(MainActivity.this, Ambulance_locate.class);
        startActivity(i);
    }

    public void wisataku(View view) {
        Intent i = new Intent(MainActivity.this, Wisataku_act.class);
        startActivity(i);
    }

    public void logout(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Anda Yakin Ingin Logout");
        alert.setCancelable(false)
                .setIcon(R.drawable.danger)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPrefManager.getInstance(getApplication()).clear();
                        Intent i = new Intent(MainActivity.this, Login_act.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }

    public void foto_click(View view) {
        Intent i = new Intent(MainActivity.this, Update_profile_act.class);
        startActivity(i);
    }

    public void profil(View view) {
        Intent i = new Intent(MainActivity.this, Profile_Screen.class);
        startActivity(i);
    }
}
