package com.potato.rv.rvpotato;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private ImageButton jugar;
    private ImageButton ranking;
    private TextView countDown;
    private CountDownTimer countDownTimer;
    private Date eventDate, presentDate;
    private Calendar calendar;
    private long initialTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //NO DESCOMENTAR, SOBREPISA TODA LA BD, PUESTO PARA DIA DE LA SEMANA JUEVES
//        if(Calendar.DAY_OF_WEEK == 7){
//            ConexionesBD.actualizarRankingPermanent();
//        }

        MobileAds.initialize(this, "ca-app-pub-1008782396803681~2515099854");

        countDown = (TextView) findViewById(R.id.contadoract);

        cuentaAtras();




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

    public static Date variarFecha(Date fecha, int valor){
        if (valor==0) return fecha;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.DAY_OF_YEAR, valor);
        return calendar.getTime();
    }

    public void cuentaAtras(){
        String[] fecha = ConexionesBD.estimarClick().split("/");
        calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2018);
        calendar.set(Calendar.MONTH, Integer.parseInt(fecha[1]));
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(fecha[0]));
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 5);
        calendar.set(Calendar.SECOND, 0);

        eventDate = calendar.getTime();

        presentDate = new Date();

        long diff = eventDate.getTime() - presentDate.getTime();
        Log.d("dateEvent", String.valueOf(eventDate));
        Log.d("dateActual", String.valueOf(presentDate));
        Log.d("diff", String.valueOf(diff));

        long seconds =(diff/1000)%60;
        long minutes=(diff/(1000*60))%60;
        long hours=(diff/(1000*60*60))%24;
        long days=(diff/(1000*60*60*24))%365;

        initialTime = diff;

        countDownTimer = new CountDownTimer(initialTime, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

                int days = (int) ((millisUntilFinished / 1000) / 86400);
                int hours = (int) (((millisUntilFinished / 1000)
                        - (days * 86400)) / 3600);
                int minutes = (int) (((millisUntilFinished / 1000)
                        - (days * 86400) - (hours * 3600)) / 60);
                int seconds = (int) ((millisUntilFinished / 1000) % 60);

                String countDownText = String.format("%2d Dias %2d Hr %2d Min %2d Seg", days, hours, minutes, seconds);
                countDown.setText(countDownText);
            }

            @Override
            public void onFinish() {
                countDown.setText(DateUtils.formatElapsedTime(0));

            }

        }.start();
    }
}