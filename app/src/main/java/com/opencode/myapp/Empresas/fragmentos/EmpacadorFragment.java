package com.opencode.myapp.Empresas.fragmentos;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfRenderer;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.ParcelFileDescriptor;
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


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import ZPL.IPort;
import ZPL.ZPLPrinterHelper;
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
    public static final String NUM_PEDIDO_PAUSA_KEY = "numpedpaus_key";
    public static final String PEDIDO_PAUSADO_KEY = "pedpaus_key";

    private List<Pedidos> listPedidos = new ArrayList<>();
    private EmpacadorRecyclerAdapter empacadorRecyclerAdapter;
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;
    private AlertDialog alertDialog;
    private SessionDatos sessionDatos;
    private int idvendedor =0,idsesionempaque=0,idoperario=0, numpedidopausado =0;
    private Sesiones sesiones;
    private TextView viewTiempoInicio, viewBackNav, viewTituloBar, viewBandaInfo;
    //private String fechaPedido="";
    private TicketDoc ticketDoc;
    //private Timer timercount;
    private Handler handler = new Handler();
    private Runnable runnable;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private CallInterface callInterface;
    //private Disposable disposable;
    //
    private String ConnectType="";
    private UsbManager mUsbManager=null;
    private UsbDevice device=null;
    private static final String ACTION_USB_PERMISSION = "com.HPRTSDKSample";
    private PendingIntent mPermissionIntent=null;
    private static IPort Printer=null;
    private ZPLPrinterHelper zplPrinterHelper;

    public EmpacadorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    void conectaImpresoraUSB(){

        try {
            if (zplPrinterHelper != null) {
                zplPrinterHelper.PortClose();
            }

            ConnectType = "USB";
            //HPRTPrinter=new ZPLPrinterHelper(thisCon,arrPrinterList.getItem(spnPrinterList.getSelectedItemPosition()).toString());
            //USB not need call "iniPort"
            mUsbManager = (UsbManager) getContext().getSystemService(Context.USB_SERVICE);
            HashMap<String, UsbDevice> deviceList = mUsbManager.getDeviceList();
            Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();

            boolean HavePrinter = false;

            while (deviceIterator.hasNext()) {
                device = deviceIterator.next();
                int count = device.getInterfaceCount();
                for (int i = 0; i < count; i++) {
                    UsbInterface intf = device.getInterface(i);
                    if (intf.getInterfaceClass() == 7) {
                        HavePrinter = true;
                        mUsbManager.requestPermission(device, mPermissionIntent);
                    }
                }
            }

            if (!HavePrinter)
                Toast.makeText(getContext(), "NO HAY IMPRESORA PARA CONECTAR..",
                        Toast.LENGTH_LONG).show();
            //txtTips.setText(thisCon.getString(R.string.activity_main_connect_usb_printer));
        }
        catch (Exception e)
        {
            //Log.e("HPRTSDKSample", (new StringBuilder("Activity_Main --> onClickConnect "+ConnectType)).append(e.getMessage()).toString());
        }

    }

    private BroadcastReceiver mUsbReceiver = new BroadcastReceiver()
    {
        public void onReceive(Context context, Intent intent)
        {
            try
            {
                String action = intent.getAction();
                if (ACTION_USB_PERMISSION.equals(action))
                {
                    synchronized (this)
                    {
                        device = (UsbDevice)intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                        if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false))
                        {
                            if(zplPrinterHelper.PortOpen(device)!=0)
                            {
//				        		HPRTPrinter=null;
                                //txtTips.setText(thisCon.getString(R.string.activity_main_connecterr));
                                Toast.makeText(getContext(), "ERROR al conectar..",
                                        Toast.LENGTH_SHORT).show();
                                return;
                            }
                            else
                            Toast.makeText(getContext(), "OK Conectado.",
                                    Toast.LENGTH_SHORT).show();
                            //else
                            //txtTips.setText(thisCon.getString(R.string.activity_main_connected));

                        }
                        else
                        {
                            return;
                        }
                    }
                }

                if (UsbManager.ACTION_USB_DEVICE_DETACHED.equals(action))
                {
                    device = (UsbDevice)intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    if (device != null)
                    {
                        zplPrinterHelper.PortClose();
                    }
                }
            }
            catch (Exception e)
            {
                Log.e("HPRTSDKSample", (new StringBuilder("Activity_Main --> mUsbReceiver ")).append(e.getMessage()).toString());
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        sessionDatos = new SessionDatos(getContext());
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        View view = inflater.inflate(R.layout.fragment_empacador, container, false);

        //IMPRESORA
        mPermissionIntent = PendingIntent.getBroadcast(getContext(), 0, new Intent(ACTION_USB_PERMISSION), 0);
        IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
        getContext().registerReceiver(mUsbReceiver, filter);
        zplPrinterHelper = ZPLPrinterHelper.getZPL(getContext());
        conectaImpresoraUSB();

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
        numpedidopausado = 0;
        for(Pedidos item: pedidos)
        {
            if(item.getPedidopausa() > 0){
                numpedidopausado++;
            }
        }


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
                        item.getPedidopausa(),
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
                                int pedidopausa,
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

                    if (pedidopausa == 0) {
                        ticketDoc.openDocument(
                                sesiones.getFechaInicio() + " " + sesiones.getHoraInicio(),
                                false,
                                1,
                                0,
                                String.valueOf(registro), //id detalle
                                tipopedido, // id pedido
                                comuna,//comuna
                                condominio,//condominio
                                nomCliente, //nombre cliente
                                direccion); //direccion

                        String file_path = ticketDoc.getPathFile();
                        File pdfFile = new File(file_path);
                        progressDialog.setMessage("Imprimiendo..");
                        progressDialog.show();
                        List<Bitmap> list_bitmap = pdfToBitmap(pdfFile);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                //do in background
                                try {
                                    //
                                    for (Bitmap bmp : list_bitmap) {
                                        zplPrinterHelper.start();
                                        zplPrinterHelper.printBitmap("60", "60", bmp);
                                        zplPrinterHelper.end();
                                    }
                                    progressDialog.dismiss();
                                } catch (Exception e) {
                                    progressDialog.dismiss();
                                }
                            }
                        }).start();

                        Toast.makeText(getContext(), "Ticket Ingreso N°Pedido " + String.valueOf(registro) + " Generado..", Toast.LENGTH_LONG).show();

                    }

                    //
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
                    bundle.putString(NUM_PEDIDO_PAUSA_KEY, String.valueOf(numpedidopausado));
                    bundle.putString(PEDIDO_PAUSADO_KEY, String.valueOf(pedidopausa));

                    newFragment.setArguments(bundle);

                    FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                    fm.replace(R.id.frame_empresas, newFragment);
                    fm.commit();
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

    private  ArrayList<Bitmap> pdfToBitmap(File pdfFile) {

        ArrayList<Bitmap> bitmaps = new ArrayList<>();

        try {
            PdfRenderer renderer = new PdfRenderer(ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY));

            Bitmap bitmap;

            final int pageCount = renderer.getPageCount();

            for (int i = 0; i < pageCount; i++) {

                PdfRenderer.Page page = renderer.openPage(i);

                int width = getResources().getDisplayMetrics().densityDpi / 72 * page.getWidth();
                int height = getResources().getDisplayMetrics().densityDpi / 72 * page.getHeight();

                bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

                Canvas canvas = new Canvas(bitmap);
                canvas.drawColor(Color.WHITE);
                canvas.drawBitmap(bitmap, 0, 0, null);

                page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);

                bitmaps.add(getResizedBitmap(bitmap, 750, 750));

                // close the page
                page.close();

            }

            // close the renderer
            renderer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return bitmaps;
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);
        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

}