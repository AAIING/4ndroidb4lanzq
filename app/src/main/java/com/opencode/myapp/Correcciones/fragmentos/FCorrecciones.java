package com.opencode.myapp.Correcciones.fragmentos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.content.Intent;

import android.os.SystemClock;
import android.provider.Settings;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.opencode.myapp.Empresas.Empresas;
import com.opencode.myapp.R;
import com.opencode.myapp.bdsqlite.DBDatos;
import com.opencode.myapp.bdsqlite.DBMetodos;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.ContentValues.TAG;

public class FCorrecciones extends Fragment {

    public int item_id=0;
    public String empresa = "0";
    public String cuartel = "0";
    public String telefono = "0";
    public String nosinc = "0";
    public String producto = "0";
    public String variedad = "0";
    public String cantidad = "0";
    private long mLastClickTime = 0;

    private DBMetodos dbmetodos;
    SimpleCursorAdapter cursorAdapter;
    Cursor cursor;

    public FCorrecciones() {
        // Required empty public constructor
    }

    public static FCorrecciones newInstance(String param1, String param2) {
        FCorrecciones fragment = new FCorrecciones();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_correcciones, container, false);

        ListView lv = (ListView) view.findViewById(R.id.lista_de_capturas);

        DBMetodos db = new DBMetodos(getContext());

        cursor = db.leercapturas();
        String[] from = new String[]
                {
                        DBDatos.NOMBRE,
                        DBDatos.IDCAPTURA,
                        DBDatos.EMPRESA,
                        DBDatos.CUARTEL,
                        DBDatos.TELEFONO,
                        DBDatos.NOSINC,
                        DBDatos.PRODUCTO,
                        DBDatos.VARIEDAD,
                        DBDatos.CANTIDAD
                };
        int[] to = new int[]
                {
                        R.id.nombre,
                        R.id.id,
                        R.id.empresa,
                        R.id.cuartel,
                        R.id.telefono,
                        R.id.nosinc,
                        R.id.producto,
                        R.id.variedad,
                        R.id.cantidad
                };
        cursorAdapter = new SimpleCursorAdapter(getContext(), R.layout.fragment_correcciones_list_row, cursor, from, to, 1);

        lv.setAdapter(cursorAdapter);

        lv.setOnItemClickListener(listContentOnItemClickListener);


        Button back = (Button) view.findViewById(R.id.btnBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Empresas.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private ListView.OnItemClickListener listContentOnItemClickListener = new ListView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();

            Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
            item_id = cursor.getInt(cursor.getColumnIndex(DBDatos.IDCAPTURA));
            empresa = cursor.getString(cursor.getColumnIndex(DBDatos.EMPRESA));
            cuartel = cursor.getString(cursor.getColumnIndex(DBDatos.CUARTEL));
            telefono = cursor.getString(cursor.getColumnIndex(DBDatos.TELEFONO));
            nosinc = cursor.getString(cursor.getColumnIndex(DBDatos.NOSINC));
            producto = cursor.getString(cursor.getColumnIndex(DBDatos.PRODUCTO));
            variedad = cursor.getString(cursor.getColumnIndex(DBDatos.VARIEDAD));
            cantidad = cursor.getString(cursor.getColumnIndex(DBDatos.CANTIDAD));

            final AlertDialog.Builder myDialog = new AlertDialog.Builder(getContext());

            myDialog.setTitle("Elimina/Modifica?");

            TextView dialogTxt_id = new TextView(getContext());
            LayoutParams dialogTxt_idLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            dialogTxt_id.setLayoutParams(dialogTxt_idLayoutParams);
            dialogTxt_id.setText("#" + String.valueOf(item_id));

            final EditText dialog_empresa = new EditText(getContext());
            LayoutParams dialog_empresaLayoutParams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
            dialog_empresa.setLayoutParams(dialog_empresaLayoutParams);
            dialog_empresa.setText(empresa);
            dialog_empresa.setInputType(InputType.TYPE_NULL);
            dialog_empresa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SelectorEmpresaDialog(dialog_empresa);
                }
            });

            final EditText dialog_cuartel = new EditText(getContext());
            LayoutParams dialog_cuartelLayoutParams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
            dialog_cuartel.setLayoutParams(dialog_cuartelLayoutParams);
            dialog_cuartel.setText(cuartel);
            dialog_cuartel.setInputType(InputType.TYPE_NULL);
            dialog_cuartel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SelectorCuartelDialog(dialog_cuartel);
                }
            });

            final EditText dialog_telefono = new EditText(getContext());
            LayoutParams dialog_telefonoLayoutParams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
            dialog_telefono.setLayoutParams(dialog_telefonoLayoutParams);
            dialog_telefono.setText(telefono);
            dialog_telefono.setInputType(InputType.TYPE_NULL);
            dialog_telefono.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SelectorTelefonoDialog(dialog_telefono);
                }
            });

            final EditText dialog_nosinc = new EditText(getContext());
            LayoutParams dialog_nosincLayoutParams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
            dialog_nosinc.setLayoutParams(dialog_nosincLayoutParams);
            dialog_nosinc.setText("SINC : "+nosinc);
            dialog_nosinc.setInputType(InputType.TYPE_NULL);


            final EditText dialog_producto = new EditText(getContext());
            LayoutParams dialog_productoLayoutParams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
            dialog_producto.setLayoutParams(dialog_productoLayoutParams);
            dialog_producto.setText(producto+"-"+nproducto());
            dialog_producto.setInputType(InputType.TYPE_NULL);

            final EditText dialog_variedad = new EditText(getContext());
            LayoutParams dialog_variedadLayoutParams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
            dialog_variedad.setLayoutParams(dialog_variedadLayoutParams);
            dialog_variedad.setText(variedad+"-"+nvariedad());
            dialog_variedad.setInputType(InputType.TYPE_NULL);
            dialog_variedad.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SelectorVariedadDialog(dialog_variedad);
                }
            });
            dialog_producto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SelectorProductosDialog(dialog_producto,dialog_variedad);
                }
            });

            final EditText dialog_cantidad = new EditText(getContext());
            LayoutParams dialog_cantidadLayoutParams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
            dialog_cantidad.setLayoutParams(dialog_cantidadLayoutParams);
            dialog_cantidad.setText(cantidad);
            dialog_cantidad.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            int maxLengthofEditText = 5;
            dialog_cantidad.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLengthofEditText)});

            LinearLayout layout = new LinearLayout(getContext());
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.addView(dialogTxt_id);
            layout.addView(dialog_empresa);
            layout.addView(dialog_cuartel);
            layout.addView(dialog_telefono);
            layout.addView(dialog_nosinc);
            layout.addView(dialog_producto);
            layout.addView(dialog_variedad);
            layout.addView(dialog_cantidad);
            myDialog.setView(layout);

            myDialog.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                // do something when the button is clicked
                public void onClick(DialogInterface arg0, int arg1) {
                    dbmetodos = new DBMetodos(getContext());
                    dbmetodos.Eliminar_Captura_x_id(item_id);
                    refresca_lv();
                }
            });

            myDialog.setNeutralButton("Modificar", new DialogInterface.OnClickListener() {
                // do something when the button is clicked
                public void onClick(DialogInterface arg0, int arg1) {
//                    empresa = dialog_empresa.getText().toString();
//                    cuartel = dialog_cuartel.getText().toString();
//                    producto = dialog_producto.getText().toString();
//                    variedad = dialog_variedad.getText().toString();
                    cantidad = dialog_cantidad.getText().toString();
                    String mensaje="";
                    if (producto.equals("1"))
                    {
                        switch (variedad)
                        {
                            case "4": mensaje = "Variedad incorrecta !!";break;
                            case "5": mensaje = "Variedad incorrecta !!";break;
                            case "6": mensaje = "Variedad incorrecta !!";break;
                        }
                    }
                    if (mensaje.length()>0){
                        Toast.makeText(getContext(), mensaje, Toast.LENGTH_LONG).show();
                    } else {
                        mensaje="Datos modificados";
                        dbmetodos = new DBMetodos(getContext());
                        int count = dbmetodos.Modificar_Captura_x_id(item_id, empresa, cuartel, telefono, nosinc, producto, variedad, cantidad);
                        refresca_lv();
                        Toast.makeText(getContext(), mensaje, Toast.LENGTH_LONG).show();
                    }
                }
            });

            myDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                // do something when the button is clicked
                public void onClick(DialogInterface arg0, int arg1) {

                }
            });

            myDialog.show();

        }
    };

    private void refresca_lv(){
        cursor.requery();
    }

    private void SelectorEmpresaDialog(final EditText edttxtempresa) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Empresa");
        String[] items = {"Empresa 1","Empresa 2"};
        int checkedItem = 0;
        if (empresa.equals("1"))
        {
            checkedItem = 0;
        }
        else
        {
            checkedItem = 1;
        }
        alertDialog.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                switch (which) {
                    case 0:edttxtempresa.setText("1");empresa="1";break;
                    case 1:edttxtempresa.setText("2");empresa="2";break;
                }

            }
        });
        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(true);
        alert.show();
    }

    private void SelectorTelefonoDialog(final EditText edt_telefono) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Telefono");
        String[] items = {"Tel 1","Tel 2","Tel 3","Tel 4","Tel 5","Tel 6","Tel 7","Tel 8","Tel 9","Tel 10"};
        int checkedItem = 0;
        switch (telefono)
        {
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
        alertDialog.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
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

    private void SelectorCuartelDialog(final EditText edttxtcuartel) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Cuartel");
        String[] items = {"Cuartel 01","Cuartel 02","Cuartel 03"};
        int checkedItem = 0;
        switch (cuartel)
        {
            case "01":checkedItem = 0;break;
            case "02":checkedItem = 1;break;
            case "03":checkedItem = 2;break;
        }
        alertDialog.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                switch (which) {
                    case 0:
                        edttxtcuartel.setText("01");
                        cuartel="01";
                        break;
                    case 1:
                        edttxtcuartel.setText("02");
                        cuartel="02";
                        break;
                    case 2:
                        edttxtcuartel.setText("03");
                        cuartel="03";
                        break;
                }

            }
        });
        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(true);
        alert.show();
    }

    private void SelectorProductosDialog(final EditText edttxtproducto, final EditText edttxtvariedad) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Producto");
        String[] items = {"ARANDANO","CEREZA"};
        int checkedItem = 0;
        if (producto.equals("1"))
        {
//            Toast.makeText(getContext(), "+Producto:"+producto+"-0", Toast.LENGTH_LONG).show();
            checkedItem = 0;
        }
        else
        {
//            Toast.makeText(getContext(), "+Producto:"+producto+"-1", Toast.LENGTH_LONG).show();
            checkedItem = 1;
        }
        alertDialog.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                switch (which) {
                    case 0:
                        producto="1";
                        edttxtproducto.setText("1-"+nproducto());
//                        Toast.makeText(getContext(), "Clicked on java", Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        producto="2";
                        edttxtproducto.setText("2-"+nproducto());
//                        Toast.makeText(getContext(), "Clicked on android", Toast.LENGTH_LONG).show();
                        break;
                    case 2:
//                        Toast.makeText(getContext(), "Clicked on Data Structures", Toast.LENGTH_LONG).show();
                        break;
                }
                edttxtvariedad.setText(variedad+"-"+nvariedad());
            }
        });
        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(true);
        alert.show();
    }

    private void SelectorVariedadDialog(final EditText edttxtvariedad) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Variedad");

        String[] items;
        int checkedItem = 1;
        if (producto.equals("1"))
        {
            items =new String[] {"LEGACY","BRIGITTA","DUKE"};
            switch (variedad){
                case "1": checkedItem=0; break;
                case "2": checkedItem=1; break;
                case "3": checkedItem=2; break;
            }
        }
        else
        {
            items =new String[] {"LAPINS","BING","REGINA","KORDIA","SUMMERSET","SILVIA"};
            switch (variedad){
                case "1": checkedItem=0; break;
                case "2": checkedItem=1; break;
                case "3": checkedItem=2; break;
                case "4": checkedItem=3; break;
                case "5": checkedItem=4; break;
                case "6": checkedItem=5; break;}
        }

        alertDialog.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                switch (which) {
                    case 0:
                        variedad="1";
                        edttxtvariedad.setText("1-"+ nvariedad());
                        break;
                    case 1:
                        variedad="2";
                        edttxtvariedad.setText("2-"+ nvariedad());
                        break;
                    case 2:
                        variedad="3";
                        edttxtvariedad.setText("3-"+ nvariedad());
                        break;
                    case 3:
                        variedad="4";
                        edttxtvariedad.setText("4-"+ nvariedad());
                        break;
                    case 4:
                        variedad="5";
                        edttxtvariedad.setText("5-"+ nvariedad());
                        break;
                    case 5:
                        variedad="6";
                        edttxtvariedad.setText("6-"+ nvariedad());
                        break;
                }
            }
        });
        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(true);
        alert.show();
    }

    private String nproducto ()
    {
        String texto="";
        switch (producto){
            case "1": texto= "ARANDANO";break;
            case "2": texto= "CEREZA";break;
        }
        return texto;
    }
    private String nvariedad ()
    {
        String texto="";
        switch (producto) {
            case "1":
                switch (variedad) {
                    case "1":
                        texto = "LEGACY";
                        break;
                    case "2":
                        texto = "BRIGITTA";
                        break;
                    case "3":
                        texto = "DUKE";
                        break;
                };
                break;
            case "2":
                switch (variedad) {
                    case "1":
                        texto = "LAPINS";
                        break;
                    case "2":
                        texto = "BING";
                        break;
                    case "3":
                        texto = "REGINA";
                        break;
                    case "4":
                        texto = "KORDIA";
                        break;
                    case "5":
                        texto = "SUMMERSET";
                        break;
                    case "6":
                        texto = "SILVIA";
                        break;
                }
                break;
        }
        return texto;
    }
}

