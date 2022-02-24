package com.opencode.myapp.Produccion.fragmentos;

import android.app.AlertDialog;
import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.SystemClock;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.opencode.myapp.Empresas.Empresas;
import com.opencode.myapp.Produccion.Produccion;
import com.opencode.myapp.R;
import com.opencode.myapp.Variedades.Variedades_arandano;
import com.opencode.myapp.Variedades.Variedades_cereza;
import com.opencode.myapp.bdsqlite.DBDatos;
import com.opencode.myapp.bdsqlite.DBMetodos;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.UUID;

public class FProduccion extends Fragment implements FProduccion_Buscar_Pesa.OnInputSelected {

    private BluetoothAdapter myBluetooth = null;
    private BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private String  readMessage ="00,00#0";
    private String  pesa_address;

    private Handler handler = new Handler();
    private Runnable runnable;

    public boolean syncpesa;

    public ConnectBT LectorPesa = null;

    public String[] telefonos = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };
    public String telefono="0";
    public String cuartel ="00";

    private FrameLayout frame_produccion_buscar_pesa;
    private RadioGroup radio_grp_cuartel;
    private RadioButton radio_btn_cuartel_01,radio_btn_cuartel_02,radio_btn_cuartel_03;
    private Button btn_activarcamara,btn_capturar,btn_buscar_pesa,btn_volver;
    private EditText dialog_telefono,edt_operario,edt_producto,edt_variedad,edt_cantidad;
    private TextView btn_restar,btn_sumar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.fragment_produccion, container, false);

        final Produccion moo = (Produccion) getActivity();
        final String empresa = moo.getmenu();
        final String idsinc = moo.getidsinc();
        final String producto = moo.getproducto();
        final String nomproducto = moo.getnomproducto();
        final String variedad = moo.getsubproducto();
        final String nomvariedad = moo.getnomsubproducto();

        myBluetooth = BluetoothAdapter.getDefaultAdapter();

        dialog_telefono=view.findViewById(R.id.edt_telefono);
        dialog_telefono.setKeyListener(null);
        dialog_telefono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectorTelefonoDialog(dialog_telefono);
            }
        });

        radio_btn_cuartel_01=view.findViewById(R.id.radio_btn_cuartel_01);
        radio_btn_cuartel_02=view.findViewById(R.id.radio_btn_cuartel_02);
        radio_btn_cuartel_03=view.findViewById(R.id.radio_btn_cuartel_03);


        btn_activarcamara=view.findViewById(R.id.btn_ActivarCamara);
        btn_activarcamara.setText("("+idsinc+")"+"LEER CODIGO DE BARRA");
        edt_operario=view.findViewById(R.id.edt_Operario);
        edt_operario.setFocusable(false);

        edt_producto=view.findViewById(R.id.edt_Producto);
        edt_producto.setFocusable(false);
        edt_producto.setText(nomproducto);

        edt_variedad=view.findViewById(R.id.edt_Variedad);
        edt_variedad.setFocusable(false);
        edt_variedad.setText(nomvariedad);

        edt_cantidad=view.findViewById(R.id.edt_Cantidad);
        edt_cantidad.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        int maxLengthofEditText = 5;
        edt_cantidad.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLengthofEditText)});
        
        btn_restar=view.findViewById(R.id.btn_Restar);
        btn_sumar=view.findViewById(R.id.btn_Sumar);
        btn_capturar=view.findViewById(R.id.btn_Capturar);
        btn_buscar_pesa=view.findViewById(R.id.btn_Buscar_Pesa);

        btn_volver=view.findViewById(R.id.btn_Volver);

        btn_activarcamara.setFocusableInTouchMode(true);

        radio_btn_cuartel_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cuartel="01";
            }
        });

        radio_btn_cuartel_02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cuartel="02";
            }
        });

        radio_btn_cuartel_03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cuartel="03";
            }
        });

        btn_activarcamara.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean tieneelfocus) {
                if (tieneelfocus) {
                    btn_activarcamara.setBackgroundColor(Color.rgb(248, 196, 113));
                }
            }
        });
        btn_activarcamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // syncpesa=false;
                edt_operario.setText("");
                LeerconCamara(3);
                if (syncpesa==true)
                {
                    startThread();
                }
            }
        });

        edt_cantidad.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edt_cantidad.getWindowToken(), 0);
                //btn_capturar.setFocusable(true);
            }
        });

        btn_restar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                double cantidad=0;
                String dato = edt_cantidad.getText().toString();
                if (!dato.equals("")) {
                    cantidad = Double.parseDouble((edt_cantidad.getText().toString()));
                }
                cantidad -= 1;
                if (cantidad<0){cantidad=0;}
                edt_cantidad.setText(""+cantidad);
            };
        });

        btn_sumar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                double cantidad=0;
                String dato = edt_cantidad.getText().toString();
                if (!dato.equals("")) {
                    cantidad = Double.parseDouble((edt_cantidad.getText().toString()));
                }
                cantidad += 1;
                edt_cantidad.setText(""+cantidad);
            };
        });

        btn_capturar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getContext(),telefono, Toast.LENGTH_LONG).show();
            if (Arrays.asList(telefonos).contains(telefono)){
              if (cuartel.equals("01") || cuartel.equals("02") || cuartel.equals("03"))
              {
                String rut =  moo.getrut();
                if (rut != null ) {
//                    Toast.makeText(getContext(),"RUT "+rut, Toast.LENGTH_LONG).show();
                    String cantidad = edt_cantidad.getText().toString();
                    if (!cantidad.isEmpty()) {
                        double rutp = Double.parseDouble(rut);
                        double canp = 0;
                        canp = new Double(cantidad);
//                        Toast.makeText(getContext(),"RUTP "+rutp, Toast.LENGTH_LONG).show();
//                        Toast.makeText(getContext(),"CANP "+canp, Toast.LENGTH_LONG).show();
                        if (rutp > 0 && canp > 0) {
                            String nombre=edt_operario.getText().toString();
  //                          Toast.makeText(getContext(),"OK VALORES1 "+nombre, Toast.LENGTH_LONG).show();
 //                           if (!isNumeric(nombre))
 //                           {
 //                           Toast.makeText(getContext(),"OK VALORES2 ", Toast.LENGTH_LONG).show();
                            DBMetodos dbmetodos = new DBMetodos(getContext());
                            SQLiteDatabase db = dbmetodos.getWritableDatabase();
                            dbmetodos.guardarcaptura(rut, nombre, empresa, cuartel, telefono, idsinc, producto, variedad, cantidad, "0", db);
                            dbmetodos.close();
                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(edt_cantidad.getWindowToken(), 0);
                            edt_cantidad.setText("0.00");
                            edt_operario.setText("");
                            moo.setrut(null);
                            avisar_grabo_ok(view);
     //                       }
                        }
                    }
                }else
                    {
                        Toast.makeText(getContext(),"Señale un Operario", Toast.LENGTH_LONG).show();
                    }
              }else{
                  Toast.makeText(getContext(),"Seleccion Cuartel", Toast.LENGTH_LONG).show();
              }
            }else{
                    Toast.makeText(getContext(),"Indique Telefono", Toast.LENGTH_LONG).show();
            }
          }
        });

        btn_buscar_pesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FProduccion_Buscar_Pesa newFragment = new FProduccion_Buscar_Pesa();
                Bundle bundle = new Bundle();
                newFragment.setArguments(bundle);
                newFragment.setTargetFragment(FProduccion.this, 1);
                newFragment.show(getFragmentManager(), "Dialog");
                startThread();
            }
        });

        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                onStop();
                syncpesa=false;
                if (btSocket!=null)
                {
                    try {
                        btSocket.close();
                    } catch (IOException e) {
                        Log.e("CIERRA SOCKET:",  e.getMessage());
                        e.printStackTrace();
                    }
                }
                if (producto.equals("1"))
                {
                    Intent intent = new Intent(getContext(), Variedades_arandano.class);
                    intent.putExtra("menu", empresa);
                    intent.putExtra("idsinc", idsinc);
                    intent.putExtra("producto", producto);
                    intent.putExtra("nomproducto", nomproducto);
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(getContext(), Variedades_cereza.class);
                    intent.putExtra("menu", empresa);
                    intent.putExtra("idsinc", idsinc);
                    intent.putExtra("producto", producto);
                    intent.putExtra("nomproducto", nomproducto);
                    startActivity(intent);
                }
            }
        });
        return view;
    }

    private void SelectorTelefonoDialog(final EditText edt_telefono) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Telefono");
        int checkedItem = 0;
        switch (telefono)
        {
            case "0":checkedItem = -1;break;
            case "1":checkedItem = 0;break;
            case "2":checkedItem = 1;break;
            case "3":checkedItem = 2;break;
            case "4":checkedItem = 3;break;
            case "5":checkedItem = 4;break;
            case "6":checkedItem = 5;break;
            case "7":checkedItem = 6;break;
            case "8":checkedItem = 7;break;
            case "9":checkedItem = 8;break;
            case "10":checkedItem = 9;break;
        }
        alertDialog.setSingleChoiceItems(telefonos, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                switch (which) {
                    case 0:edt_telefono.setText("1");telefono="1";break;
                    case 1:edt_telefono.setText("2");telefono="2";break;
                    case 2:edt_telefono.setText("3");telefono="3";break;
                    case 3:edt_telefono.setText("4");telefono="4";break;
                    case 4:edt_telefono.setText("5");telefono="5";break;
                    case 5:edt_telefono.setText("6");telefono="6";break;
                    case 6:edt_telefono.setText("7");telefono="7";break;
                    case 7:edt_telefono.setText("8");telefono="8";break;
                    case 8:edt_telefono.setText("9");telefono="9";break;
                    case 9:edt_telefono.setText("10");telefono="10";break;
                }

            }
        });
        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(true);
        alert.show();
    }

    private void LeerconCamara(int tipocodigo){


        switch (tipocodigo)
        {
            case 1:

                IntentIntegrator integrator1 = new IntentIntegrator(getActivity());
                integrator1.setDesiredBarcodeFormats(IntentIntegrator.CODE_39);
                integrator1.setPrompt("Scan cODE39");
                integrator1.setCameraId(0);
                integrator1.setOrientationLocked(true);
                integrator1.setBeepEnabled(false);
                integrator1.setBarcodeImageEnabled(false);
                integrator1.initiateScan();
                break;
            case 2:

                IntentIntegrator integrator2 = new IntentIntegrator(getActivity());
                integrator2.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                integrator2.setPrompt("Scan QR");
                integrator2.setCameraId(0);
                integrator2.setOrientationLocked(true);
                integrator2.setBeepEnabled(false);
                integrator2.setBarcodeImageEnabled(false);
                integrator2.initiateScan();
                break;
            case 3:

                IntentIntegrator integrator3 = new IntentIntegrator(getActivity());
                integrator3.setDesiredBarcodeFormats(IntentIntegrator.EAN_13);
                integrator3.setPrompt("Scan EAN13");
                integrator3.setCameraId(0);
                integrator3.setOrientationLocked(true);
                integrator3.setBeepEnabled(false);
                integrator3.setBarcodeImageEnabled(false);
                integrator3.initiateScan();
                break;
        }

    }

    @Override
    public void sendStatus(final String address) {
        pesa_address=address;
        AsyncTask LectorPesa = new ConnectBT(address).execute();

    }

    class ConnectBT extends AsyncTask<Void, Void, Void> {
        //
        private boolean ConnectSuccess = true; //if it's here, it's almost connected
        private String address;

        public ConnectBT(String address) {
            this.address = address;
        }

        @Override
        protected void onPreExecute()
        {
            // progress = ProgressDialog.show(ledControl.this, "Connecting...", "Please wait!!!");  //show a progress dialog
            //msg("Conectando.. Espere xfa!!");
        }

        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {
            try
            {
                if (btSocket == null || !isBtConnected)
                {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect(); //start connection
                    //
                    receiveData(); //
                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;//if the try failed, you can check the exception here
                Log.e("RECONECTA:",  e.getMessage());
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            if (!ConnectSuccess)  {
                //msg("Conexion fallida..");
                //finish();
            } else {
                //msg("Conectado..");
                isBtConnected = true;
            }

        }
    }

    public void receiveData()  throws IOException{
        InputStream socketInputStream =  btSocket.getInputStream();
        byte[] buffer = new byte[256];
        int bytes;

        syncpesa=true;

        // Keep looping to listen for received messages
        while (syncpesa) {

                try {
                bytes = socketInputStream.read(buffer);            //read bytes from input buffer
                readMessage = new String(buffer, 0, bytes);
                // Send the obtained bytes to the UI Activity via handler
                Log.e("DATOS--->", readMessage + "");
            } catch (IOException e) {
                    Log.e("ERROR HILO--->",  e.getMessage() + "");
                break;
            }

        }
    }

    public void startThread(){
        //
                runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                Log.e("DATOS trhead--->", readMessage + "");

                String value=readMessage;
                String[] phrase = value.split("#");
                String[] words = phrase[0].split(" ");

                String newValue="";
                for(int i = 0; i < words.length; i++){newValue += words[i];}

                String peso = newValue.replace(",",".");
                String cantidad = peso.replaceFirst("^0+(?!$)", "");

                edt_cantidad.setText(cantidad);

            }
        };
        handler.postDelayed(runnable, 0);
    }

    @Override
    public void onStop() {
        super.onStop();
        handler.removeCallbacks(runnable);
    }

    // fast way to call Toast
    private void msg(String msje) {
        Toast.makeText(getContext(), msje, Toast.LENGTH_LONG).show();
    }

    private static boolean isNumeric(String cadena){
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe){
            return false;
        }
    }


    public void avisar_grabo_ok(View view) {
        // create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Captura guardada");
        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.custom_layout, null);
        builder.setView(customLayout);
        // add a button
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // send data from the AlertDialog to the Activity
           //     EditText texto = customLayout.findViewById(R.id.editText);
           //     texto.setInputType(InputType.TYPE_NULL);

                //               sendDialogDataToActivity(editText.getText().toString());
            }
        });
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}