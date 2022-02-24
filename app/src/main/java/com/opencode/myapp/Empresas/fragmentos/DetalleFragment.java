package com.opencode.myapp.Empresas.fragmentos;

import static com.opencode.myapp.Empresas.fragmentos.EmpacadorFragment.REGISTRO_DETALLE_KEY;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.opencode.myapp.Empresas.adapters.DetalleRecyclerAdapter;
import com.opencode.myapp.Models.Pedidosd;
import com.opencode.myapp.Models.Presentaciones;
import com.opencode.myapp.R;
import com.opencode.myapp.config.ApiConf;
import com.opencode.myapp.config.session.SessionDatos;
import com.opencode.myapp.config.session.SessionKeys;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetalleFragment extends Fragment {


    public static final String NOMBRE_PROD_KEY = "nomprd_key";
    public static final String CANT_PROD_KEY = "cantprod_key";
    public static final String DESC_PROD_KEY = "descprod_key";
    public static final String PRESENT_PROD_KEY = "presentprod_key";
    public static final String OTROS_CABEZA_KEY = "ocabez_key";
    public static final String OTROS_ESQUELON_KEY = "oesq_key";
    public static final String CANT_REAL_KEY = "cantre_key";
    public static final String TIPO_UNIDAD_KEY = "tipound_key";
    public static final String OBS_PROD_KEY = "obsprod_key";

    private RecyclerView recyclerView;
    private DetalleRecyclerAdapter detalleRecyclerAdapter;
    private List<Pedidosd> listDetalle = new ArrayList<>();
    private SharedPreferences sp;
    private SharedPreferences.Editor edit;
    private SessionDatos sessionDatos;
    private ProgressDialog progressDialog;

    public DetalleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public String getRegistro(){
        String s1 = sp.getString("registro", "0");
        return s1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Detalle Pedido");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        View view = inflater.inflate(R.layout.fragment_detalle, container, false);
        progressDialog = new ProgressDialog(getContext());
        recyclerView = view.findViewById(R.id.recycler_list_detalle);
        sessionDatos = new SessionDatos(getContext());
        int registro = 0;
        //
        if(getArguments() != null){
            String r1 = getArguments().getString(REGISTRO_DETALLE_KEY);
            sessionDatos.IdRegistro(r1);
            registro = Integer.parseInt(r1);
        }else{
            String s1 = sessionDatos.getRecord().get(SessionKeys.idRegistroPedido);
            registro = Integer.parseInt(s1);
        }

        progressDialog.show();
        progressDialog.setMessage("Cargando datos..");
        loadDetalle(registro);

        return view;
    }

    void loadListDetalle(){
        /***/
        progressDialog.dismiss();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        detalleRecyclerAdapter = new DetalleRecyclerAdapter(getContext(), listDetalle);
        detalleRecyclerAdapter.setOnClickListener(new DetalleRecyclerAdapter.OnClickListener() {
            @Override
            public void onEditar(View view, int position) {
                Pedidosd item = listDetalle.get(position);
                Presentaciones item_pres = item.getPresentaciones();

                ModificarDetalleFragment newFragment = new ModificarDetalleFragment();
                Bundle bundle = new Bundle();
                //
                bundle.putString(NOMBRE_PROD_KEY, item.getDetalle());
                bundle.putString(CANT_PROD_KEY, String.valueOf(item.getCantidad()));
                bundle.putString(DESC_PROD_KEY, String.valueOf(item.getDescuento1()));
                bundle.putString(PRESENT_PROD_KEY, item_pres.getNombre());
                bundle.putString(OTROS_CABEZA_KEY, String.valueOf(item.getCabeza()));
                bundle.putString(OTROS_ESQUELON_KEY, String.valueOf(item.getEsquelon()));
                bundle.putString(CANT_REAL_KEY, String.valueOf(item.getCantidadreal()));
                bundle.putString(TIPO_UNIDAD_KEY, item.getUnidad());
                bundle.putString(OBS_PROD_KEY, item.getObs());
                newFragment.setArguments(bundle);
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frame_empresas, newFragment);
                fm.commit();

            }
        });
        recyclerView.setAdapter(detalleRecyclerAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //
                ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
                EmpacadorFragment newFragment = new EmpacadorFragment();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frame_empresas, newFragment);
                fm.commit();

                return true;
            case R.id.action_agregar:
                //
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_detalle, menu);
    }

    private void loadDetalle(int registro){
        Call<List<Pedidosd>> call = ApiConf.getData().getPedidosdDetalle(registro);
        call.enqueue(new Callback<List<Pedidosd>>() {
            @Override
            public void onResponse(Call<List<Pedidosd>> call, Response<List<Pedidosd>> response) {
                //
                if (response.isSuccessful()) {
                    List<Pedidosd> result = response.body();
                    listDetalle.addAll(result);
                    loadListDetalle();
                }
            }

            @Override
            public void onFailure(Call<List<Pedidosd>> call, Throwable t) {
                Log.e("ERROR-->", t.getMessage());
            }
        });
    }
}