package com.opencode.myapp.Produccion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.opencode.myapp.R;
import com.opencode.myapp.Produccion.fragmentos.FProduccion;

import java.io.IOException;

import WebServices.wsLeerOperario;

public class Produccion extends AppCompatActivity {

    private FrameLayout frame_produccion;
    public String menu,idsinc,producto, nomproducto, subproducto,nomsubproducto,readMessage,rut,nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produccion);

        Intent noo = getIntent();
        menu = noo.getStringExtra("menu");
        idsinc = noo.getStringExtra("idsinc");
        producto = noo.getStringExtra("producto");
        nomproducto = noo.getStringExtra("nomproducto");
        subproducto = noo.getStringExtra("subproducto");
        nomsubproducto = noo.getStringExtra("nomsubproducto");

        Toast.makeText(Produccion.this, "Ruta: " + menu + "-" + producto + "-" + subproducto+"-"+nomsubproducto, Toast.LENGTH_SHORT).show();

        frame_produccion = findViewById(R.id.frame_produccion);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_produccion, new FProduccion()).commit();

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

    public String getsubproducto() {return subproducto;}

    public String getnomsubproducto() {return nomsubproducto;}

    public String getreadMessage() {
        return readMessage;
    }

    public String getrut() {return rut;}

    public void setrut(String par) {this.rut=par;}

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (result.getContents() == null) {
                Toast.makeText(Produccion.this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                String rut1 = result.getContents();
                Log.e("++ Capturando : ", rut1);
                String rut2 = rut1.substring(0,rut1.length()-1);
                rut = rut2.substring(4);
                Log.e("+++ Capturado : ", rut);
                EditText eddt = findViewById(R.id.edt_Operario);
                eddt.setText(rut);
 //               LeerOperario tarea_leeroperario = new LeerOperario(rut);
 //               if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
 //                   tarea_leeroperario.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
  //                  Toast.makeText(Produccion.this, "EXECUTEONEXECUTOR : " , Toast.LENGTH_LONG).show();
  //              }
 //               else
 //                   tarea_leeroperario.execute();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public class LeerOperario extends AsyncTask<Void, Void, String> {

        private final String rut;

        LeerOperario(String rut) {
            this.rut = rut;
        }

        protected void onPreExecute() {
            //cancelButton.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... voids) {
            String resultado = "";
            wsLeerOperario cs = new wsLeerOperario(Produccion.this);
            try {
                Log.e("Nombre-wsLeerOperario1", "ok");
                resultado = cs.Leer(this.rut);

            } catch (Exception e) {
            }
            return resultado;
        }

        @Override
        protected void onPostExecute(final String resultado) {
            //Toast.makeText(getApplicationContext(),"SUCCESS", Toast.LENGTH_LONG);
            switch (resultado)
            {
                case "-1":
                    Toast.makeText(getApplicationContext(),"Error -1 en Ws", Toast.LENGTH_LONG).show();
                    break;
                case "0":
                    Toast.makeText(getApplicationContext(),"Operario no existe", Toast.LENGTH_LONG).show();
                    break;
                default:
                    nombre=resultado;
                    EditText eddt = findViewById(R.id.edt_Operario);
                    String nrorol=eddt.getText().toString();
                    eddt.setText(nrorol+" "+nombre);
                    FProduccion fp = new FProduccion();
                        fp.syncpesa=true;

                    //    Toast.makeText(getApplicationContext(), "nombre: " + nombre.toString(), Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }
}