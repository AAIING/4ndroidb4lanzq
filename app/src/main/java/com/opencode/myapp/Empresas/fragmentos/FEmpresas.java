package com.opencode.myapp.Empresas.fragmentos;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.opencode.MainActivity;
import com.opencode.myapp.Correcciones.Correcciones;
import com.opencode.myapp.Empresas.Empresas;
import com.opencode.myapp.Models.Capturas;
import com.opencode.myapp.Models.Itemsid;
import com.opencode.myapp.Models.Login;
import com.opencode.myapp.Models.Pedidosd;
import com.opencode.myapp.R;
import com.opencode.myapp.Productos.Productos;
import com.opencode.myapp.bdsqlite.DBDatos;
import com.opencode.myapp.bdsqlite.DBMetodos;
import com.opencode.myapp.config.ApiConf;
import com.opencode.myapp.config.itext.TicketDoc;
import com.opencode.myapp.config.itext.TicketEmpaqRestaurantDoc;
import com.opencode.myapp.config.session.SessionDatos;
import com.opencode.myapp.config.session.SessionKeys;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import WebServices.wsSincroniza;
import ZPL.IPort;
import ZPL.ZPLPrinterHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FEmpresas extends Fragment {

    //
    public static final String ID_EMPAQUE_RESTAURANT_KEY = "emprest_key";

    private Button btn_opcion1, btn_empacador, btn_empaquerestaurant;
    private Button btn_opcion2;
    private Button btn_correcciones;
    private Button btn_sincronizar;
    private Button btn_reiniciasinc;
    private Button btn_volver;
    private List<Capturas> listadecapturas=new ArrayList<>();
    private String idsinc;
    private SessionDatos sessionDatos;
    private TicketEmpaqRestaurantDoc ticketEmpaqRestaurantDoc;
    private TicketDoc ticketDoc;
    private List<Itemsid> listItems = new ArrayList<>();

    public FEmpresas() {
        // Required empty public constructor
    }

    public static FEmpresas newInstance(String param1, String param2) {
        FEmpresas fragment = new FEmpresas();
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
        View view = inflater.inflate(R.layout.fragment_empresas, container, false);

        ticketEmpaqRestaurantDoc = new TicketEmpaqRestaurantDoc(getContext());
        ticketDoc = new TicketDoc(getContext());
        DBMetodos dbm = new DBMetodos(getContext());
        SQLiteDatabase db = dbm.getReadableDatabase();
        idsinc = String.valueOf(dbm.ObtieneIDSincronizado());
        sessionDatos = new SessionDatos(getContext());
        btn_empacador=view.findViewById(R.id.btn_opcion_empacador);
        btn_empacador.setOnClickListener(onClickEmpacador);
        btn_opcion1=view.findViewById(R.id.btn_opcion1);
        btn_opcion2=view.findViewById(R.id.btn_opcion2);
        btn_correcciones=view.findViewById(R.id.btn_correcciones);
        btn_sincronizar=view.findViewById(R.id.btn_sincronizar);
        btn_sincronizar.setText("("+idsinc+")"+"SINCRONIZACION");
        btn_reiniciasinc=view.findViewById(R.id.btn_reiniciasinc);
        btn_volver=view.findViewById(R.id.btn_volver);
        btn_empaquerestaurant=view.findViewById(R.id.btn_opcion_empacador_restaurant);
        btn_empaquerestaurant.setOnClickListener(onClickEmpaqueRestaurant);

        btn_opcion1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Productos.class);
                intent.putExtra("menu", "1");
                intent.putExtra("idsinc", idsinc);
                startActivity(intent);
            }
        });

        btn_opcion2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Productos.class);
                intent.putExtra("menu", "2");
                intent.putExtra("idsinc", idsinc);
                startActivity(intent);
            }
        });

        btn_correcciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Correcciones.class);
                startActivity(intent);

            }
        });

        btn_sincronizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DBMetodos dbmetodos = new DBMetodos(getContext());
                SQLiteDatabase db2 = dbmetodos.getWritableDatabase();

                ContentValues contentValues = new ContentValues();
                contentValues.put(DBDatos.NOSINCRONIZADO, 0);
                db2.insert(DBDatos.TABLE_SINCRONIZADO, null, contentValues);

                Cursor cursor = dbmetodos.leercapturas();

                Toast.makeText(getContext(), "Sincronizando....", Toast.LENGTH_LONG).show();
                int c=0;
                while (cursor.moveToNext())
                {
                    String sincronizado =  cursor.getString(cursor.getColumnIndex(DBDatos.SINCRONIZADO));
                    if (sincronizado.equals("0"))
                    {
                        String rut =  cursor.getString(cursor.getColumnIndex(DBDatos.RUT));
                        if (rut != null)
                        {
                            String cantidad = cursor.getString(cursor.getColumnIndex(DBDatos.CANTIDAD));
                            if (!cantidad.isEmpty()){

                                String nombre =  cursor.getString(cursor.getColumnIndex(DBDatos.NOMBRE));
                                String empresa =  cursor.getString(cursor.getColumnIndex(DBDatos.EMPRESA));
                                String cuartel =  cursor.getString(cursor.getColumnIndex(DBDatos.CUARTEL));
                                String telefono =  cursor.getString(cursor.getColumnIndex(DBDatos.TELEFONO));
                                String nosinc =  cursor.getString(cursor.getColumnIndex(DBDatos.NOSINC));
                                String producto =  cursor.getString(cursor.getColumnIndex(DBDatos.PRODUCTO));
                                String variedad =  cursor.getString(cursor.getColumnIndex(DBDatos.VARIEDAD));

                                double rutp =Double.parseDouble(rut);
                                double canp =0;
                                canp = new Double(cantidad);

                                if (rutp>0 && canp>0)
                                {
                                    c++;
                                  //  Toast.makeText(getContext(), "Sincronizados="+String.valueOf(c), Toast.LENGTH_SHORT).show();
                                    String id = cursor.getString(cursor.getColumnIndex(DBDatos.IDCAPTURA));
                                    Sincronizar sincroniza = new Sincronizar(dbmetodos,id,rut,nombre,empresa,cuartel,telefono,nosinc,producto,variedad,canp);

                                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                                        try {
                                            String result = sincroniza.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
                                        } catch (ExecutionException e) {
                                            e.printStackTrace();
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    else {
                                        try {
                                            String result = sincroniza.execute().get();
                                        } catch (ExecutionException e) {
                                            e.printStackTrace();
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                cursor.close();
                dbmetodos.close();
                Toast.makeText(getContext(), "Fin Sincronizado !!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);

            }
        });

        btn_reiniciasinc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                List<Pedidosd> pedidosdList = new ArrayList<>();
                Pedidosd item = new Pedidosd();
                pedidosdList.add(item);
                ticketEmpaqRestaurantDoc.openDocument(
                        "FECHAELAB",
                        0,
                        pedidosdList,
                        true,
                        1,
                        1,
                        "00000", //id detalle
                        "r4", // id pedido
                        "r7",//comuna
                        "r10",//condominio
                        "r2", //nombre cliente
                        "r8");
                String url_path2 = ticketDoc.getPathFile();
                String shortuid = UUID.randomUUID().toString().replace("-","").substring(0,8);
                Log.e("UUID--->", shortuid);
                ticketDoc.openDocument(
                        "",
                        "",
                        false,
                        2,
                        0,
                        "r1", //id detalle
                        "r4", // id pedido
                        "r7",//comuna
                        "r10",//condominio
                        "r2", //nombre cliente
                        "r8"); //direccion
                String url_path2 = ticketDoc.getPathFile();
                //pdfFile = new File(url_path2);
                listItems = ticketDoc.getListItems();
                //
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getContext());
                dialogo1.setTitle("Importante");
                dialogo1.setMessage("??Esta seguro de reiniciar sincronizado?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        DBMetodos dbm = new DBMetodos(getContext());
                        SQLiteDatabase db = dbm.getWritableDatabase();
                        dbm.ReiniciaIDSincronizado(db);
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        startActivity(intent);
                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        //  cancelar();
                    }
                });
                dialogo1.show();
                */
            }
        });

        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                Call<Login> call = ApiConf.getData().getLogSesion(
                        Integer.parseInt(sessionDatos.getRecord().get(SessionKeys.idOperario)),
                        Integer.parseInt(sessionDatos.getRecord().get(SessionKeys.idSesion)));
                call.enqueue(new Callback<Login>() {
                    @Override
                    public void onResponse(Call<Login> call, Response<Login> response) {
                        if(response.isSuccessful())
                        {
                            /*
                            Login login = response.body();
                            sessionDatos.setLogin(login.getNombre(),
                                    String.valueOf(login.getVendedor()),
                                    String.valueOf(login.getIdSesion()));
                             */
                        }
                        //
                        File folder3 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                        if (folder3.exists()) {
                            File[] Files = folder3.listFiles();
                            if (Files != null) {
                                for (int j = 0; j < Files.length; j++) {
                                    //
                                    Files[j].delete();
                                }
                            }
                        }

                        Intent intent = new Intent(getContext(), MainActivity.class);
                        startActivity(intent);

                        sessionDatos.cleanSesion();

                        Log.e("OK--->", response.toString());

                    }
                    @Override
                    public void onFailure(Call<Login> call, Throwable t) {
                        Log.e("ERROR--->", t.toString());

                    }
                });
            }
        });

        return view;
    }

    private View.OnClickListener onClickEmpacador = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            EmpacadorFragment newFragment = new EmpacadorFragment();
            //0 = EMPAQUE, 1 = EMPAQE RESTAURANT
            sessionDatos.enableEmpaqueRestaurant("0");
            FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
            fm.replace(R.id.frame_empresas, newFragment);
            fm.commit();
        }
    };

    private View.OnClickListener onClickEmpaqueRestaurant = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            EmpacadorFragment newFragment = new EmpacadorFragment();
            sessionDatos.enableEmpaqueRestaurant("1");
            FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
            fm.replace(R.id.frame_empresas, newFragment);
            fm.commit();
        }
    };

    public class Sincronizar extends  AsyncTask<Void, Void, String> {

        private DBMetodos dbmetodos;
        private String id;
        private String rut;
        private String nombre;
        private String empresa;
        private String cuartel;
        private String telefono;
        private String nosinc;
        private String producto;
        private String variedad;
        private double cantidad;

         Sincronizar(DBMetodos dbmetodos,String id,String rut, String nombre, String empresa, String cuartel,String telefono, String nosinc,String producto, String variedad, double cantidad)
        {
            this.dbmetodos= dbmetodos;
            this.id= id;
            this.rut = rut;
            this.nombre = nombre;
            this.empresa=empresa;
            this.cuartel=cuartel;
            this.telefono=telefono;
            this.nosinc=nosinc;
            this.producto=producto;
            this.variedad=variedad;
            this.cantidad=cantidad;
        }

        protected void onPreExecute() {  }

        @Override
        protected String doInBackground(Void... voids) {
            String resultado = "";
            wsSincroniza cs = new wsSincroniza(getContext());
            try {
                resultado = cs.Subir(this.rut,this.nombre,this.empresa,this.cuartel,this.telefono,this.nosinc,this.producto,this.variedad,this.cantidad);
                Log.e("NOSINC", this.nosinc);

            } catch (Exception e) { resultado="-1";
            }
            return resultado;
        }

        @Override
        protected void onPostExecute(final String resultado) {
            //Toast.makeText(getApplicationContext(),"SUCCESS", Toast.LENGTH_LONG);
            switch (resultado)
            {
                case "1":
//                    Toast.makeText(getContext(),"subiendo captura...", Toast.LENGTH_LONG).show();
                    SQLiteDatabase dbw = dbmetodos.getWritableDatabase();
                    dbmetodos.updateSincronizados(id, "1", dbw);
                    break;
                case "0":
                    Toast.makeText(getContext(),"Producto no existe", Toast.LENGTH_LONG).show();
                    break;
                default:
                    Toast.makeText(getContext(),"Error en Ws"+resultado, Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }
}