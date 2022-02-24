package com.opencode.myapp.Correcciones;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.opencode.myapp.Correcciones.fragmentos.FCorrecciones;

import com.opencode.myapp.R;

public class Correcciones extends AppCompatActivity {

    private FrameLayout fragment_correcciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correcciones);

    //    fragment_correcciones = findViewById(R.id.frame_correciones);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_correcciones,new FCorrecciones()).commit();

    }
}