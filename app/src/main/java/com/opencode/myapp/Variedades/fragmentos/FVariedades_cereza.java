package com.opencode.myapp.Variedades.fragmentos;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.opencode.myapp.Produccion.Produccion;
import com.opencode.myapp.R;
import com.opencode.myapp.Productos.Productos;
import com.opencode.myapp.Variedades.Variedades_cereza;

public class FVariedades_cereza extends Fragment {

    private Button btn_variedad1;
    private Button btn_variedad2;
    private Button btn_variedad3;
    private Button btn_variedad4;
    private Button btn_variedad5;
    private Button btn_variedad6;
    private Button btn_variedad7;
    private Button btn_variedad8;
    private Button btn_variedad9;
    private Button btn_volver;

    public FVariedades_cereza() {
        // Required empty public constructor
    }


    public static FVariedades_cereza newInstance(String param1, String param2) {
        FVariedades_cereza fragment = new FVariedades_cereza();

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

        Variedades_cereza moo = (Variedades_cereza) getActivity();
        final String menu = moo.getmenu();
        final String idsinc = moo.getidsinc();
        final String producto = moo.getproducto();
        final String nomproducto = moo.getnomproducto();

        View view = inflater.inflate(R.layout.fragment_variedades_cereza, container, false);

        btn_variedad1=view.findViewById(R.id.btn_variedad1);
        btn_variedad2=view.findViewById(R.id.btn_variedad2);
        btn_variedad3=view.findViewById(R.id.btn_variedad3);
        btn_variedad4=view.findViewById(R.id.btn_variedad4);
        btn_variedad5=view.findViewById(R.id.btn_variedad5);
        btn_variedad6=view.findViewById(R.id.btn_variedad6);
        btn_variedad7=view.findViewById(R.id.btn_variedad7);
        btn_variedad8=view.findViewById(R.id.btn_variedad8);
        btn_variedad9=view.findViewById(R.id.btn_variedad9);
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

        btn_variedad4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), Produccion.class);
                intent.putExtra("menu", menu);
                intent.putExtra("idsinc", idsinc);
                intent.putExtra("producto", producto);
                intent.putExtra("nomproducto", nomproducto);
                intent.putExtra("subproducto", "4");
                intent.putExtra("nomsubproducto",  btn_variedad4.getText());
                startActivity(intent);
            }
        });

        btn_variedad5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), Produccion.class);
                intent.putExtra("menu", menu);
                intent.putExtra("idsinc", idsinc);
                intent.putExtra("producto", producto);
                intent.putExtra("nomproducto", nomproducto);
                intent.putExtra("subproducto", "5");
                intent.putExtra("nomsubproducto",  btn_variedad5.getText());
                startActivity(intent);
            }
        });

        btn_variedad6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), Produccion.class);
                intent.putExtra("menu", menu);
                intent.putExtra("idsinc", idsinc);
                intent.putExtra("producto", producto);
                intent.putExtra("nomproducto", nomproducto);
                intent.putExtra("subproducto", "6");
                intent.putExtra("nomsubproducto",  btn_variedad6.getText());
                startActivity(intent);
            }
        });

        btn_variedad7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), Produccion.class);
                intent.putExtra("menu", menu);
                intent.putExtra("idsinc", idsinc);
                intent.putExtra("producto", producto);
                intent.putExtra("nomproducto", nomproducto);
                intent.putExtra("subproducto", "7");
                intent.putExtra("nomsubproducto",  btn_variedad7.getText());
                startActivity(intent);
            }
        });

        btn_variedad8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), Produccion.class);
                intent.putExtra("menu", menu);
                intent.putExtra("idsinc", idsinc);
                intent.putExtra("producto", producto);
                intent.putExtra("nomproducto", nomproducto);
                intent.putExtra("subproducto", "8");
                intent.putExtra("nomsubproducto",  btn_variedad8.getText());
                startActivity(intent);
            }
        });
        btn_variedad9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), Produccion.class);
                intent.putExtra("menu", menu);
                intent.putExtra("idsinc", idsinc);
                intent.putExtra("producto", producto);
                intent.putExtra("nomproducto", nomproducto);
                intent.putExtra("subproducto", "9");
                intent.putExtra("nomsubproducto",  btn_variedad9.getText());
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