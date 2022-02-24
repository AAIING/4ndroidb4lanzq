package com.opencode.myapp.Productos.fragmentos;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.opencode.myapp.Empresas.Empresas;
import com.opencode.myapp.R;
import com.opencode.myapp.Productos.Productos;
import com.opencode.myapp.Variedades.Variedades_arandano;
import com.opencode.myapp.Variedades.Variedades_cereza;



public class FProductos extends Fragment {

    private Button btn_producto1;
    private Button btn_producto2;
    private Button btn_volver;

    public FProductos() {
        // Required empty public constructor
    }


    public static FProductos newInstance(String param1, String param2) {
        FProductos fragment = new FProductos();
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
        Productos moo = (Productos) getActivity();
        final String menu = moo.getmenu();
        final String idsinc = moo.getidsinc();

        View view = inflater.inflate(R.layout.fragment_productos, container, false);

        btn_producto1=view.findViewById(R.id.btn_producto1);
        btn_producto2=view.findViewById(R.id.btn_producto2);
        btn_volver=view.findViewById(R.id.btn_volver);

        btn_producto1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Variedades_arandano.class);
                intent.putExtra("menu", menu);
                intent.putExtra("idsinc", idsinc);
                intent.putExtra("producto", "1");
                intent.putExtra("nomproducto", btn_producto1.getText());
                startActivity(intent);
            }
        });

        btn_producto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Variedades_cereza.class);
                intent.putExtra("menu", menu);
                intent.putExtra("idsinc", idsinc);
                intent.putExtra("producto", "2");
                intent.putExtra("nomproducto", btn_producto2.getText());
                startActivity(intent);
            }
        });

        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Empresas.class);
                startActivity(intent);
            }
        });

        return view;
    }
}