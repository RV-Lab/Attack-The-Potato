package com.potato.rv.rvpotato;

import android.support.annotation.NonNull;

public class Jugador implements Comparable {

    //@SerializedName("nombre")
    private String nombre;
    //@SerializedName("tiempo")
    private int tiempo;


    public Jugador() {
    }


    public Jugador(String nombre, int tiempo) {
        this.nombre = nombre;
        this.tiempo = tiempo;
    }

    public String getNombre() {
        return nombre;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public int getTiempo() {
        return tiempo;
    }


    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    //comparar dos jugadores
    @Override
    public int compareTo(@NonNull Object o) {
        int tiempo1;
        tiempo1 = ((Jugador)o).getTiempo();
        /* For Ascending order*/
        return this.tiempo-tiempo1;
    }


    @Override
    public String toString() {
        return "Jugador{" +
                "nombre='" + nombre + '\'' +
                ", tiempo=" + tiempo +
                '}';
    }
}
