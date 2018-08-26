package com.potato.rv.rvpotato;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;


public class Top100 extends Fragment{
    private static final String TAGLOG = "firebase-db";
    private ListView lista_top100;
    /*private String nombre="";
    private String tiempo="";
    private String prueba="No se modifica";*/
    private DatabaseReference mibd;
    private ValueEventListener eventListener;
    private ArrayList<Jugador> jugadores = null;
    View v;


    public Top100() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mibd = FirebaseDatabase.getInstance().getReference();
        jugadores = new ArrayList<Jugador>();

        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //limpio el arraylist de jugadores, si no se repite la lista
                jugadores.clear();
                for(DataSnapshot jugSnap : dataSnapshot.child("Top100").getChildren()) {
                    Jugador jugador = jugSnap.getValue(Jugador.class);
                    jugadores.add(jugador);
                }
                //metodo buscar pos mas lento, pos mas lento = tiempo nuevo record
                comprobar(jugadores);
                Log.e(TAGLOG, "onDataChange:" + dataSnapshot.getValue().toString());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAGLOG, "Error!", databaseError.toException());
            }
        };
        mibd.addValueEventListener(eventListener);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_top100, container, false);

        MobileAds.initialize(getActivity(), "ca-app-pub-1008782396803681~2515099854");
        AdView mAdView = (AdView) v.findViewById(R.id.anuncioTop100);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        return v;
    }


    public void comprobar(ArrayList <Jugador> jugadores){
        //ordenar
        Collections.sort(jugadores);

            /*
            Toast toast = Toast.makeText(getApplicationContext(),
                    "entrado", Toast.LENGTH_SHORT);
            toast.show();
            */
        lista_top100 = (ListView) v.findViewById(R.id.lista_top100);
        Miadapter adaptador = new Miadapter(this.getChildFragmentManager(), jugadores);
        lista_top100.setAdapter(adaptador);
    }
}

