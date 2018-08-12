package com.potato.rv.rvpotato;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;



public class TopAleatorio extends Fragment {
    private static final String TAGLOG = "firebase-db";
    private ListView lista_topaleatorio;
    /*private String nombre="";
    private String tiempo="";
    private String prueba="No se modifica";*/
    private DatabaseReference mibd;
    private ValueEventListener eventListener;
    private ArrayList<Jugador> jugadores = null;
    View v;


    public TopAleatorio() {
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
                for(DataSnapshot jugSnap : dataSnapshot.child("TopAleatorio").getChildren()) {
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
        v = inflater.inflate(R.layout.fragment_top_aleatorio, container, false);
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
        lista_topaleatorio = (ListView) v.findViewById(R.id.lista_topaleatorio);
        Miadapter adaptador = new Miadapter(this.getChildFragmentManager(), jugadores);
        lista_topaleatorio.setAdapter(adaptador);
    }
}
