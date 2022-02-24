package com.opencode.myapp.Empresas.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.opencode.myapp.Models.Pedidos;
import com.opencode.myapp.Models.Pedidosd;
import com.opencode.myapp.Models.Vendedores;
import com.opencode.myapp.R;

import java.util.List;

public class DetalleRecyclerAdapter extends RecyclerView.Adapter<DetalleRecyclerAdapter.ViewHolder>{

    private Context context;
    private List<Pedidosd> listDetalle;
    private OnClickListener onClickListener = null;

    public interface OnClickListener {
        void onEditar(View view, int position);
    }

    //inicia comunicacion con fragmento contenedor del recycler view ...
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public DetalleRecyclerAdapter(Context context, List<Pedidosd> listDetalle) {
        this.context = context;
        this.listDetalle = listDetalle;
    }

    @NonNull
    @Override
    public DetalleRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detalle_rv_card, parent, false);
        return new DetalleRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DetalleRecyclerAdapter.ViewHolder holder, int position) {
        Pedidosd item = listDetalle.get(holder.getAdapterPosition());

        holder.viewCodigo.setText(String.valueOf(item.getCodigo()));
        holder.viewProducto.setText((item.getDetalle()));
        holder.viewCantidad.setText(String.valueOf(item.getCantidad()));
        holder.viewCantReal.setText(String.valueOf(item.getCantidadreal()));
        holder.viewUnidadM.setText(item.getUnidad());
        holder.viewEditarDetalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onClickListener == null) return;
                onClickListener.onEditar(view, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listDetalle.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView viewCodigo, viewProducto, viewCantidad, viewCantReal, viewUnidadM, viewEditarDetalle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            viewCodigo = itemView.findViewById(R.id.view_det_codigo);
            viewProducto = itemView.findViewById(R.id.view_det_producto);
            viewCantidad = itemView.findViewById(R.id.view_det_cantidad);
            viewCantReal = itemView.findViewById(R.id.view_det_cantreal);
            viewUnidadM = itemView.findViewById(R.id.view_det_unidadm);
            viewEditarDetalle = itemView.findViewById(R.id.view_edit_det_rv);
        }
    }
}
