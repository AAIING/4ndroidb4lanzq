package com.opencode.myapp.Variedades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.opencode.myapp.R;
import com.opencode.myapp.Variedades.fragmentos.FVariedades_cereza;

public class Variedades_cereza extends AppCompatActivity {

    private FrameLayout frame_variedades_cereza;
    private String menu, idsinc, producto,nomproducto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_variedades_cereza);

        Intent noo = getIntent();
        menu = noo.getStringExtra("menu");
        idsinc = noo.getStringExtra("idsinc");
        producto = noo.getStringExtra("producto");
        nomproducto = noo.getStringExtra("nomproducto");

       Toast.makeText(Variedades_cereza.this, "Ruta: " + menu + "-" + nomproducto, Toast.LENGTH_SHORT).show();

        frame_variedades_cereza = findViewById(R.id.frame_variedades_cereza);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_variedades_cereza, new FVariedades_cereza()).commit();

    }

    public String getmenu() {
        return menu;
    }

    public String getidsinc() {
        return idsinc;
    }

    public String getproducto() {
        return producto;
    }

    public String getnomproducto() {
        return nomproducto;
    }
}