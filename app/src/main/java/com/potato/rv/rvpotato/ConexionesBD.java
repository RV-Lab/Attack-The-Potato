package com.potato.rv.rvpotato;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class ConexionesBD {
    private static final String TAGLOG = "firebase-db";
    private static DatabaseReference mibd;
    private static ValueEventListener eventListener;
    private static ArrayList<Jugador> jugadores = null;
    private static boolean encontrado = true;
    private static boolean entra_top = false;
    private static String top = "Top10";
    public static int n = 10;

//      UNICA FORMA QUE FUNCIONA, PERO NO ME GUSTA NADA
    public static void visualizarRankingTotal(final ListView listView, final String acceso){
        mibd = FirebaseDatabase.getInstance().getReference();
        jugadores = new ArrayList<Jugador>();

        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                jugadores.clear();
                top = "Top10";
                for (DataSnapshot jugSanp : dataSnapshot.child(top).getChildren()){
                    Jugador jugador = jugSanp.getValue(Jugador.class);
                    jugadores.add(jugador);
                }
                top = "Top100";
                for (DataSnapshot jugSanp : dataSnapshot.child(top).getChildren()){
                    Jugador jugador = jugSanp.getValue(Jugador.class);
                    jugadores.add(jugador);
                }
                top = "TopAleatorio";
                for (DataSnapshot jugSanp : dataSnapshot.child(top).getChildren()){
                    Jugador jugador = jugSanp.getValue(Jugador.class);
                    jugadores.add(jugador);
                }

                switch (acceso) {
                    case "Top10":
                        visualizarRankinT10(listView, jugadores);
                        break;
                    case "Top100":
                        visualizarRankinT100(listView, jugadores);
                        break;
                    default:
                        visualizarRankinTA(listView, jugadores);
                        break;
                }
                Log.e(TAGLOG, "onDataChange:" + dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAGLOG, "Error!", databaseError.toException());
            }
        };
        mibd.addValueEventListener(eventListener);
    }

    private static void visualizarRankinT10(ListView listView, ArrayList<Jugador> jugadoress){
        ArrayList<Jugador> jugT = null;
        jugT = new ArrayList<Jugador>();
        int j = n;
        for(int i = 0; i<j; i++){
            jugT.add(jugadoress.get(i));
        }
        Collections.sort(jugT);
        Miadapter miadapter = new Miadapter(jugT);
        listView.setAdapter(miadapter);
    }


    private static void visualizarRankinT100(ListView listView, ArrayList<Jugador> jugadoress){
        ArrayList<Jugador> jugT = null;
        jugT = new ArrayList<Jugador>();
        int j = n;
        j = j + j;
        for(int i = 10; i<j; i++){
            jugT.add(jugadoress.get(i));
        }
        Collections.sort(jugT);
        Miadapter miadapter = new Miadapter(jugT);
        listView.setAdapter(miadapter);
    }


    private static void visualizarRankinTA(ListView listView, ArrayList<Jugador> jugadoress){
        ArrayList<Jugador> jugT = null;
        jugT = new ArrayList<Jugador>();
        int j = n;
        j = j + j + j;
        for(int i = 20; i<j; i++){
            jugT.add(jugadoress.get(i));
        }
        Collections.sort(jugT);
        Miadapter miadapter = new Miadapter(jugT);
        listView.setAdapter(miadapter);
    }


    public static void actualizarRanking(final String nombre, final String top, final String tiempo, final Context applicationContext){
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


                String[] parts = tiempo.split(":");
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

                if( jugadores.get(pos).getTiempo() > tiempoTotal && encontrado){
                    String num= ""+(pos+1);
                    Jugador j = new Jugador(nombre,tiempoTotal);
                    dataSnapshot.child(top).getRef().child("" + num).setValue(j);
                    encontrado = false;
                    entra_top = true;
                }

                if (entra_top){
                    Toast toast = Toast.makeText(applicationContext, "YOU ARE ON THE TOP!!!", Toast.LENGTH_LONG);
                    toast.show();
                    entra_top = false;
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
    }

//
//    NO FUNCIONA!!!!
//    public static void accesoVR(final ListView listView, final int n){
//        switch (n){
//            case 1:
//                visualizarRankinT10(listView);
//                break;
//
//            case 2:
//                visualizarRankinT100(listView);
//                break;
//
//            case 3:
//                visualizarRankinTA(listView);
//                break;
//        }
//    }
//
//    public static void visualizarRankinT10(final ListView listView){
//        mibd = FirebaseDatabase.getInstance().getReference();
//        jugadores = new ArrayList<Jugador>();
//
//        eventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                jugadores.clear();
//                top = "Top10";
//                for (DataSnapshot jugSanp : dataSnapshot.child(top).getChildren()){
//                    Jugador jugador = jugSanp.getValue(Jugador.class);
//                    jugadores.add(jugador);
//                }
//                Collections.sort(jugadores);
//                Miadapter miadapter = new Miadapter(jugadores);
//                listView.setAdapter(miadapter);
//                Log.e(TAGLOG, "onDataChange:" + dataSnapshot.getValue().toString());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Log.e(TAGLOG, "Error!", databaseError.toException());
//            }
//        };
//        mibd.addValueEventListener(eventListener);
//    }
//
//
//    public static void visualizarRankinT100(final ListView listView){
//        mibd = FirebaseDatabase.getInstance().getReference();
//        jugadores = new ArrayList<Jugador>();
//
//        eventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                jugadores.clear();
//                top = "Top100";
//                for (DataSnapshot jugSanp : dataSnapshot.child(top).getChildren()){
//                    Jugador jugador = jugSanp.getValue(Jugador.class);
//                    jugadores.add(jugador);
//                }
//                Collections.sort(jugadores);
//                Miadapter miadapter = new Miadapter(jugadores);
//                listView.setAdapter(miadapter);
//                Log.e(TAGLOG, "onDataChange:" + dataSnapshot.getValue().toString());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Log.e(TAGLOG, "Error!", databaseError.toException());
//            }
//        };
//        mibd.addValueEventListener(eventListener);
//    }
//
//
//    public static void visualizarRankinTA(final ListView listView){
//        mibd = FirebaseDatabase.getInstance().getReference();
//        jugadores = new ArrayList<Jugador>();
//
//        eventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                jugadores.clear();
//                top = "TopAleatorio";
//                for (DataSnapshot jugSanp : dataSnapshot.child(top).getChildren()){
//                    Jugador jugador = jugSanp.getValue(Jugador.class);
//                    jugadores.add(jugador);
//                }
//                Collections.sort(jugadores);
//                Miadapter miadapter = new Miadapter(jugadores);
//                listView.setAdapter(miadapter);
//                Log.e(TAGLOG, "onDataChange:" + dataSnapshot.getValue().toString());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Log.e(TAGLOG, "Error!", databaseError.toException());
//            }
//        };
//        mibd.addValueEventListener(eventListener);
//    }


    //      NO DUNCIONA!!!!!
//    public static void visualizarRanking(final ListView listView, final String top){
//        mibd = FirebaseDatabase.getInstance().getReference();
//        jugadores = new ArrayList<Jugador>();
//
//        eventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                jugadores.clear();
//                for (DataSnapshot jugSanp : dataSnapshot.child(top).getChildren()){
//                    Jugador jugador = jugSanp.getValue(Jugador.class);
//                    jugadores.add(jugador);
//                }
//                Collections.sort(jugadores);
//                Miadapter miadapter = new Miadapter(jugadores);
//                listView.setAdapter(miadapter);
//                Log.e(TAGLOG, "onDataChange:" + dataSnapshot.getValue().toString());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Log.e(TAGLOG, "Error!", databaseError.toException());
//            }
//        };
//        mibd.addValueEventListener(eventListener);
//    }


}
