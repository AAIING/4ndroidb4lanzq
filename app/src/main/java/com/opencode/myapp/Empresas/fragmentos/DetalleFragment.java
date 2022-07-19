package com.opencode.myapp.Empresas.fragmentos;

import static com.opencode.myapp.Empresas.fragmentos.EmpacadorFragment.CANTIDAD_COMANDA_KEY;
import static com.opencode.myapp.Empresas.fragmentos.EmpacadorFragment.CATEGORIA_CLIENTE_KEY;
import static com.opencode.myapp.Empresas.fragmentos.EmpacadorFragment.COMUNA_PEDIDO_KEY;
import static com.opencode.myapp.Empresas.fragmentos.EmpacadorFragment.CONDOMINIO_CLIENTE_KEY;
import static com.opencode.myapp.Empresas.fragmentos.EmpacadorFragment.DIRECCION_PEDIDO_KEY;
import static com.opencode.myapp.Empresas.fragmentos.EmpacadorFragment.FECHA_PEDIDO_KEY;
import static com.opencode.myapp.Empresas.fragmentos.EmpacadorFragment.ID_PEDIDO_WEB_KEY;
import static com.opencode.myapp.Empresas.fragmentos.EmpacadorFragment.ID_SESION_EMPAQUE_KEY;
import static com.opencode.myapp.Empresas.fragmentos.EmpacadorFragment.NOMBRE_CLIENTE_KEY;
import static com.opencode.myapp.Empresas.fragmentos.EmpacadorFragment.NOMBRE_VENDEDOR_KEY;
import static com.opencode.myapp.Empresas.fragmentos.EmpacadorFragment.NUM_PEDIDO_PAUSA_KEY;
import static com.opencode.myapp.Empresas.fragmentos.EmpacadorFragment.PEDIDO_PAUSADO_KEY;
import static com.opencode.myapp.Empresas.fragmentos.EmpacadorFragment.REGISTRO_DETALLE_KEY;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfRenderer;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.os.SystemClock;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.opencode.myapp.Empresas.adapters.DetalleRecyclerAdapter;
import com.opencode.myapp.Models.Itemsid;
import com.opencode.myapp.Models.Pedidos;
import com.opencode.myapp.Models.Pedidosd;
import com.opencode.myapp.Models.Presentaciones;
import com.opencode.myapp.Models.PresentacionesHasProductos;
import com.opencode.myapp.Models.Productos;
import com.opencode.myapp.Models.Sesiones;
import com.opencode.myapp.Produccion.fragmentos.FProduccion_Buscar_Pesa;
import com.opencode.myapp.R;
import com.opencode.myapp.config.ApiConf;
import com.opencode.myapp.config.itext.ComandaDoc;
import com.opencode.myapp.config.itext.TicketComandaDoc;
import com.opencode.myapp.config.itext.TicketDoc;
import com.opencode.myapp.config.itext.TicketEmpaqRestaurantDoc;
import com.opencode.myapp.config.session.SessionDatos;
import com.opencode.myapp.config.session.SessionKeys;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.regex.Pattern;

import ZPL.IPort;
import ZPL.ZPLPrinterHelper;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetalleFragment extends Fragment implements FProduccion_Buscar_Pesa.OnInputSelected {
    private RecyclerView recyclerView;
    private DetalleRecyclerAdapter detalleRecyclerAdapter;
    private ArrayList<Pedidosd> listDetalle = new ArrayList<>();
    private SharedPreferences sp;
    private SharedPreferences.Editor edit;
    private SessionDatos sessionDatos;
    private ProgressDialog progressDialog;
    private AlertDialog alertDialog;
    private String pesa_address,readMessage="",timeempq="",empaqRest=""; //00,00#0
    private String r1="",r2="",r3="",r4="",r5="",r6="",r7="",r8="",r9="", r10="";
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private BluetoothAdapter myBluetooth = null;
    private BluetoothSocket btSocket = null;
    public boolean syncpesa,actpesa,isOk=false,isOkPedido=false,isBtConnected=false,fragil=false;
    private double pesajeFin=0, pesotope=0, montototal=0,pesototal=0, pesototalneto=0;
    private int listPos =0,registro =0,idsesionempaque =0,pedidorestaurant=0,
    cajas=0,bolsas=0,cantcomanda=0,numpedidopausa=0,pedidopausa=0;
    private Handler handler = new Handler();
    private Runnable runnable;//, runnable2;
    private Drawable drawable;
    private TextView viewTiempoInicio,viewBackNav,viewTituloBar,viewMenu,viewPausarPedido,viewColTara,viewColPsjeTotal, viewColPesaje;
    private EditText editCajas, editBolsas, editPesoTotal;
    private CheckBox chkFragil;
    private Button btnGuardarDet;
    private Chronometer timer;
    private Timer timercount;
    private List<String> listSpStatus = new ArrayList<>();
    private ComandaDoc comandaDoc;
    private TicketDoc ticketDoc;
    private TicketComandaDoc ticketComandaDoc;
    private String ConnectType="", coduid="";
    private UsbManager mUsbManager=null;
    private UsbDevice device=null;
    private static final String ACTION_USB_PERMISSION = "com.HPRTSDKSample";
    private PendingIntent mPermissionIntent=null;
    private static IPort Printer=null;
    private ZPLPrinterHelper zplPrinterHelper;
    private TicketEmpaqRestaurantDoc ticketEmpaqRestaurantDoc;
    private List<Itemsid> listItems = new ArrayList<>();

    public DetalleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        timercount = new Timer();
        timercount.schedule(new TimerTask() {
            @Override
            public void run() {
                // If you want to modify a view in your Activity
                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DecimalFormat fd = new DecimalFormat("###,###.##");
                            if(detalleRecyclerAdapter != null) {
                                listDetalle = detalleRecyclerAdapter.getListDetalle();
                                montototal =0;
                                pesototal =0;
                                pesototalneto =0;
                                for (Pedidosd item : listDetalle) {
                                    if(item.getCantidadreal() == 0){
                                        pesototalneto = pesototalneto + item.getCantidad();
                                        pesototal = pesototal + item.getCantidad();
                                        montototal = item.getCantidad() * item.getPrecio();
                                    }else {
                                        pesototalneto = pesototalneto + item.getCantidadreal();
                                        pesototal = pesototal + item.getCantidadreal();
                                        montototal = item.getCantidadreal() * item.getPrecio();
                                    }
                                }
                                editPesoTotal.setText(fd.format(pesototal));
                                editPesoTotal.setTextColor(Color.BLACK);
                            }
                        }
                    });
                }
            }
        },
       500,
       500); // initial delay 1 second, interval 1 second
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        //((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true)
        View view = inflater.inflate(R.layout.fragment_detalle, container, false);
        //IMPRESORA
        mPermissionIntent = PendingIntent.getBroadcast(getContext(), 0, new Intent(ACTION_USB_PERMISSION), 0);
        IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
        getContext().registerReceiver(mUsbReceiver, filter);
        zplPrinterHelper = ZPLPrinterHelper.getZPL(getContext());
        conectaImpresoraUSB();
        ticketEmpaqRestaurantDoc = new TicketEmpaqRestaurantDoc(getContext());
        comandaDoc = new ComandaDoc(getContext());
        ticketComandaDoc = new TicketComandaDoc(getContext());
        ticketDoc = new TicketDoc(getContext());
        alertDialog = new AlertDialog.Builder(getContext()).create();
        progressDialog = new ProgressDialog(getContext());
        viewPausarPedido = view.findViewById(R.id.view_det_pausar_pedido);
        viewPausarPedido.setOnClickListener(onClickPausarPedido);
        recyclerView = view.findViewById(R.id.recycler_list_detalle);
        sessionDatos = new SessionDatos(getContext());
        chkFragil = view.findViewById(R.id.chk_det_fragil);
        editCajas = view.findViewById(R.id.edit_det_cajas);
        editBolsas = view.findViewById(R.id.edit_det_bolsas);
        editPesoTotal = view.findViewById(R.id.edit_det_peso_total);
        btnGuardarDet = view.findViewById(R.id.btn_guardar_det);
        btnGuardarDet.setText("CERRAR\nPEDIDO");
        btnGuardarDet.setOnClickListener(onClickGuardarDet);
        timer = view.findViewById(R.id.timer_det_empaque);
        viewTiempoInicio = view.findViewById(R.id.view_det_tiempo_inicio);
        viewTituloBar = view.findViewById(R.id.view_titulo_toolbar_det);
        viewBackNav = view.findViewById(R.id.view_back_nav_detalle);
        viewBackNav.setOnClickListener(onClickBackNav);
        viewTituloBar.setText("Cliente: ??");
        viewMenu = view.findViewById(R.id.view_det_menu);
        //viewMenu.setOnClickListener(onClickMenu);
        viewColTara = view.findViewById(R.id.view_col_tara);
        viewColPsjeTotal = view.findViewById(R.id.view_col_psje_total);
        viewColPesaje = view.findViewById(R.id.view_col_pesaje);
        //**/ 0 = EMPAQUE, 1 = EMPAQE RESTAURANT
        if(sessionDatos.getRecord().get(SessionKeys.empaqueRestaurant).equals("0")){
            empaqRest = "0";
            viewColTara.setVisibility(View.GONE);
            viewColPsjeTotal.setVisibility(View.GONE);
        }else{
            viewColPesaje.setText("Peso NETO");
        }
        if(getArguments() != null){
            r1 = getArguments().getString(REGISTRO_DETALLE_KEY); //R1 = ID_PEDIDO
            r2 = getArguments().getString(NOMBRE_CLIENTE_KEY).toUpperCase(Locale.ROOT);
            r3 = getArguments().getString(ID_SESION_EMPAQUE_KEY);
            r4 = getArguments().getString(ID_PEDIDO_WEB_KEY);//R4=VENDEDOR
            r5 = getArguments().getString(NOMBRE_VENDEDOR_KEY).toUpperCase(Locale.ROOT);
            r6 = getArguments().getString(FECHA_PEDIDO_KEY);
            r7 = getArguments().getString(COMUNA_PEDIDO_KEY);
            r8 = getArguments().getString(DIRECCION_PEDIDO_KEY);
            r9 = getArguments().getString(CATEGORIA_CLIENTE_KEY).toUpperCase(Locale.ROOT);
            if(r9.isEmpty()){
                r9 ="STANDAR";
            }
            coduid = getArguments().getString("CODIGO_UUID");
            numpedidopausa = Integer.parseInt(getArguments().getString(NUM_PEDIDO_PAUSA_KEY));
            pedidopausa = Integer.parseInt(getArguments().getString(PEDIDO_PAUSADO_KEY));
            if(getArguments().getString(CONDOMINIO_CLIENTE_KEY) != null) {
                r10 = getArguments().getString(CONDOMINIO_CLIENTE_KEY).toUpperCase(Locale.ROOT);
            }else{
                r10="SIN DECLARAR";
            }
            cantcomanda = Integer.parseInt(getArguments().getString(CANTIDAD_COMANDA_KEY));
            sessionDatos.IdRegistro(r1);
            registro = Integer.parseInt(r1);
            idsesionempaque = Integer.parseInt(r3);
            viewTituloBar.setText("Cliente: "+r2+" | "+r9+" |  "+"Vendedor: "+r5+"  | "+"Pedido: "+r4);
        }else{
            String s1 = sessionDatos.getRecord().get(SessionKeys.idRegistroPedido);
            registro = Integer.parseInt(s1);
        }
        progressDialog.show();
        progressDialog.setMessage("Cargando datos..");
        loadDetalle(registro);
        if(!sessionDatos.getRecord().get(SessionKeys.pesaMac).equals("")) {
            pesa_address = sessionDatos.getRecord().get(SessionKeys.pesaMac);
            syncpesa=true;
            new ConnectBT().execute();
        }else{
            isOk = true;
        }
        timer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener(){
            @Override
            public void onChronometerTick(Chronometer cArg) {
                long time = SystemClock.elapsedRealtime() - cArg.getBase();
                int h = (int)(time /3600000);
                int m = (int)(time - h*3600000)/60000;
                int s = (int)(time - h*3600000 - m*60000)/1000 ;
                String hh = h < 10 ? "0" +h: h+"";
                String mm = m < 10 ? "0" +m: m+"";
                String ss = s < 10 ? "0" +s: s+"";
                cArg.setText(mm + ":" + ss);
                //
                timeempq=hh+ ":" +mm+ ":" +ss;
            }
        });
        timer.setBase(SystemClock.elapsedRealtime());
        timer.start();
        chkFragil.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    fragil = true;
                }else{
                    fragil = false;
                }
            }
        });
        /**PARAMETRO PESO TOPE*/
        pesotope = Double.parseDouble(sessionDatos.getRecord().get(SessionKeys.pesoTope));
        /***/
        if(sessionDatos.getRecord().get(SessionKeys.empaqueRestaurant).equals("1")){
            editCajas.setEnabled(false);
            editCajas.setTextColor(Color.BLACK);
        }else{
            editCajas.setHint("0");
            cajas =1;
        }
        return view;
    }

    void conectaImpresoraUSB(){
        try {
            if (zplPrinterHelper != null) {
                zplPrinterHelper.PortClose();
            }
            ConnectType = "USB";
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
                                //HPRTPrinter=null;
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

    private View.OnClickListener onClickPausarPedido = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //
            if(numpedidopausa < 3 || pedidopausa == 1) {
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.setTitle("Pausar Pedido");
                alertDialog.setMessage("¿Desea pausar el pedido?");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "PAUSAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //
                        double pesototal = 0.0;
                        if (!editPesoTotal.getText().toString().isEmpty() ) {
                            pesototal = Double.parseDouble(editPesoTotal.getText().toString());
                        }

                        updatePedidos(registro,
                                Integer.parseInt(sessionDatos.getRecord().get(SessionKeys.idOperario)),
                                cajas,
                                bolsas,
                                montototal,
                                pesototal,
                                0,
                                0,
                                1);

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

            } else {
                Toast.makeText(getContext(), "NO PUEDE PAUSAR PEDIDOS SIN HABER TERMINADO AL MENOS UNO (1)", Toast.LENGTH_LONG).show();
            }

        }
    };
 /*
    private View.OnClickListener onClickMenu = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //
            PopupMenu popupMenu = new PopupMenu(getContext(), view);
            popupMenu.getMenuInflater().inflate(R.menu.menu_popup, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.item_gen_comanda:
                            if(cantcomanda >= 1) {
                                alertDialog.setCanceledOnTouchOutside(false);
                                alertDialog.setTitle("Generar Comanda N°: "+(cantcomanda + 1));
                                alertDialog.setMessage("¿Desea generar otra comanda?");
                                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Generar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //
                                        Call<Pedidos> call = ApiConf.getData().getCantComanda(registro, cantcomanda + 1);
                                        call.enqueue(new Callback<Pedidos>() {
                                           @Override
                                           public void onResponse(Call<Pedidos> call, Response<Pedidos> response) {
                                               //
                                               if (response.isSuccessful()) {
                                                   comandaDoc.openDocument(r6, //fecha
                                                           r1, //pedido
                                                           r4, //tpedido
                                                           sessionDatos.getRecord().get(SessionKeys.nombreUsuario), //empacador
                                                           r2, //cliente
                                                           r9,//armado cat cliente
                                                           listDetalle);
                                                   String url_path = comandaDoc.getPathFile();
                                                   Toast.makeText(getContext(), "Documento Generado", Toast.LENGTH_LONG).show();
                                                   cantcomanda = cantcomanda + 1;
                                                }
                                            }
                                            @Override
                                            public void onFailure(Call<Pedidos> call, Throwable t) {
                                                 Log.e("ERROR-->", t.getMessage());
                                            }
                                        });
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
                            } else {
                                Call<Pedidos> call = ApiConf.getData().getCantComanda(registro, 1);
                                call.enqueue(new Callback<Pedidos>() {
                                    @Override
                                    public void onResponse(Call<Pedidos> call, Response<Pedidos> response) {
                                        //
                                        if (response.isSuccessful()) {
                                            comandaDoc.openDocument(r6, //fecha
                                                    r1, //pedido
                                                    r4, //tpedido
                                                    sessionDatos.getRecord().get(SessionKeys.nombreUsuario), //empacador
                                                    r2, //cliente
                                                    r9,//armado cat cliente
                                                    listDetalle);
                                            String url_path = comandaDoc.getPathFile();
                                            Toast.makeText(getContext(), "Comanda Generada", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<Pedidos> call, Throwable t) {
                                        Log.e("ERROR-->", t.getMessage());
                                    }
                                });
                            }
                            return true;
                        case R.id.item_gen_etiqueta:
                            bolsas = 0;
                            cajas = 0;
                            if (!editCajas.getText().toString().isEmpty()) {
                                cajas = Integer.parseInt(editCajas.getText().toString());
                            }
                            if (!editBolsas.getText().toString().isEmpty()) {
                                bolsas = Integer.parseInt(editBolsas.getText().toString());
                            }
                            if (bolsas == 0 && cajas == 0) {
                                alertRango("Empaque", "Debe especificar al menos una (1) Bolsa o Caja.");
                                return false;
                            }

                            //
                            ticketDoc.openDocument(
                                    coduid,
                                    "",
                                    fragil,
                                    cajas,
                                    bolsas,
                                    r1, //id detalle
                                    r4, // id pedido
                                    r7,//comuna
                                    r10,//condominio
                                    r2, //nombre cliente
                                    r8); //direccion
                            String url_path2 = ticketDoc.getPathFile();
                            File pdfFile = new File(url_path2);
                            progressDialog.setMessage("Imprimiendo..");
                            progressDialog.show();
                            List<Bitmap> list_bitmap = pdfToBitmap(pdfFile);
                            new Thread(){
                                @Override
                                public void run() {
                                    super.run();
                                    try{
                                        for(Bitmap bmp: list_bitmap) {
                                            zplPrinterHelper.start();
                                            zplPrinterHelper.printBitmap("60", "60", bmp);
                                            zplPrinterHelper.end();
                                        }
                                        progressDialog.dismiss();
                                    }catch (Exception e){
                                        progressDialog.dismiss();
                                    }
                                }
                            }.start();
                            Toast.makeText(getContext(), "Ticket Generado", Toast.LENGTH_LONG).show();
                            listItems = ticketDoc.getListItems();
                          return true;

                        case R.id.item_gen_com_etq:
                            bolsas = 0;
                            cajas = 0;
                            if(!editCajas.getText().toString().isEmpty()){
                                cajas = Integer.parseInt(editCajas.getText().toString());
                            }
                            if(!editBolsas.getText().toString().isEmpty()){
                                bolsas = Integer.parseInt(editBolsas.getText().toString());
                            }

                            if(bolsas == 0 && cajas == 0){
                                alertRango("Empaque","Debe especificar al menos una (1) Bolsa o Caja.");
                                return false;
                            }

                            ticketComandaDoc.openDocument(
                                    fragil,
                                    cajas,
                                    bolsas,
                                    r6, //fecha
                                    r1, //pedido
                                    r4, //tpedido
                                    sessionDatos.getRecord().get(SessionKeys.nombreUsuario), //empacador
                                    r2, //cliente
                                    r9, //armado cat cliente
                                    listDetalle,
                                    r7,//comuna
                                    r10,//condominio
                                    r8);
                            String url_path3 = ticketComandaDoc.getPathFile();
                            Toast.makeText(getContext(), "Ticket y Comanda Generada", Toast.LENGTH_LONG).show();
                            return true;
                        default:
                          return false;
                    }
                }
            });
            popupMenu.show();
        }
    };
*/
    private View.OnClickListener onClickGuardarDet = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //
            DecimalFormat fd = new DecimalFormat("###,###.##");
            if(!sessionDatos.getRecord().get(SessionKeys.empaqueRestaurant).equals("1")){
                /**VERIFICAR RANGOS DE PESO CORRECTOS*/
                for(Pedidosd item_pedidod: listDetalle){
                    Productos item_prod = item_pedidod.getProductos();
                    PresentacionesHasProductos item_presprod = item_pedidod.getPreshasprod();
                    if(item_presprod.getRendimiento() > 0 && item_pedidod.getAnulado() == 0) {
                        if (item_pedidod.getCantidadreal() <= item_pedidod.getMinPeso()) {
                            alertRango("Cantidad Real menor al rango de pesaje",
                                    "El código: " + item_pedidod.getCodigo() + " es menor al rango de peso: " + fd.format(item_pedidod.getMinPeso()) +
                                            " de pesaje establecido.\nEste debe ser mayor.");
                            return;
                        }

                        if (item_pedidod.getCantidadreal() >= item_pedidod.getMaxPeso()) {
                            alertRango("Cantidad Real mayor al rango de pesaje",
                                    "El código: " + item_pedidod.getCodigo() + " es mayor al rango: " + fd.format(item_pedidod.getMaxPeso()) +
                                            " de pesaje establecido.\nEste debe ser menor.");
                            return;
                        }
                    }
                }
            }
            else
            {
                if(pesototal <= 0 && sessionDatos.getRecord().get(SessionKeys.empaqueRestaurant).equals("0")){
                    Toast.makeText(getContext(), "EL PESO TOTAL DEBE SER MAYOR A CERO (0)\nPARA CERRAR PEDIDO", Toast.LENGTH_LONG).show();
                    return;
                }
            }
            //
            if(!editCajas.getText().toString().isEmpty()){
                cajas = cajas + Integer.parseInt(editCajas.getText().toString());
            }
            if(!editBolsas.getText().toString().isEmpty()){
                bolsas = Integer.parseInt(editBolsas.getText().toString());
            }
            if(bolsas == 0){
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.setTitle("Sin Bolsas");
                alertDialog.setMessage("¿Esta seguro que el pedido va sin bolsa?.");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Cerrar Pedido", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cierraPedido();
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
                //
            }else{
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.setTitle("Cerrar Pedido");
                alertDialog.setMessage("¿Esta seguro de cerrar pedido?");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /***/
                        cierraPedido();
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
        }
    };

    void cierraPedido(){
        progressDialog.setMessage("Cerrando pedido..");
        progressDialog.show();
        /**ANULAR PRODUCTO EN PEDIDOD*/
        for(Pedidosd item_pedidod: listDetalle) {
            if (item_pedidod.getAnulado() > 0) {
                updatePedidosd(registro,
                        item_pedidod.getCodigo(),
                        Double.parseDouble(item_pedidod.getTara()),
                        item_pedidod.getDescuento1(),
                        item_pedidod.getCantidadreal(),
                        item_pedidod.getCabeza(),
                        item_pedidod.getEsquelon(),
                        item_pedidod.getAnulado(),
                        item_pedidod.getObs());
            }else{
                updatePedidosd(registro,
                        item_pedidod.getCodigo(),
                        Double.parseDouble(item_pedidod.getTara()),
                        item_pedidod.getDescuento1(),
                        item_pedidod.getCantidadreal(),
                        item_pedidod.getCabeza(),
                        item_pedidod.getEsquelon(),
                        item_pedidod.getAnulado(),
                        item_pedidod.getObs());
            }
        }
        double pesototal = 0.0;
        if(!editPesoTotal.getText().toString().isEmpty()){
            pesototal = Double.parseDouble(editPesoTotal.getText().toString());
        }

        if(sessionDatos.getRecord().get(SessionKeys.empaqueRestaurant).equals("1")){
            updatePedidos(registro,
                    Integer.parseInt(sessionDatos.getRecord().get(SessionKeys.idOperario)),
                    cajas,
                    bolsas,
                    montototal,
                    0,
                    pesototalneto,
                    1,
                    0);
        } else {
            updatePedidos(registro,
                    Integer.parseInt(sessionDatos.getRecord().get(SessionKeys.idOperario)),
                    cajas,
                    bolsas,
                    montototal,
                    pesototal,
                    0,
                    1,
                    0);
        }
    }

    private View.OnClickListener onClickBackNav = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setTitle("Salir a empaque");
            alertDialog.setMessage("¿Desea salir?");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //
                    /**ELIMINA SESION SIN GUARDAR/FINALIZAR*/
                    getSesionEmpaque(Integer.parseInt(sessionDatos.getRecord().get(SessionKeys.idOperario)),
                            0,
                            idsesionempaque,
                            2,
                            "");
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
    };

    void loadListDetalle(){
        /***/
        progressDialog.dismiss();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        detalleRecyclerAdapter = new DetalleRecyclerAdapter(getContext(), listDetalle, empaqRest);
        detalleRecyclerAdapter.setOnClickListener(new DetalleRecyclerAdapter.OnClickListener() {
            @Override
            public void onEditar(View view, final int position) {
                final Pedidosd item = listDetalle.get(position);
                Productos item_prod = item.getProductos();
                final PresentacionesHasProductos item_preshasprod = item.getPreshasprod();
                if(sessionDatos.getRecord().get(SessionKeys.empaqueRestaurant).equals("1")){
                    if(Double.parseDouble(item.getTara()) <= 0){
                        Toast.makeText(getContext(), "PRIMERO DEBE PESAR TARA MAYOR A CERO(0)", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                handler.removeCallbacks(runnable);
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        ArrayList<Pedidosd> listP = new ArrayList<>();
                        handler.postDelayed(this, 1000);
                        for(Pedidosd p: listDetalle){
                            listP.add(p.clone());
                        }
                        try {
                            //filtra signo = dado por la balanza, si no es number atrapa exepcion
                            double pesa = Double.parseDouble(readMessage);
                            double pesoteorico =0;
                            double minPeso =0;
                            double maxPeso =0;
                            double pesototalfila =0;
                            if(sessionDatos.getRecord().get(SessionKeys.empaqueRestaurant).equals("1")){
                                pesototalfila = Double.parseDouble(item.getTara()) + pesa;
                                listP.get(position).setPesototalfila(String.valueOf(pesototalfila));
                                pesa = pesa - Double.parseDouble(item.getTara());
                                listP.get(position).setReadPesaje(String.valueOf(pesa));
                                listP.get(position).setCantidadreal(pesa);
                            }
                            else
                            {
                                listP.get(position).setReadPesaje(String.valueOf(pesa));;
                                listP.get(position).setCantidadreal(pesa);
                            }
                            pesoteorico = item.getCantidad() * (1 - (item_preshasprod.getRendimiento() / 100));
                            minPeso = pesoteorico * 0.9;
                            maxPeso = pesoteorico * 1.1 ;
                            maxPeso = maxPeso + (maxPeso * (pesotope / 100));
                            listP.get(position).setMinPeso(minPeso);
                            listP.get(position).setMaxPeso(maxPeso);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                        detalleRecyclerAdapter.updatePedidosd(listP);
                    }
                };
                handler.postDelayed(runnable, 0);
                /***/
                if(!syncpesa || isOk){
                    //
                    FProduccion_Buscar_Pesa newFragment2 = new FProduccion_Buscar_Pesa();
                    Bundle bundle = new Bundle();
                    newFragment2.setArguments(bundle);
                    newFragment2.setTargetFragment(DetalleFragment.this, 1);
                    newFragment2.show(getFragmentManager(), "Dialog");
                }
            }
            @Override
            public void onTara(View view, int position) {
                final Pedidosd item = listDetalle.get(position);
                Productos item_prod = item.getProductos();
                final PresentacionesHasProductos item_preshasprod = item.getPreshasprod();
                handler.removeCallbacks(runnable);
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        ArrayList<Pedidosd> listP = new ArrayList<>();
                        handler.postDelayed(this, 1000);
                        for(Pedidosd p: listDetalle){
                            listP.add(p.clone());
                        }
                        try {
                            //filtra signo = dado por la balanza, si no es number atrapa exepcion
                            double pesa = Double.parseDouble(readMessage);
                            listP.get(position).setTara(String.valueOf(pesa));
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                        detalleRecyclerAdapter.updatePedidosd(listP);
                    }
                };
                handler.postDelayed(runnable, 0);
                /***/
                if(!syncpesa || isOk){
                    FProduccion_Buscar_Pesa newFragment2 = new FProduccion_Buscar_Pesa();
                    Bundle bundle = new Bundle();
                    newFragment2.setArguments(bundle);
                    newFragment2.setTargetFragment(DetalleFragment.this, 1);
                    newFragment2.show(getFragmentManager(), "Dialog");
                }
            }
        });
        recyclerView.setAdapter(detalleRecyclerAdapter);
        /**SIRVE PARA QUE NO DESAPARESCAN LOS ICONOS AL HACER SCROLL AL RECYCLER..*/
        recyclerView.getRecycledViewPool().setMaxRecycledViews(0, 0);
    }

    private void postItemPedido(int pedidosregistro,
                                String codigo,
                                String tipoitem){
        Map<String, Object> jsonArrayMap = new ArrayMap<>();
        jsonArrayMap.put("Pedidosregistro", pedidosregistro);
        jsonArrayMap.put("Codigo", codigo);
        jsonArrayMap.put("Tipoitem", tipoitem);

        RequestBody body = RequestBody.create(MediaType
                .parse("application/json; charset=utf-8"), (new JSONObject(jsonArrayMap)).toString());

        Call<Itemsid> call = ApiConf.getData().postItemsPedido(body);
        call.enqueue(new Callback<Itemsid>() {
            @Override
            public void onResponse(Call<Itemsid> call, Response<Itemsid> response) {
                if (response.isSuccessful()) {
                    //
                    Log.e("respuestaOK==>", String.valueOf(response.body()));
                } else {
                    Log.e("respuestaErr==>", String.valueOf(response.errorBody()));
                }
            }

            @Override
            public void onFailure(Call<Itemsid> call, Throwable t) {
                Log.e("failureInst==>", t.toString());
            }
        });
    }

    //**UPDATE PEDIDOS*/
    private void updatePedidos(int registro,
                               int idoperario,
                               int cajas,
                               int bolsas,
                               double montototal,
                               double pesototal,
                               double pesototalneto,
                               int estado,
                               int pedidopausa){
        Map<String, Object> jsonArrayMap = new ArrayMap<>();
        jsonArrayMap.put("Cajas", cajas);
        jsonArrayMap.put("Bolsas", bolsas);
        jsonArrayMap.put("Total", montototal);
        jsonArrayMap.put("Pesototal", pesototal);
        jsonArrayMap.put("Pesototalneto", pesototalneto);
        jsonArrayMap.put("Estado", estado);
        jsonArrayMap.put("Pedidopausa", pedidopausa);
        RequestBody body = RequestBody.create(MediaType
                .parse("application/json; charset=utf-8"), (new JSONObject(jsonArrayMap)).toString());
        Call<Pedidos> call = ApiConf.getData().postPedido(registro, idoperario, body);
        call.enqueue(new Callback<Pedidos>() {
            @Override
            public void onResponse(Call<Pedidos> call, Response<Pedidos> response) {
                /***/
                if (response.isSuccessful()) {
                    Pedidos result = response.body();
                    for(Pedidosd item_pedidod: listDetalle) {
                        updatePedidosd(registro,
                                item_pedidod.getCodigo(),
                                Double.parseDouble(item_pedidod.getTara()),
                                item_pedidod.getDescuento1(),
                                item_pedidod.getCantidadreal(),
                                item_pedidod.getCabeza(),
                                item_pedidod.getEsquelon(),
                                item_pedidod.getAnulado(),
                                item_pedidod.getObs());
                    }
                    handler.removeCallbacks(runnable);
                    if(btSocket != null) {
                        try {
                            btSocket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    progressDialog.dismiss();
                    File pdfFile = null;
                    /**IMPRIME TICKET RESTAURANT*/
                    if(sessionDatos.getRecord().get(SessionKeys.empaqueRestaurant).equals("1")){
                        //
                        ticketEmpaqRestaurantDoc.openDocument(
                                r9, //CATEG.CLIENTE
                                result.getFechaelab(),
                                pesototalneto,
                                listDetalle,
                                fragil,
                                cajas,
                                bolsas,
                                String.valueOf(registro), //id detalle
                                r4, // id pedido
                                r7,//comuna
                                r10,//condominio
                                r2, //nombre cliente
                                r8); //direccion
                        //listItems =
                        String url_path2 = ticketEmpaqRestaurantDoc.getPathFile();
                        pdfFile = new File(url_path2);
                        listItems = ticketEmpaqRestaurantDoc.getListItems();
                    }
                    else
                    {
                        //
                        if(cajas > 1 || bolsas > 0) {
                            ticketDoc.openDocument(
                                    "",
                                    coduid, //UUID ENTRADA PARA EL REGISTRO DE CIERRE
                                    "",
                                    fragil,
                                    cajas,
                                    bolsas,
                                    r1, //id pedido
                                    r4, // vendedor
                                    r7,//comuna
                                    r10,//condominio
                                    r2, //nombre cliente
                                    r8); //direccion
                            String url_path2 = ticketDoc.getPathFile();
                            pdfFile = new File(url_path2);
                            listItems = ticketDoc.getListItems();
                            //
                        }else{
                            Itemsid itemsid = new Itemsid();
                            itemsid.setCodigo(coduid);
                            itemsid.setTipoitem("CAJA");
                            itemsid.setPedidosregistro(Integer.parseInt(r1));
                            listItems.add(itemsid);
                        }
                    }
                    //
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
                    //POST ITEMS
                    for(Itemsid itemid: listItems){
                        postItemPedido(itemid.getPedidosregistro(),
                                itemid.getCodigo(),
                                itemid.getTipoitem());
                    }
                    EmpacadorFragment newFragment = new EmpacadorFragment();
                    FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                    fm.replace(R.id.frame_empresas, newFragment);
                    fm.commit();
                    if(pedidopausa == 0){
                        /* POR SI cliente SE ARREPIENTE Y QUIERE VOLVER A IMPRIMIR EN SALIDA COMANDA
                        comandaDoc.openDocument(r6, //fecha
                                r1, //pedido
                                r4, //tpedido
                                sessionDatos.getRecord().get(SessionKeys.nombreUsuario), //empacador
                                r2, //cliente
                                r9,//armado cat cliente
                                listDetalle);
                        String file_path = comandaDoc.getPathFile();
                        File pdfFile = new File(file_path);
                        progressDialog.setMessage("Imprimiendo..");
                        progressDialog.show();
                        List<Bitmap> list_bitmap = pdfToBitmap(pdfFile);
                        new Thread(){
                            @Override
                            public void run() {
                                super.run();
                                    try{
                                        for(Bitmap bmp: list_bitmap) {
                                            zplPrinterHelper.start();
                                            zplPrinterHelper.printBitmap("60", "60", bmp);
                                            zplPrinterHelper.end();
                                        }
                                        progressDialog.dismiss();
                                    }catch (Exception e){
                                        progressDialog.dismiss();
                                    }
                            }
                        }.start();

                        Toast.makeText(getContext(), "PEDIDO CERRADO.\nCOMANDA GENERADA..", Toast.LENGTH_LONG).show();
                        */

                    }else{
                        Toast.makeText(getContext(), "PEDIDO PAUSADO..", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Log.e("respuestaErr==>", String.valueOf(response.errorBody()));
                }

            }

            @Override
            public void onFailure(Call<Pedidos> call, Throwable t) {
                Log.e("failureInst==>", t.toString());
                Toast.makeText(getContext(), "ERROR..", Toast.LENGTH_LONG).show();
            }
        });
    }

    //**UPDATE PEDIDOS DETALLE*/
    private void updatePedidosd(int registro,
                                int codigo,
                                double tara,
                                double desc,
                                double cantreal,
                                short cabeza,
                                short esquelon,
                                short anular,
                                String obs){
        Map<String, Object> jsonArrayMap = new ArrayMap<>();
        jsonArrayMap.put("Tara", tara);
        jsonArrayMap.put("Descuento1", desc);
        jsonArrayMap.put("Cantidadreal", cantreal);
        jsonArrayMap.put("Cabeza", cabeza);
        jsonArrayMap.put("Esquelon", esquelon);
        jsonArrayMap.put("Anulado",anular);
        jsonArrayMap.put("Obs", obs);

        RequestBody body = RequestBody.create(MediaType
                .parse("application/json; charset=utf-8"), (new JSONObject(jsonArrayMap)).toString());

        Call<Pedidosd> call = ApiConf.getData().postPedidod(registro, codigo, body);
        call.enqueue(new Callback<Pedidosd>() {
            @Override
            public void onResponse(Call<Pedidosd> call, Response<Pedidosd> response) {
                if (response.isSuccessful()) {
                    Pedidosd result = response.body();
                    //
                    Log.e("respuestaOK==>", String.valueOf(response.body()));

                } else {
                    Log.e("respuestaErr==>", String.valueOf(response.errorBody()));
                }

            }

            @Override
            public void onFailure(Call<Pedidosd> call, Throwable t) {
                Log.e("failureInst==>", t.toString());
            }
        });

    }

    private void alertRango(String title, String msg){
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    /**FINALIZAR TURNO*/
    private void getSesionEmpaque(int idusuario, int idpedido, int idsesionempaque, final int status, String tiempoempaque){
        Call<Sesiones> call = ApiConf.getData().getSesionEmpaque(0, idusuario, idpedido, idsesionempaque, status, tiempoempaque);
        call.enqueue(new Callback<Sesiones>() {
            @Override
            public void onResponse(Call<Sesiones> call, Response<Sesiones> response) {
                if (response.isSuccessful()) {
                    Sesiones sesiones = response.body();
                    EmpacadorFragment newFragment = new EmpacadorFragment();
                    FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                    fm.replace(R.id.frame_empresas, newFragment);
                    fm.commit();
                    handler.removeCallbacks(runnable);
                    if(btSocket != null) {
                        try {
                            btSocket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if(status <= 1) {
                        Toast.makeText(getContext(), "Guardado..", Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<Sesiones> call, Throwable t) {
                Log.e("ERROR--->", t.toString());
            }
        });
    }

    @Override
    public void sendStatus(String mac) {
        if(!mac.equals("0")) {
            pesa_address = mac;
            sessionDatos.setPesa(mac);
            new ConnectBT().execute();
        }else{
            //actpesa = false;
            isOk = true;
            sessionDatos.pesaOff();
        }
    }

    private class ConnectBT extends AsyncTask<Void, Void, Void> {
        //
        private boolean ConnectSuccess = true; //if it's here, it's almost connected
        @Override
        protected void onPreExecute()
        {
            progressDialog.setCancelable(false);
            progressDialog.show();
            progressDialog.setMessage("Conectando balanza..");
        }

        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {
            try
            {
                if (btSocket == null || !isBtConnected)
                {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(pesa_address);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect(); //start connection
                    receiveData();
                }else{
                    progressDialog.dismiss();
                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;//if the try failed, you can check the exception here
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
        {   super.onPostExecute(result);
            if (!ConnectSuccess)  {
                actpesa = false;
                isOk = true;
                sessionDatos.pesaOff();
                sessionDatos.setPesa("");
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Dispositivo desconectado o apagado, vuelva a intentar.", Toast.LENGTH_LONG).show();
            } else {
                //msg("Conectado..");
                isBtConnected = true;
                //syncpesa=true;
                //Toast.makeText(getContext(), "Balanza conectada..", Toast.LENGTH_LONG).show();
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void receiveData() throws IOException{
        //startThread();
        isOk=false;
        progressDialog.dismiss();
        InputStream socketInputStream = btSocket.getInputStream();
        byte[] buffer = new byte[256];
        int bytes;
        syncpesa=true;
        // Keep looping to listen for received messages
        while (syncpesa) {
            try {
                bytes = socketInputStream.read(buffer); //read bytes from input buffer
                String readMess = new String(buffer, 0, bytes);
                String[] parts = readMess.split(Pattern.quote("+"));
                if(parts.length > 1) {
                    String pesopart = parts[1];
                    String[] parts2 = pesopart.split("kg\r\n");
                    String pesopart2 = parts2[0];
                    readMessage = pesopart2;
                }else{
                    readMessage = readMess;
                }
            } catch (IOException e) {
                break;
            }
        }
    }

    private void loadDetalle(int registro){
        Call<List<Pedidosd>> call = ApiConf.getData().getPedidosdDetalle(registro);
        call.enqueue(new Callback<List<Pedidosd>>() {
            @Override
            public void onResponse(Call<List<Pedidosd>> call, Response<List<Pedidosd>> response) {
                if (response.isSuccessful()) {
                    List<Pedidosd> result = response.body();
                    listDetalle.addAll(result);
                    loadListDetalle();
                    if(sessionDatos.getRecord().get(SessionKeys.empaqueRestaurant).equals("1")) {
                        editCajas.setText("" + listDetalle.size());
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Pedidosd>> call, Throwable t) {
                Log.e("ERROR-->", t.getMessage());
            }
        });
    }

    private ArrayList<Bitmap> pdfToBitmap(File pdfFile) {
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