package com.opencode.myapp.Empresas.fragmentos;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.opencode.MainActivity;
import com.opencode.myapp.Empresas.adapters.EmpacadorRecyclerAdapter;
import com.opencode.myapp.Models.Clientes;
import com.opencode.myapp.Models.Login;
import com.opencode.myapp.Models.Parametros;
import com.opencode.myapp.Models.Pedidos;
import com.opencode.myapp.Models.Pedidosd;
import com.opencode.myapp.Models.Sesiones;
import com.opencode.myapp.Models.Vendedores;
import com.opencode.myapp.R;
import com.opencode.myapp.config.ApiConf;
import com.opencode.myapp.config.CallInterface;
import com.opencode.myapp.config.itext.TicketDoc;
import com.opencode.myapp.config.session.SessionDatos;
import com.opencode.myapp.config.session.SessionKeys;

import org.json.JSONException;
import org.json.JSONObject;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class EmpacadorFragment extends Fragment {

    public static final String REGISTRO_DETALLE_KEY = "detalle_key";
    public static final String NOMBRE_CLIENTE_KEY = "nomcli_key";
    public static final String ID_SESION_EMPAQUE_KEY = "sesemp_key";
    public static final String ID_PEDIDO_WEB_KEY = "pedweb_key";
    public static final String NOMBRE_VENDEDOR_KEY = "nomvend_key";
    public static final String FECHA_PEDIDO_KEY = "fchped_key";
    public static final String COMUNA_PEDIDO_KEY = "comped_key";
    public static final String DIRECCION_PEDIDO_KEY = "dirped_key";
    public static final String CATEGORIA_CLIENTE_KEY = "catcl_key";
    public static final String CONDOMINIO_CLIENTE_KEY = "concl_key";
    public static final String CANTIDAD_COMANDA_KEY = "cantcom_key";

    private List<Pedidos> listPedidos = new ArrayList<>();
    private EmpacadorRecyclerAdapter empacadorRecyclerAdapter;
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;
    private AlertDialog alertDialog;
    private SessionDatos sessionDatos;
    private int idvendedor =0,idsesionempaque=0,idoperario=0;
    private Sesiones sesiones;
    private TextView viewTiempoInicio, viewBackNav, viewTituloBar, viewBandaInfo;
    //private String fechaPedido="";
    private TicketDoc ticketDoc;
    //private Timer timercount;
    //private Handler handler = new Handler();
    //private Runnable runnable;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private CallInterface callInterface;

    //private Disposable disposable;

    public EmpacadorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        sessionDatos = new SessionDatos(getContext());
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        View view = inflater.inflate(R.layout.fragment_empacador, container, false);
        alertDialog = new AlertDialog.Builder(getContext()).create();
        progressDialog = new ProgressDialog(getContext());
        recyclerView = view.findViewById(R.id.recycler_list_empaca);
        viewTiempoInicio = view.findViewById(R.id.view_det_tiempo_inicio_empacador);
        viewBackNav = view.findViewById(R.id.view_back_nav_empacador);
        viewBackNav.setOnClickListener(onClickBackNav);
        viewTituloBar = view.findViewById(R.id.view_titulo_toolbar_empacador);
        ticketDoc = new TicketDoc(getContext());
        viewBandaInfo = view.findViewById(R.id.view_banda_info);
        Animation marquee =  AnimationUtils.loadAnimation(getContext(), R.anim.marquee);
        viewBandaInfo.startAnimation(marquee);
        viewTituloBar.setText("Bienvenido "+sessionDatos.getRecord().get(SessionKeys.nombreUsuario));
        viewTiempoInicio.setText("Tiempo Inicio Turno: "+sessionDatos.getRecord().get(SessionKeys.horaInicio));
        progressDialog.show();
        progressDialog.setMessage("Cargando datos..");
        idoperario = Integer.parseInt(sessionDatos.getRecord().get(SessionKeys.idOperario));
        //
        Retrofit retrofit = ApiConf.getRetrofit();
        callInterface = retrofit.create(CallInterface.class);

        compositeDisposable.add(Observable.interval(3, TimeUnit.SECONDS)
                .flatMap(n -> callInterface.getPedidosPendientes(idoperario))
                .repeat()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Pedidos>>() {
                    @Override
                    public void accept(List<Pedidos> pedidos) throws Exception {
                        loadListEmpacador(pedidos);
                    }
                }, this::onError));

        compositeDisposable.add(Observable.interval(3, TimeUnit.SECONDS)
                .flatMap(n -> callInterface.getParametros())
                .repeat()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Parametros>() {
                    @Override
                    public void accept(Parametros result) throws Exception {
                        viewBandaInfo.setText(result.getBandaInfo());
                        sessionDatos.setPesoTope(String.valueOf(result.getPesoTope()));
                    }
                }, this::onError));

        return view;
    }

    private void onError(Throwable throwable) {
        //Toast.makeText(this, "OnError in Observable Timer", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStop() {
        //
        compositeDisposable.clear();
        super.onStop();
    }


    void loadListEmpacador(List<Pedidos> pedidos){
        /***/
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        empacadorRecyclerAdapter = new EmpacadorRecyclerAdapter(getContext(), pedidos);
        empacadorRecyclerAdapter.setOnClickListener(new EmpacadorRecyclerAdapter.OnClickListener() {
            @Override
            public void onEditar(View view, int position) {
                Pedidos item = pedidos.get(position);

                Vendedores item_vend ;
                Clientes item_cl = item.getClientes();

                progressDialog.setCancelable(false);
                progressDialog.setMessage("Ingresando a pedido..");
                progressDialog.show();

                String tipopedido = "";
                String categoria = "";

                if(item.isWeb()){
                    tipopedido = "WWW";
                } else {
                    tipopedido = "VENDEDOR";
                }

                if(item_cl.getCategoria().equals("1")){
                    categoria = "STANDAR";
                } else if(item_cl.getCategoria().equals("2")){
                    categoria = "PREMIUM";
                }

                if(item.getVendedores() == null){
                    Vendedores item_vend2 = new Vendedores();
                    item_vend2.setNombre("SIN VENDEDOR");
                    pedidos.get(position).setVendedores(item_vend2);
                    item_vend = item.getVendedores();

                }else{
                    item_vend = item.getVendedores();
                }

                getSesionEmpaque(
                        item.getCantcomanda(),
                        Integer.parseInt(sessionDatos.getRecord().get(SessionKeys.idSesion)),
                        item.getRegistro(),
                        item.getCliente(),
                        Integer.parseInt(sessionDatos.getRecord().get(SessionKeys.idOperario)),
                        item.getRegistro(),
                        0,
                        item_vend.getNombre(),
                        tipopedido,
                        item.getFecha(),
                        item.getCondominioenvio(),
                        item.getCondominioenvio(),
                        item.getDireccionenvio(),
                        categoria);
            }
        });
        recyclerView.setAdapter(empacadorRecyclerAdapter);
        progressDialog.dismiss();
    }

    private View.OnClickListener onClickBackNav = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setTitle("Finalizar Turno");
            alertDialog.setMessage("¿Desea finalizar turno?");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    alertDialog.dismiss();

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
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Volver a menú", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    alertDialog.dismiss();

                    FEmpresas newFragment = new FEmpresas();
                    FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                    fm.replace(R.id.frame_empresas, newFragment);
                    fm.commit();
                }
            });

            alertDialog.show();

        }
    };

    private void getSesionEmpaque(
                                int cantcomanda,
                                  int idsesion,
                                  int registro,
                                  String nomCliente,
                                  int idusuario,
                                  int idpedido,
                                  int idsesionempaque,
                                  String nomVendedor,
                                  String tipopedido,
                                  String fechaPedido,
                                  String comuna,
                                  String condominio,
                                  String direccion,
                                  String categoria){

        Call<Sesiones> call = ApiConf.getData().getSesionEmpaque(idsesion,idusuario, idpedido, idsesionempaque, 0, "");
        call.enqueue(new Callback<Sesiones>() {
            @Override
            public void onResponse(Call<Sesiones> call, Response<Sesiones> response) {
                if (response.isSuccessful()) {
                    Sesiones sesiones = response.body();

                    DetalleFragment newFragment = new DetalleFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(REGISTRO_DETALLE_KEY, String.valueOf(registro));
                    bundle.putString(NOMBRE_CLIENTE_KEY, nomCliente);
                    bundle.putString(ID_SESION_EMPAQUE_KEY, String.valueOf(sesiones.getCodigoSesionEmpaque()));
                    bundle.putString(ID_PEDIDO_WEB_KEY, tipopedido);
                    bundle.putString(NOMBRE_VENDEDOR_KEY, nomVendedor);
                    bundle.putString(FECHA_PEDIDO_KEY, fechaPedido);
                    bundle.putString(COMUNA_PEDIDO_KEY, comuna);
                    bundle.putString(DIRECCION_PEDIDO_KEY, direccion);
                    bundle.putString(CATEGORIA_CLIENTE_KEY, categoria);
                    bundle.putString(CONDOMINIO_CLIENTE_KEY, condominio);
                    bundle.putString(CANTIDAD_COMANDA_KEY, String.valueOf(cantcomanda));

                    newFragment.setArguments(bundle);
                    FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                    fm.replace(R.id.frame_empresas, newFragment);
                    fm.commit();

                    ticketDoc.openDocument(
                            sesiones.getFechaInicio()+" "+sesiones.getHoraInicio(),
                            false,
                            1,
                            0,
                            String.valueOf(registro), //id detalle
                            tipopedido, // id pedido
                            comuna,//comuna
                            condominio,//condominio
                            nomCliente, //nombre cliente
                            direccion); //direccion

                    Toast.makeText(getContext(), "Ticket Ingreso N°Pedido "+String.valueOf(registro)+" Generado..", Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Sesiones> call, Throwable t) {
                Log.e("ERROR--->", t.toString());
                progressDialog.dismiss();
            }
        });
    }
}