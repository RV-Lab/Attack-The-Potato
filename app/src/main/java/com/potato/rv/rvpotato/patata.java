package com.potato.rv.rvpotato;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class patata extends AppCompatActivity {

    ImageView patata;
    TextView contador_clicks;
    int contador=0;
    boolean encontrado = true;
    private Chronometer cronometro;
    private int maximo;
    private boolean entra_top = false;
    private String top="";
    private ImageButton retry;
    private ImageButton ranking;
    //prueba bbdd
    private DatabaseReference mibd;
    private ValueEventListener eventListener;
    private ArrayList<Jugador> jugadores = null;
    private InterstitialAd mInterstitialAd;
    private String nombre;
    int soundId;
    SoundPool sp;
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patata);

        //fuente
        patata = (ImageView) findViewById(R.id.patata);
        contador_clicks = (TextView)findViewById(R.id.text_contador);
        cronometro = (Chronometer) findViewById(R.id.cronometro);
        retry = (ImageButton)findViewById(R.id.retry);
        ranking = (ImageButton)findViewById(R.id.ranking);
        retry.setVisibility(View.GONE);
        ranking.setVisibility(View.GONE);
        MobileAds.initialize(this, "ca-app-pub-1008782396803681~2515099854");

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pegar_patata(view);
            }
        });


        ranking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrir_ranking(view);
            }
        });
        //
        String carpetaFuente = "fonts/impact.ttf";

// Obtenemos la id del TextView donde queremos cambiar la fuente
        TextView vistaFuente = (TextView) findViewById(R.id.text_contador);

// Cargamos la fuente
        Typeface fuente = Typeface.createFromAsset(getAssets(), carpetaFuente);

// Aplicamos la fuente
        vistaFuente.setTypeface(fuente);

        //recibir numero

        maximo = getIntent().getIntExtra("numero",0 );

        //ver en que parte de la bbdd tiene que guardar 10 o 100
        if(maximo==10){
            top = "Top10";
        }else{
            top = "Top100";
        }

        sp = new SoundPool(1, AudioManager.STREAM_MUSIC, 1);
        soundId = sp.load(this, R.raw.golpe, 1);


        AdView mAdView = (AdView) findViewById(R.id.anunciopatata);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-1008782396803681/5564661370");
        mInterstitialAd.loadAd(adRequest);

        patata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                contador = contador+1;
                actualizar_contador(contador);

                patata.this.animar();
                play_sound(view);

                if(contador==1){
                    cronometro.setBase(SystemClock.elapsedRealtime());
                    cronometro.start();
                }

                if(contador >= maximo*0.3 && contador < maximo*0.5){
                    patata.setImageResource(R.drawable.dedo);
                    contador_clicks.setTextColor(Color.parseColor("#D7DF01"));
                }
                if(contador >= maximo*0.5 && contador < maximo*0.7){
                    patata.setImageResource(R.drawable.triste);
                    contador_clicks.setTextColor(Color.parseColor("#FACC2E"));
                }
                if(contador >= maximo*0.7 && contador < maximo){
                    patata.setImageResource(R.drawable.llorandomorado);
                    contador_clicks.setTextColor(Color.parseColor("#FF8000"));
                }
                if(contador==maximo) {
                    terminado(view);
                    patata.setImageResource(R.drawable.muerta);
                    contador_clicks.setTextColor(Color.parseColor("#DF0101"));

                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    }
                }
            }
        });


    }


    public void animar(){
        this.patata.startAnimation(AnimationUtils.loadAnimation(this,R.anim.popup));
    }
    public void play_sound(View view) {

        sp.play(soundId,1,1,1,0,0);
    }

    public void actualizar_contador(int contador){
        contador_clicks.setText(""+contador);
    }

    public void parar_cronometro(){
        cronometro.stop();
    }

    public void terminado(View view){
        parar_cronometro();
        int elapsedMillis = (int) (SystemClock.elapsedRealtime() - cronometro.getBase());
/*
            Toast toast = Toast.makeText(getApplicationContext(),
                    "" + cronometro.getTimeElapsed() + "hola " + elapsedMillis, Toast.LENGTH_SHORT);
            toast.show();
*/
        patata.setClickable(false);
        dialogo(view,cronometro.getText().toString());

    }

    public void dialogo(View view,String tiempo) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(patata.this);
        builder.setCancelable(false);
        LayoutInflater inflater = patata.this.getLayoutInflater();
        view = inflater.inflate(R.layout.dialogo, null);
        builder.setView(view);
        final AlertDialog alert = builder.create();
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //boton
        final EditText nombre_record = view.findViewById(R.id.name);
        final TextView time = view.findViewById(R.id.time);
        time.setText("Time: " + tiempo);
        ImageButton ir = view.findViewById(R.id.boton_go);

        ir.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(nombre_record.getText().toString().isEmpty()){
                            nombre_record.setText("Unknown");
                        }
                        ConexionesBD.actualizarRanking(nombre_record.getText().toString(), top, cronometro.getText().toString(), getApplicationContext());

//                        if(ConexionesBD.actualizarRanking(nombre_record.getText().toString(), top, cronometro.getText().toString(), getApplicationContext())){
//                            Toast toast = Toast.makeText(getApplicationContext(), "YOU ARE ON THE TOP!!!", Toast.LENGTH_LONG);
//                            toast.show();
//                        }
                        ranking.setVisibility(View.VISIBLE);
                        retry.setVisibility(View.VISIBLE);
                        alert.dismiss();
                    }
                }
        );
        alert.show();
    }

/*
    public void dialogo (String tiempo){
        final EditText nombre_record = new EditText(this);
        final AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("Game Completed ");
        builder1.setMessage("Time: "+tiempo +"\nEnter your name: ");
        builder1.setView(nombre_record);
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "GO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        //coger datos editext y mandarlo
                        modificar_record(nombre_record.getText().toString());
                        ranking.setVisibility(View.VISIBLE);
                        retry.setVisibility(View.VISIBLE);


                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.setCanceledOnTouchOutside(false);
        alert11.show();

    }
*/
/*
    public void modificar_record(final String nombre){

        mibd = FirebaseDatabase.getInstance().getReference();
        jugadores = new ArrayList<Jugador>();
        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //limpio el arraylist de jugadores, si no se repite la lista
                jugadores.clear();


                for(DataSnapshot jugSnap : dataSnapshot.child(top).getChildren()) {
                    Jugador jugador = jugSnap.getValue(Jugador.class);
                    jugadores.add(jugador);
                }


                String tstring = cronometro.getText().toString();
                String[] parts = tstring.split(":");
                String part1 = parts[0];
                String part2 = parts[1];
                String part3 = parts[2];

                int min = Integer.parseInt(part1);
                int seg = Integer.parseInt(part2);
                int mil = Integer.parseInt(part3);

                int tiempoTotal = min*60*1000 + seg*1000 + mil;

                //saco la posicion del arraylist del mas lento
                int mayor=0;
                int pos=0;
                for(int i=0; i<jugadores.size(); i++){
                    if(jugadores.get(i).getTiempo() > mayor){
                        mayor = jugadores.get(i).getTiempo();
                        pos = i;
                    }
                }

                if( jugadores.get(pos).getTiempo() > tiempoTotal && encontrado==true){
                    String num= ""+(pos+1);
                    Jugador j = new Jugador(nombre,tiempoTotal);
                    dataSnapshot.child(top).getRef().child("" + num).setValue(j);
                    encontrado = false;
                    entra_top=true;
                }

                if (entra_top){
                    Toast toast = Toast.makeText(getApplicationContext(), "YOU ARE ON THE TOP!!!", Toast.LENGTH_LONG);
                    toast.show();
                    entra_top=false;
                }

                //metodo buscar pos mas lento, pos mas lento = tiempo nuevo record

                Log.e("", "onDataChange:" + dataSnapshot.getValue().toString());
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("", "Error!", databaseError.toException());
            }

        };
        mibd.addValueEventListener(eventListener);
        //mibd.onDisconnect();
    }
*/

    //botones
    public void pegar_patata(View v) {

        startActivity(new Intent(this, elegir_numero.class));
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }

    public void abrir_ranking(View v) {
        startActivity(new Intent(this, Activity_ranking.class));
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }
}
