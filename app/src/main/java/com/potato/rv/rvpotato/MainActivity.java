package com.potato.rv.rvpotato;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {

    private ImageButton jugar;
    private ImageButton ranking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this, "ca-app-pub-1008782396803681~2515099854");

        jugar = (ImageButton)findViewById(R.id.imagen_jugar);
        jugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pegar_patata(view);
            }
        });

        ranking = (ImageButton)findViewById(R.id.imagen_ranking);
        ranking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrir_ranking(view);
            }
        });

        AdView mAdView = (AdView) findViewById(R.id.anunciomain);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }

    public void pegar_patata(View v) {

        startActivity(new Intent(this, elegir_numero.class));
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }

    public void abrir_ranking(View v) {
        startActivity(new Intent(this, Activity_ranking.class));
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }
}