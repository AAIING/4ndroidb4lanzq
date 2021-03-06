package com.opencode.myapp.Empresas.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.opencode.myapp.Models.Pedidos;
import com.opencode.myapp.Models.Vendedores;
import com.opencode.myapp.R;

import java.util.List;

public class EmpacadorRecyclerAdapter extends RecyclerView.Adapter<EmpacadorRecyclerAdapter.ViewHolder>{

    private Context context;
    private List<Pedidos> listPedidos;
    private OnClickListener onClickListener = null;
    private int indexPos = -1;

    public interface OnClickListener {
        void onEditar(View view, int position);
    }

    //inicia comunicacion con fragmento contenedor del recycler view ...
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public EmpacadorRecyclerAdapter(Context context, List<Pedidos> listPedidos) {
        this.context = context;
        this.listPedidos = listPedidos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.empacador_rv_card, parent, false);
        return new EmpacadorRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Pedidos item = listPedidos.get(holder.getAdapterPosition());
        //Vendedores item_vend = item.getVendedores();

        holder.viewPedido.setText(String.valueOf(item.getRegistro()));
        holder.viewFechaEnt.setText(item.getFechaent());
        holder.viewHoraEnt.setText(String.valueOf(item.getHoraent()));
        //holder.viemEmpacador.setText(item_vend.getNombre());
        holder.viewCliente.setText(item.getCliente());
        holder.viewComuna.setText(item.getComunaenvio());
        holder.viewDireccion.setText(item.getDireccionenvio());
        holder.viewCondominio.setText(item.getCondominioenvio());
        holder.rowEmpacador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(onClickListener == null) return;
                onClickListener.onEditar(view, holder.getAdapterPosition());
                indexPos = holder.getAdapterPosition();
                notifyDataSetChanged();
            }
        });


        if(indexPos == holder.getAdapterPosition()){
            holder.rowEmpacador.setBackgroundColor(context.getResources().getColor(R.color.lightGreen));
        }else{

            if(item.getPedidopausa() == 1){
                //holder.viewIconPause.setVisibility(View.VISIBLE);
                holder.rowEmpacador.setBackgroundColor(context.getResources().getColor(R.color.yellow_white));
            }else{
                holder.rowEmpacador.setBackgroundColor(context.getResources().getColor(R.color.white));
            }
        }
    }

    @Override
    public int getItemCount() {
        return listPedidos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView viewPedido, viewFechaEnt, viewHoraEnt, viemEmpacador, viewCliente, viewComuna,
                viewDireccion,  viewCondominio, viewEditar, viewIconPause;

        private TableRow rowEmpacador;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            viewIconPause = itemView.findViewById(R.id.view_icon_pause);
            rowEmpacador = itemView.findViewById(R.id.table_row_empacador);
            viewEditar = itemView.findViewById(R.id.view_edit_rv);
            viewPedido = itemView.findViewById(R.id.view_pedido);
            viewFechaEnt = itemView.findViewById(R.id.view_fecha_ent);
            viewHoraEnt = itemView.findViewById(R.id.view_hora_ent);
            viemEmpacador = itemView.findViewById(R.id.view_empacador);
            viewCliente = itemView.findViewById(R.id.view_cliente);
            viewComuna = itemView.findViewById(R.id.view_comuna);
            viewDireccion = itemView.findViewById(R.id.view_direccion);
            viewCondominio = itemView.findViewById(R.id.view_condominio);
        }
    }
}
