package com.bimasakti.diskominfosan;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.bimasakti.diskominfosan.e_ambulance.Ambulance_locate;

public class E_ambulance extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e_ambulance);
    }

    public void locate_am(View view) {
        Intent i = new Intent(E_ambulance.this, Ambulance_locate.class);
        startActivity(i);
    }
}
