package com.opencode.myapp.Productos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.opencode.myapp.R;
import com.opencode.myapp.Productos.fragmentos.FProductos;

public class Productos extends AppCompatActivity {

    private FrameLayout frame_productos;
    private String menu;
    private String idsinc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);

        Intent noo = getIntent();
        menu = noo.getStringExtra("menu");
        idsinc = noo.getStringExtra("idsinc");

      //  Toast.makeText(Productos.this,"Ruta: "+menu,Toast.LENGTH_SHORT).show();

        frame_productos = findViewById(R.id.frame_productos);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_productos,new FProductos()).commit();

    }

    public String getmenu() {
        return menu;
    }
    public String getidsinc() {
        return idsinc;
    }
}