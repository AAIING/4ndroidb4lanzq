package com.opencode.myapp.Variedades.fragmentos;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.opencode.myapp.Produccion.Produccion;
import com.opencode.myapp.R;
import com.opencode.myapp.Productos.Productos;
import com.opencode.myapp.Variedades.Variedades_arandano;

public class FVariedades_arandano extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    private String  readMessage ="00,00#0";

    private Button btn_variedad1;
    private Button btn_variedad2;
    private Button btn_variedad3;
    private Button btn_volver;

    public FVariedades_arandano() {
        // Required empty public constructor
    }

    public static FVariedades_arandano newInstance(String param1, String param2) {
        FVariedades_arandano fragment = new FVariedades_arandano();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Variedades_arandano moo = (Variedades_arandano) getActivity();
        final String menu = moo.getmenu();
        final String idsinc = moo.getidsinc();
        final String producto = moo.getproducto();
        final String nomproducto = moo.getnomproducto();

        View view = inflater.inflate(R.layout.fragment_variedades_arandano, container, false);

        btn_variedad1=view.findViewById(R.id.btn_variedad1);
        btn_variedad2=view.findViewById(R.id.btn_variedad2);
        btn_variedad3=view.findViewById(R.id.btn_variedad3);
        btn_volver=view.findViewById(R.id.btn_volver);

        btn_variedad1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), Produccion.class);
                intent.putExtra("menu", menu);
                intent.putExtra("idsinc", idsinc);
                intent.putExtra("producto", producto);
                intent.putExtra("nomproducto", nomproducto);
                intent.putExtra("subproducto", "1");
                intent.putExtra("nomsubproducto",  btn_variedad1.getText());
                startActivity(intent);
            }
        });

        btn_variedad2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), Produccion.class);
                intent.putExtra("menu", menu);
                intent.putExtra("idsinc", idsinc);
                intent.putExtra("producto", producto);
                intent.putExtra("nomproducto", nomproducto);
                intent.putExtra("subproducto", "2");
                intent.putExtra("nomsubproducto",  btn_variedad2.getText());
                startActivity(intent);
            }
        });

        btn_variedad3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), Produccion.class);
                intent.putExtra("menu", menu);
                intent.putExtra("idsinc", idsinc);
                intent.putExtra("producto", producto);
                intent.putExtra("nomproducto", nomproducto);
                intent.putExtra("subproducto", "3");
                intent.putExtra("nomsubproducto",  btn_variedad3.getText());
                startActivity(intent);
            }
        });
        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Productos.class);
                intent.putExtra("menu", menu);
                intent.putExtra("idsinc", idsinc);
                startActivity(intent);
            }
        });

        return view;
    }
}