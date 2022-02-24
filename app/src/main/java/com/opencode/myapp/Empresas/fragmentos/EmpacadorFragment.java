package com.opencode.myapp.Empresas.fragmentos;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.opencode.myapp.Empresas.adapters.EmpacadorRecyclerAdapter;
import com.opencode.myapp.Models.Pedidos;
import com.opencode.myapp.R;
import com.opencode.myapp.config.ApiConf;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EmpacadorFragment extends Fragment {

    public static final String REGISTRO_DETALLE_KEY = "detalle_key";

    private List<Pedidos> listPedidos = new ArrayList<>();
    private EmpacadorRecyclerAdapter empacadorRecyclerAdapter;
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;

    public EmpacadorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Empaque");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        View view = inflater.inflate(R.layout.fragment_empacador, container, false);
        progressDialog = new ProgressDialog(getContext());
        recyclerView = view.findViewById(R.id.recycler_list_empaca);

        progressDialog.show();
        progressDialog.setMessage("Cargando datos..");
        loadPendientes();

        return view;
    }

    void loadListEmpacador(){
        /***/
        progressDialog.dismiss();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        empacadorRecyclerAdapter = new EmpacadorRecyclerAdapter(getContext(), listPedidos);
        empacadorRecyclerAdapter.setOnClickListener(new EmpacadorRecyclerAdapter.OnClickListener() {
            @Override
            public void onEditar(View view, int position) {
                Pedidos item = listPedidos.get(position);
                DetalleFragment newFragment = new DetalleFragment();
                Bundle bundle = new Bundle();
                bundle.putString(REGISTRO_DETALLE_KEY, String.valueOf(item.getRegistro()));
                newFragment.setArguments(bundle);
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frame_empresas, newFragment);
                fm.commit();

            }
        });
        recyclerView.setAdapter(empacadorRecyclerAdapter);
    }

    private void loadPendientes(){
        Call<List<Pedidos>> call = ApiConf.getData().getPedidosPendientes();
        call.enqueue(new Callback<List<Pedidos>>() {
            @Override
            public void onResponse(Call<List<Pedidos>> call, Response<List<Pedidos>> response) {
                //
                if (response.isSuccessful()) {
                    List<Pedidos> result = response.body();
                    listPedidos.addAll(result);
                    loadListEmpacador();
                }
            }

            @Override
            public void onFailure(Call<List<Pedidos>> call, Throwable t) {
                Log.e("ERROR-->", t.getMessage());
            }
        });
    }
}