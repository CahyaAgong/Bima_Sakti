package com.bimasakti.diskominfosan;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bimasakti.diskominfosan.retrofit.ApiInterface;

public class Splash extends AppCompatActivity {

    private Animation top_button;
    private Animation stb;
    private Animation btn;

    private TextView headertitle, subtitle;
    private ImageView ic_cards;
    private Button btn_next_course;
    private ProgressBar pre;
    private ApiInterface apiInterface;
    private static int LamaTampilSplash = 6000;
    ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        top_button = AnimationUtils.loadAnimation(this, R.anim.top_button);
        stb = AnimationUtils.loadAnimation(this, R.anim.stb);
        btn = AnimationUtils.loadAnimation(this, R.anim.btn);

        headertitle = (TextView) findViewById(R.id.headertitle);
        subtitle = (TextView) findViewById(R.id.subtitle);

        ic_cards = (ImageView) findViewById(R.id.ic_cards);
        btn_next_course = (Button) findViewById(R.id.btn_next_course);
        pre = (ProgressBar) findViewById(R.id.load);

        //set animasi
        headertitle.startAnimation(top_button);
        subtitle.startAnimation(top_button);

        ic_cards.startAnimation(stb);
        pre.startAnimation(btn);

//        btn_next_course.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent (Splash.this, MainActivity.class);
//                startActivity(i);
//            }
//        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // to do auto generated stub
                Intent apasih = new Intent(Splash.this, MainActivity.class);
                startActivity(apasih);

                // jeda setelah splashscren
            }

            private void selesai(){
                //auto
            }
        },LamaTampilSplash);
    }
}
