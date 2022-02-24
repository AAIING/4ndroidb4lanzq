package com.opencode.myapp.Empresas.fragmentos;

import static com.opencode.myapp.Empresas.fragmentos.DetalleFragment.CANT_PROD_KEY;
import static com.opencode.myapp.Empresas.fragmentos.DetalleFragment.CANT_REAL_KEY;
import static com.opencode.myapp.Empresas.fragmentos.DetalleFragment.DESC_PROD_KEY;
import static com.opencode.myapp.Empresas.fragmentos.DetalleFragment.NOMBRE_PROD_KEY;
import static com.opencode.myapp.Empresas.fragmentos.DetalleFragment.OBS_PROD_KEY;
import static com.opencode.myapp.Empresas.fragmentos.DetalleFragment.OTROS_CABEZA_KEY;
import static com.opencode.myapp.Empresas.fragmentos.DetalleFragment.OTROS_ESQUELON_KEY;
import static com.opencode.myapp.Empresas.fragmentos.DetalleFragment.PRESENT_PROD_KEY;
import static com.opencode.myapp.Empresas.fragmentos.DetalleFragment.TIPO_UNIDAD_KEY;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.opencode.myapp.Models.Pedidosd;
import com.opencode.myapp.Produccion.fragmentos.FProduccion;
import com.opencode.myapp.Produccion.fragmentos.FProduccion_Buscar_Pesa;
import com.opencode.myapp.R;
import com.opencode.myapp.config.ApiConf;
import com.opencode.myapp.config.session.SessionDatos;
import com.opencode.myapp.config.session.SessionKeys;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Map;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ModificarDetalleFragment extends Fragment implements FProduccion_Buscar_Pesa.OnInputSelected {

    private Context context;
    private TextView viewNomProducto, viewCantidad, viewPresentacion, viewUm1, viewUm2, viewActivaPesa;
    private EditText editObs, editCantReal, editDescuento;
    private CheckBox chkCabeza, chkEsquelon, chkAnular;
    private Button btnGuardar;
    private int anular =0, cabeza=0, esquelon=0, registro=0;
    private boolean actpesa =false, isOk = false;;
    private SessionDatos sessionDatos;
    private String  readMessage =""; //00,00#0
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private BluetoothAdapter myBluetooth = null;
    private BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    private String  pesa_address;
    public boolean syncpesa;
    //public ModificarDetalleFragment.ConnectBT LectorPesa = null;
    private Handler handler = new Handler();
    private Runnable runnable;
    private ProgressDialog progressDialog;
    private FProduccion_Buscar_Pesa fProduccion_buscar_pesa;

    public ModificarDetalleFragment() {
        // Required empty public constructor
    }

    public ModificarDetalleFragment(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Modificar detalle");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        View view = inflater.inflate(R.layout.fragment_modificar_detalle, container, false);
        setViews(view);

        startThread();

        progressDialog = new ProgressDialog(getContext());
        sessionDatos = new SessionDatos(getContext());
        registro = Integer.parseInt(sessionDatos.getRecord().get(SessionKeys.idRegistroPedido));
        myBluetooth = BluetoothAdapter.getDefaultAdapter();

        if(getArguments() != null){
            viewNomProducto.setText(getArguments().getString(NOMBRE_PROD_KEY));   //
            viewCantidad.setText(getArguments().getString(CANT_PROD_KEY)); //
            viewPresentacion.setText(getArguments().getString(PRESENT_PROD_KEY)); //
            editCantReal.setText(getArguments().getString(CANT_REAL_KEY));
            editDescuento.setText(getArguments().getString(DESC_PROD_KEY)); //
            viewUm1.setText(getArguments().getString(TIPO_UNIDAD_KEY)); //
            viewUm2.setText(getArguments().getString(TIPO_UNIDAD_KEY)); //
            editObs.setText(getArguments().getString(OBS_PROD_KEY)); //

            cabeza = Integer.parseInt(getArguments().getString(OTROS_CABEZA_KEY));
            if(getArguments().getString(OTROS_CABEZA_KEY).equals("1")){
                chkCabeza.setChecked(true);
            } else if(getArguments().getString(OTROS_CABEZA_KEY).equals("0")){
                chkCabeza.setChecked(false);
            }

            esquelon = Integer.parseInt(getArguments().getString(OTROS_ESQUELON_KEY));
            if(getArguments().getString(OTROS_ESQUELON_KEY).equals("1")){
                chkEsquelon.setChecked(true);
            } else if(getArguments().getString(OTROS_ESQUELON_KEY).equals("0")){
                chkEsquelon.setChecked(false);
            }
        }

        chkCabeza.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    cabeza = 1;
                }else{
                    cabeza = 0;
                }
            }
        });

        chkEsquelon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    esquelon = 1;
                }else{
                    esquelon = 0;
                }
            }
        });

        chkAnular.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    anular = 1;
                }else{
                    anular = 0;
                }
            }
        });


        if(!sessionDatos.CheckPesa()) {
            actpesa = false;
            viewActivaPesa.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_red_monitor_weight_24, 0);
            editCantReal.setEnabled(true);
            editCantReal.setText("");
        }
        else
        {
            actpesa = true;
            viewActivaPesa.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_green_monitor_weight_24, 0);
            editCantReal.setEnabled(false);
            editCantReal.setText("");
            //
            //if(btSocket == null || !isBtConnected) {
                pesa_address = sessionDatos.getRecord().get(SessionKeys.pesaMac);
                new ConnectBT().execute();
            //}
        }

        return view;
    }

    /*
    public void setDisabled(){
        actpesa = false;
        viewActivaPesa.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_red_monitor_weight_24, 0);
        editCantReal.setEnabled(true);
        editCantReal.setText("");
        sessionDatos.pesaOff();
    }
    */

    private View.OnClickListener onClickGuardar = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //
            String desc = "0", cantreal = "0";
            if(!editDescuento.getText().toString().isEmpty()){
                desc = editDescuento.getText().toString();
            }
            if(!editCantReal.getText().toString().isEmpty()){
                cantreal = editCantReal.getText().toString();
            }
            if(!editCantReal.getText().toString().isEmpty()){
                cantreal = editCantReal.getText().toString();
            }
            //
            if(!cantreal.equals("0") && !cantreal.equals("=")) {
                //final
                Map<String, Object> jsonArrayMap = new ArrayMap<>();
                jsonArrayMap.put("Descuento1", desc);
                jsonArrayMap.put("Cantidadreal", cantreal);
                jsonArrayMap.put("Cabeza", String.valueOf(cabeza));
                jsonArrayMap.put("Esquelon", String.valueOf(esquelon));
                jsonArrayMap.put("Anulado", String.valueOf(anular));
                jsonArrayMap.put("Obs", editObs.getText().toString());

                RequestBody body = RequestBody.create(MediaType
                        .parse("application/json; charset=utf-8"), (new JSONObject(jsonArrayMap)).toString());

                Call<Pedidosd> call = ApiConf.getData().postPedidod(registro, body);
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

                        //Toast.makeText(getContext(), "Guardado..", Toast.LENGTH_LONG).show();
                        isOk = true;
                    }

                    @Override
                    public void onFailure(Call<Pedidosd> call, Throwable t) {
                        Log.e("failureInst==>", t.toString());
                        isOk = false;
                    }
                });

                if(isOk){
                    Toast.makeText(getContext(), "Guardado..", Toast.LENGTH_LONG).show();
                    DetalleFragment newFragment = new DetalleFragment();
                    FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                    fm.replace(R.id.frame_empresas, newFragment);
                    fm.commit();
                }else{
                    Toast.makeText(getContext(), "Error al guardar.. compruebe conexión..", Toast.LENGTH_LONG).show();
                }
            }else{
                if(cantreal.equals("0")){
                    Toast.makeText(getContext(), "Debe ingresar una cantidad real mayor a 0", Toast.LENGTH_LONG).show();
                }
            }
        }
    };

    private View.OnClickListener onClickActivaPesa = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(actpesa)
            {
                actpesa = false;
                syncpesa=false;
                viewActivaPesa.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_red_monitor_weight_24, 0);
                editCantReal.setEnabled(true);
                btSocket = null;
                readMessage ="";
                editCantReal.setText("");
                sessionDatos.pesaOff();
                myBluetooth.disable();

                /*
                try {
                    btSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                */
            }
            else
            {
                actpesa = true;
                viewActivaPesa.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_green_monitor_weight_24, 0);
                editCantReal.setEnabled(false);
                editCantReal.setText("");
                //
                //startThread();
                FProduccion_Buscar_Pesa newFragment = new FProduccion_Buscar_Pesa();
                Bundle bundle = new Bundle();
                newFragment.setArguments(bundle);
                newFragment.setTargetFragment(ModificarDetalleFragment.this, 1);
                newFragment.show(getFragmentManager(), "Dialog");
            }
        }
    };

    public void setViews(View view){
        editDescuento = view.findViewById(R.id.view_mod_det_descuento);
        editCantReal = view.findViewById(R.id.edit_cant_real);
        viewActivaPesa = view.findViewById(R.id.view_activa_pesa);
        viewActivaPesa.setOnClickListener(onClickActivaPesa);
        viewUm1 = view.findViewById(R.id.view_cant_unm_1);
        viewUm2 = view.findViewById(R.id.view_cant_unm_2);
        viewNomProducto = view.findViewById(R.id.view_mod_det_nombre);
        viewCantidad = view.findViewById(R.id.view_mod_det_cantidad);
        viewPresentacion = view.findViewById(R.id.view_mod_det_present);
        editObs = view.findViewById(R.id.edit_observacion_item);
        chkCabeza = view.findViewById(R.id.chk_det_cabeza);
        chkEsquelon = view.findViewById(R.id.chk_det_esquelon);
        chkAnular = view.findViewById(R.id.chk_det_anular);
        btnGuardar = view.findViewById(R.id.btn_det_guardar);
        btnGuardar.setOnClickListener(onClickGuardar);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //
                ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
                DetalleFragment newFragment = new DetalleFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frame_empresas, newFragment);
                fm.commit();

                if(btSocket != null) {
                    try {
                        btSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                return true;
            case R.id.action_agregar:
                //
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void sendStatus(String mac) {
        if(!mac.equals("0")) {
            pesa_address = mac;
            sessionDatos.setPesa(mac);
            //AsyncTask LectorPesa = new ModificarDetalleFragment.ConnectBT(mac).execute();
            new ConnectBT().execute();
        }else{
            actpesa = false;
            viewActivaPesa.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_red_monitor_weight_24, 0);
            editCantReal.setEnabled(true);
            editCantReal.setText("");
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
                    //
                    receiveData(); //
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
                //msg("Conexion fallida..");
                //finish();

                actpesa = false;
                viewActivaPesa.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_red_monitor_weight_24, 0);
                editCantReal.setEnabled(true);
                editCantReal.setText("");
                sessionDatos.pesaOff();

                Toast.makeText(getContext(), "Dispositivo desconectado o apagado, vuelva a intentar.", Toast.LENGTH_LONG).show();

                progressDialog.dismiss();

            } else {
                //msg("Conectado..");
                isBtConnected = true;
            }
        }
    }

    public void receiveData() throws IOException{
        startThread();
        progressDialog.dismiss();
        InputStream socketInputStream =  btSocket.getInputStream();
        byte[] buffer = new byte[256];
        int bytes;

        syncpesa=true;
        // Keep looping to listen for received messages
        while (syncpesa) {
            try {
                bytes = socketInputStream.read(buffer); //read bytes from input buffer
                readMessage = new String(buffer, 0, bytes);
                // Send the obtained bytes to the UI Activity via handler
                Log.e("DATOS--->", readMessage + "");
            } catch (IOException e) {
                Log.e("ERROR HILO--->",  e.getMessage() + "");
                break;
            }
        }
    }

    //actualiza el dato en el UI
    public void startThread(){
        //
        runnable = new Runnable() {
            @Override
            public void run() {
            handler.postDelayed(this, 1000);
                if(actpesa) {
                    editCantReal.setText("" + readMessage);
                }
            }
        };
        handler.postDelayed(runnable, 0);
    }

}