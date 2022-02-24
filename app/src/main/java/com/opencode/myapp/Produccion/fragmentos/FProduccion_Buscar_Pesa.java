package com.opencode.myapp.Produccion.fragmentos;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.opencode.myapp.Empresas.fragmentos.ModificarDetalleFragment;
import com.opencode.myapp.R;

import java.util.ArrayList;
import java.util.Set;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;


public class FProduccion_Buscar_Pesa extends DialogFragment {

    private BluetoothAdapter myBluetooth = null;
    private Set<BluetoothDevice> pairedDevices;
    private ListView devicelist;
    private Button btn_buscar_dispositivos;
    private ModificarDetalleFragment modificarDetalleFragment;
    //private AlertDialog alertDialog;

    private static final int REQUEST_ENABLE_BT = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_produccion__buscar__pesa, container, false);
        devicelist = view.findViewById(R.id.listview_dispositivos);
        btn_buscar_dispositivos=view.findViewById(R.id.btn_buscar_dispositivos);
        myBluetooth = BluetoothAdapter.getDefaultAdapter();

        //modificarDetalleFragment = new ModificarDetalleFragment(getContext());

        if (myBluetooth == null) {
            // Device doesn't support Bluetooth
            //Toast.makeText(getContext(), "Bluetooth no soportado " , Toast.LENGTH_LONG).show();
        }else {
            //Toast.makeText(getContext(), "Bluetooth soportado ", Toast.LENGTH_LONG).show();
            if (!myBluetooth.isEnabled()) {
                //Toast.makeText(getContext(), "Bluetooth no esta habilitado ", Toast.LENGTH_LONG).show();
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                //int rspta = enableBtIntent.getIntExtra()
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);

            } else {
                //Toast.makeText(getContext(), "Bluetooth ya esta habilitado ", Toast.LENGTH_LONG).show();
                /*
                if (myBluetooth.startDiscovery()) {
                //Toast.makeText(getContext(), "Se inicio la deteccion de dispositivos Bluetooth", Toast.LENGTH_LONG).show();
                //getActivity().getSupportFragmentManager().replace(R.id.frame_produccion_buscar_pesa, new FProduccion()).commit();
                    pairedDevicesList();
                }
                */
                pairedDevicesList();
            }
        }

        btn_buscar_dispositivos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_ENABLE_BT:

                if (resultCode == RESULT_OK) {
                    pairedDevicesList();
                } else {

                    // User declined to enable Bluetooth, exit the app.
                    Toast.makeText(getContext(), "Debe activar bluetooth",
                            Toast.LENGTH_SHORT).show();

                    //modificarDetalleFragment.setDisabled();
                    mOnInputSelected.sendStatus("0");
                    dismiss();
                }

            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }


    private void pairedDevicesList()
    {
        pairedDevices = myBluetooth.getBondedDevices();
        ArrayList list = new ArrayList();

        if (pairedDevices.size()>0)
        {
            for(BluetoothDevice bt : pairedDevices)
            {
                list.add(bt.getName() + "\n" + bt.getAddress()); //Get the device's name and the address
            }
        }
        else
        {
            Toast.makeText(getContext(), "Debe parear bluetooh con balanza..", Toast.LENGTH_LONG).show();

            mOnInputSelected.sendStatus("0");
            dismiss();

            startActivity(new Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS));
            //Intent bluetoothPicker = new Intent("android.bluetooth.devicepicker.action.LAUNCH");
            //startActivity(bluetoothPicker);

            /*
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_SETTINGS);
            Uri uri = Uri.fromParts("package", getActivity().getPackageName(),
                    null);
            intent.setData(uri);
            getActivity().startActivity(intent);
            */
        }

        final ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, list);
        devicelist.setAdapter(adapter);
        devicelist.setOnItemClickListener(myListClickListener); //Method called when the device from the list is clicked

    }

    private AdapterView.OnItemClickListener myListClickListener = new AdapterView.OnItemClickListener()
    {
        public void onItemClick (AdapterView<?> av, View view, int arg2, long arg3)
        {
            //Get the device MAC address, the last 17 chars in the View
            String info = ((TextView) view).getText().toString();
            String address = info.substring(info.length() - 17);

            mOnInputSelected.sendStatus(address);
            dismiss();  // es para cerrar el dialog fragmente

 /*         ReceiveDataFragment newFragment = new ReceiveDataFragment();
            FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
            Bundle bundle = new Bundle();
            bundle.putString(EXTRA_ADDRESS, address);
            newFragment.setArguments(bundle);
            fm.replace(R.id.main_frame, newFragment);
            fm.commit();
            /*
            ControlFragment newFragment = new ControlFragment();
            FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
            Bundle bundle = new Bundle();
            bundle.putString(EXTRA_ADDRESS, address);
            newFragment.setArguments(bundle);
            fm.replace(R.id.main_frame, newFragment);
            fm.commit();
            */
        }
    };

    public interface OnInputSelected {
        void sendStatus(String mac);
    }

    public OnInputSelected mOnInputSelected;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnInputSelected = (OnInputSelected) getTargetFragment();
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: ClassCastException : " + e.getMessage());
        }
    }
}