package com.opencode.myapp.Variedades;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.opencode.myapp.R;
import com.opencode.myapp.Variedades.fragmentos.FVariedades_arandano;

public class Variedades_arandano extends AppCompatActivity {

    private FrameLayout frame_variedades_arandano;
    private String menu,idsinc,producto,nomproducto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_variedades_arandano);

        Intent noo = getIntent();
        menu = noo.getStringExtra("menu");
        idsinc = noo.getStringExtra("idsinc");
        producto = noo.getStringExtra("producto");
        nomproducto = noo.getStringExtra("nomproducto");

       // Toast.makeText(Variedades_arandano.this,"Ruta: "+menu+"-"+nomproducto,Toast.LENGTH_SHORT).show();

        frame_variedades_arandano = findViewById(R.id.frame_variedades_arandano);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_variedades_arandano,new FVariedades_arandano()).commit();

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