package com.opencode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.opencode.myapp.Empresas.Empresas;
import com.opencode.myapp.Empresas.adapters.EmpacadorRecyclerAdapter;
import com.opencode.myapp.Empresas.adapters.OperarioRecyclerAdapter;
import com.opencode.myapp.Empresas.fragmentos.EmpacadorFragment;
import com.opencode.myapp.Models.Login;
import com.opencode.myapp.Models.Operarios;
import com.opencode.myapp.Models.Pedidos;
import com.opencode.myapp.R;
import com.opencode.myapp.bdsqlite.DBMetodos;
import com.opencode.myapp.config.ApiConf;
import com.opencode.myapp.config.session.SessionDatos;
import com.opencode.myapp.config.session.SessionKeys;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import WebServices.wsLogin;
import ZPL.IPort;
import ZPL.ZPLPrinterHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText edt_usuario, edt_password;
    private Button btn_login,btn_cerrar;
    private SessionDatos sessionDatos;
    private ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    private OperarioRecyclerAdapter operarioRecyclerAdapter;
    //Loginsync tarea = null;
    private List<Operarios> listOperarios = new ArrayList<>();
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Iniciar Turno");
        sessionDatos = new SessionDatos(this);
        progressDialog = new ProgressDialog(this);
        recyclerView = findViewById(R.id.recycler_list_operarios);
        alertDialog = new AlertDialog.Builder(this).create();
        getOperarios();
        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[] {
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },
                    0);
        }

        edt_usuario =findViewById(R.id.edt_user);
        edt_usuario.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        edt_password=findViewById(R.id.edt_password);
        btn_login=findViewById(R.id.btn_login);
        btn_cerrar=findViewById(R.id.btn_cerrar);
        if (sessionDatos.CheckSession()) {
            Intent intent = new Intent(MainActivity.this, Empresas.class);
            startActivity(intent);
            finish();
        }
        edt_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean tieneelfocus) {
                if (!tieneelfocus) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edt_password.getWindowToken(), 0);
                    btn_login.setBackgroundColor(Color.rgb(248, 196, 113));
                }
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usuario= edt_usuario.getText().toString();
                String password=edt_password.getText().toString();

                if (!usuario.isEmpty() && !password.isEmpty()){
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    progressDialog.setMessage("Iniciando sesión..");
                    login(usuario, password);
                }else{
                    Toast.makeText(MainActivity.this,"Faltan parametros",Toast.LENGTH_SHORT).show();
                }

/*              if (!usuario.isEmpty()){
                    tarea = new Loginsync(usuario, password);
                    tarea.execute((Void) null);
                }else{
                    Toast.makeText(MainActivity.this,"Debe indicar usuario",Toast.LENGTH_SHORT).show();
                }*/
//                  Toast.makeText(MainActivity.this,"**usuario**"+usuario+"-"+password,Toast.LENGTH_SHORT).show();
                /*
                DBMetodos dbm = new DBMetodos(MainActivity.this);
                SQLiteDatabase db = dbm.getReadableDatabase();
                boolean isExist = dbm.validarUsuario(usuario, password,db);

                if(isExist){
                    Intent intent = new Intent(MainActivity.this, Empresas.class);
                    startActivity(intent);
                    finish();
                } else {
                    edt_password.setText(null);
                    Toast.makeText(MainActivity.this, "usuario o password invalido", Toast.LENGTH_SHORT).show();
                }
                */
            }
        });


        btn_cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            //homeIntent.addCategory( Intent.CATEGORY_HOME );
            //homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //startActivity(homeIntent);
              finishAffinity();
              System.exit(0);

            }
        });
    }

    void listaOperarios(){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        operarioRecyclerAdapter = new OperarioRecyclerAdapter(this, listOperarios);
        operarioRecyclerAdapter.setOnClickListener(new OperarioRecyclerAdapter.OnClickListener() {
            @Override
            public void onSelect(View view, int position) {
                Operarios item = listOperarios.get(position);
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.setTitle("Iniciar Turno");
                alertDialog.setMessage("¿Esta seguro de iniciar turno?");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressDialog.setCancelable(false);
                        progressDialog.setMessage("Ingresando..");
                        progressDialog.show();
                        //
                        logSesion(item.Codigo, item.Nombre);
                        alertDialog.dismiss();
                    }
                });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Volver", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });
        recyclerView.setAdapter(operarioRecyclerAdapter);
    }

    void logSesion(int idoperario, String nombre){
        //
        Call<Login> call = ApiConf.getData().getLogSesion(idoperario, 0);
        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if(response.isSuccessful())
                {
                    Login item = response.body();
                    progressDialog.dismiss();
                    sessionDatos.setIdOperario(
                            String.valueOf(idoperario),
                            String.valueOf(item.getIdSesion()),
                            nombre,
                            item.getHoraInicio());

                    Intent intent = new Intent(MainActivity.this, Empresas.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                progressDialog.dismiss();
            }
        });

    }

    void getOperarios(){
        Call<List<Operarios>> call = ApiConf.getData().getListaOperarios();
        call.enqueue(new Callback<List<Operarios>>() {
            @Override
            public void onResponse(Call<List<Operarios>> call, Response<List<Operarios>> response) {
                if(response.isSuccessful())
                {
                    List<Operarios> op = response.body();
                    listOperarios.addAll(op);
                    listaOperarios();
                }
            }

            @Override
            public void onFailure(Call<List<Operarios>> call, Throwable t) {

            }
        });
    }

    /***/
    void login(String usr, String contrasena) {
        Call<Login> call = ApiConf.getData().getLogin(usr, contrasena, 0);
        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if(response.isSuccessful())
                {
                    progressDialog.dismiss();
                    Login login = response.body();
                    sessionDatos.setLogin(login.getNombre(),
                            String.valueOf(login.getVendedor()),
                            String.valueOf(login.getIdSesion()),
                            String.valueOf(login.getUsuariosReferencia()));

                    Intent intent = new Intent(MainActivity.this, Empresas.class);
                    startActivity(intent);
                    finish();
                    /*
                    sessionOperarios.loginSession(String.valueOf(login.getRut()),
                            login.getDiv(), login.getNombre(), String.valueOf(login.getEmpresa()));
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    startActivity(intent);
                    */
                }
                else
                {
                    //Toast.makeText(MainActivity.this, "Error login", Toast.LENGTH_LONG).show();
                }


            }
            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Log.e("ERROR--->", t.toString());
                progressDialog.dismiss();

                Toast.makeText(MainActivity.this, "Error login..\ncompruebe usuario y/o contraseña", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        return;
    }

}