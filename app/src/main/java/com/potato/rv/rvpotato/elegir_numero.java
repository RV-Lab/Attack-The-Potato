package com.potato.rv.rvpotato;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class elegir_numero extends AppCompatActivity {

    ImageButton boton_10;
    ImageButton boton_100;
    ImageButton boton_aleatorio;

    public static final String NUMERO = "numero";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elegir_numero);

        boton_10 = (ImageButton)findViewById(R.id.boton_10);
        boton_100 = (ImageButton)findViewById(R.id.boton_100);
        boton_aleatorio = (ImageButton)findViewById(R.id.boton_aleatorio);

        MobileAds.initialize(this, "ca-app-pub-1008782396803681~2515099854");
        String carpetaFuente = "fonts/impact.ttf";

// Obtenemos la id del TextView donde queremos cambiar la fuente
        TextView vistaFuente = (TextView) findViewById(R.id.textonumero);

// Cargamos la fuente
        Typeface fuente = Typeface.createFromAsset(getAssets(), carpetaFuente);

// Aplicamos la fuente
        vistaFuente.setTypeface(fuente);

        boton_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mandar_numero(view,10);
            }
        });
        boton_100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mandar_numero(view,100);
            }
        });
        boton_aleatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                elegir_aleatorio(view);
            }
        });

        AdView mAdView = (AdView) findViewById(R.id.anuncionumero);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }


    public void mandar_numero(View view, int numero_clicks) {
        Intent intent = new Intent(this, patata.class);
        intent.putExtra("numero",numero_clicks);
        startActivity(intent);
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }

    public void elegir_aleatorio(View view){
        Intent intent = new Intent(this, Juego_Aleatorio.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }
}
