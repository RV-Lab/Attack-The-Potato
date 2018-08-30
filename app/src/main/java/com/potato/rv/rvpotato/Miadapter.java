package com.potato.rv.rvpotato;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Miadapter extends BaseAdapter {
    private FragmentManager fragment;
    private Activity activity;
    private ArrayList<Jugador> jugadores;


//    public Miadapter(FragmentManager fragment, ArrayList<Jugador> jugadores) {
//        this.fragment = fragment;
//        this.jugadores = jugadores;
//    }
//
//    public Miadapter(Activity activity, ArrayList<Jugador> jugadores) {
//        this.activity = activity;
//        this.jugadores = jugadores;
//    }

    public Miadapter(ArrayList<Jugador> jugadores){
        this.jugadores = jugadores;
    }


    @Override
    public int getCount() {
        return jugadores.size();
    }


    @Override
    public Object getItem(int arg0) {
        return jugadores.get(arg0);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent){
//        View v = convertView;
//        if(convertView == null){
//            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento_lista, parent, false);
//            /*LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            v = inflater.inflate(R.layout.elemento_lista, null);*/
//        }
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento_lista, parent, false);

        Jugador jug = jugadores.get(position);

        TextView numero = (TextView) v.findViewById(R.id.numero);
        numero.setText("" + (position+1));

        TextView nombre = (TextView) v.findViewById(R.id.nombre);
        nombre.setText(jug.getNombre());

        TextView tiempo = (TextView) v.findViewById(R.id.tiempo);

        int tstring = jug.getTiempo();
        int seg = tstring/1000;
        int mil = tstring - seg*1000;
        int min=0;

        if(seg>60){
            min = seg/60;
            seg = seg - min*60;
        }

        String tiempoTotal ="" + min +"m  " + seg +"s  "+ mil + "ms";
        tiempo.setText(tiempoTotal);
        ImageView imagen_lista  = (ImageView) v.findViewById(R.id.imagen_lista);

        if (position == 0){
            imagen_lista.setVisibility(v.VISIBLE);
            numero.setVisibility(View.GONE);
            imagen_lista.setImageResource(R.drawable.oro);
        }else if (position == 1){
            imagen_lista.setVisibility(v.VISIBLE);
            numero.setVisibility(View.GONE);
            imagen_lista.setImageResource(R.drawable.plata);

        }else if (position == 2){
            imagen_lista.setVisibility(v.VISIBLE);
            numero.setVisibility(View.GONE);
            imagen_lista.setImageResource(R.drawable.bronce);
        }else {
            imagen_lista.setVisibility(v.GONE);
            //.GONE quita imagen.
        }



        return v;
    }

/*
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento_lista, parent, false);

        Jugador jug = jugadores.get(position);

        TextView numero = (TextView) v.findViewById(R.id.numero);
        numero.setText("" + (position+1));

        TextView nombre = (TextView) v.findViewById(R.id.nombre);
        nombre.setText(jug.getNombre());

        TextView tiempo = (TextView) v.findViewById(R.id.tiempo);

        int tstring = jug.getTiempo();
        int seg = tstring/1000;
        int mil = tstring - seg*1000;
        int min=0;

        if(seg>60){
            min = seg/60;
            seg = seg - min*60;
        }

        String tiempoTotal ="" + min +"m  " + seg +"s  "+ mil + "ms";
        tiempo.setText(tiempoTotal);
        ImageView imagen_lista  = (ImageView) v.findViewById(R.id.imagen_lista);

        if (position == 0){
            imagen_lista.setVisibility(v.VISIBLE);
            numero.setVisibility(View.GONE);
            imagen_lista.setImageResource(R.drawable.oro);
        }else if (position == 1){
            imagen_lista.setVisibility(v.VISIBLE);
            numero.setVisibility(View.GONE);
            imagen_lista.setImageResource(R.drawable.plata);

        }else if (position == 2){
            imagen_lista.setVisibility(v.VISIBLE);
            numero.setVisibility(View.GONE);
            imagen_lista.setImageResource(R.drawable.bronce);
        }else {
            imagen_lista.setVisibility(v.GONE);
            //.GONE quita imagen.
        }
        //Get imageview and check the position, put the correct trophy for 1,2 and 3 positions.

        return v;
    }*/
}