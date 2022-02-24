package com.opencode.myapp.Empresas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.opencode.myapp.Empresas.fragmentos.FEmpresas;
import com.opencode.myapp.R;

import java.util.ArrayList;

public class Empresas extends AppCompatActivity {

    private FrameLayout fragment_menuop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresas);

       // fragment_menuop = findViewById(R.id.frame_empresas);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_empresas,new FEmpresas()).commit();

    }
}