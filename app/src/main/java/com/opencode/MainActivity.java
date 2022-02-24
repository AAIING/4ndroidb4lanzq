package com.opencode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
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
import com.opencode.myapp.Models.Login;
import com.opencode.myapp.R;
import com.opencode.myapp.bdsqlite.DBMetodos;
import com.opencode.myapp.config.ApiConf;
import com.opencode.myapp.config.session.SessionDatos;

import WebServices.wsLogin;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText edt_usuario, edt_password;
    private Button btn_login,btn_cerrar;
    private SessionDatos sessionDatos;
    //Loginsync tarea = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sessionDatos = new SessionDatos(this);
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
                    login(usuario, password);
                }else{
                    Toast.makeText(MainActivity.this,"Faltan parametros",Toast.LENGTH_SHORT).show();
                }

/*                if (!usuario.isEmpty()){
                        tarea = new Loginsync(usuario, password);
                        tarea.execute((Void) null);
                }else{
                    Toast.makeText(MainActivity.this,"Debe indicar usuario",Toast.LENGTH_SHORT).show();
                }*/
//                Toast.makeText(MainActivity.this,"**usuario**"+usuario+"-"+password,Toast.LENGTH_SHORT).show();
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

    void login(String usr, String contrasena) {
        Call<Login> call = ApiConf.getData().getLogin(usr, contrasena);
        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if(response.isSuccessful())
                {
                    Login login = response.body();
                    sessionDatos.setLogin();

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
                    Toast.makeText(MainActivity.this, "Error login", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Log.e("ERROR--->", t.toString());
            }
        });
    }

    /*
    public class Loginsync extends AsyncTask<Void, Void, String> {

        private final String usuario;
        private final String password;

        Loginsync(String usuario, String password) {
            this.usuario = usuario;
            this.password = password;
        }

        @Override
        protected String doInBackground(Void... voids) {
            String res = "";
            wsLogin cs = new wsLogin(getApplicationContext());
            try
            {
                res = cs.LoginMovil(this.usuario, this.password);
            }
            catch (Exception e) {
            }
            Log.e("res:",String.valueOf(res));
            return res;
        }

        @Override
        protected void onPreExecute() {
            //cancelButton.setVisibility(View.VISIBLE);
        }

        protected void onPostExecute(final String success) {
            Log.e("succes:",String.valueOf(success));
            switch (success)
            {
                case "1":
                    Toast.makeText(getApplicationContext(), "Usuario/Passw incorrectos", Toast.LENGTH_LONG).show();
                    break;
                case "2":
                    Intent intent = new Intent(MainActivity.this, Empresas.class);
                    startActivity(intent);
                    finish();
                    break;
                default:
                    Toast.makeText(getApplicationContext(), "Web service error : "+success, Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }
    */
}