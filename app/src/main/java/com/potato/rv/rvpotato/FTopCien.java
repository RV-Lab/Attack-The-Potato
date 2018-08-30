package com.potato.rv.rvpotato;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class FTopCien extends Fragment {

    private View v;
    private OnFragmentInteractionListener mListener;

    public FTopCien() {
        // Required empty public constructor
    }


    public static FTopCien newInstance() {
        return new FTopCien();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_top100, container, false);
        cargarDatos();
        initPubli();
        return v;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    private void cargarDatos() {
        ListView lista_top100 = v.findViewById(R.id.lista_top100);
        try {
            //NO FUNCIONA NINGUNA
            //ConexionesBD.visualizarRanking(lista_top100, "Top100", (Activity) getActivity().getApplicationContext());
            //ConexionesBD.visualizarRanking(lista_top100, "Top100");
            //ConexionesBD.visualizarRankingTotal();
            //ConexionesBD.accesoVR(lista_top100, 2);
            //UNICA QUE FUNCIONA
            ConexionesBD.visualizarRankingTotal(lista_top100, "Top100");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initPubli(){
        MobileAds.initialize(getActivity(), "ca-app-pub-1008782396803681~2515099854");
        AdView mAdView = (AdView) v.findViewById(R.id.anuncioTop100);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
}

















/*public class FTopCien extends Fragment{
    private static final String TAGLOG = "firebase-db";
    private ListView lista_top100;
    /*private String nombre="";
    private String tiempo="";
    private String prueba="No se modifica";
    private DatabaseReference mibd;
    private ValueEventListener eventListener;
    private ArrayList<Jugador> jugadores = null;
    View v;


    public FTopCien() {
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
                for(DataSnapshot jugSnap : dataSnapshot.child("FTopCien").getChildren()) {
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

        lista_top100 = (ListView) v.findViewById(R.id.lista_top100);
        Miadapter adaptador = new Miadapter(this.getChildFragmentManager(), jugadores);
        lista_top100.setAdapter(adaptador);
    }
}*/

