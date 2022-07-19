package com.opencode.myapp.Empresas.adapters;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.opencode.myapp.Empresas.utils.PedidosdDiffCallback;
import com.opencode.myapp.Models.Pedidos;
import com.opencode.myapp.Models.Pedidosd;
import com.opencode.myapp.Models.Presentaciones;
import com.opencode.myapp.Models.PresentacionesHasProductos;
import com.opencode.myapp.Models.Productos;
import com.opencode.myapp.Models.Vendedores;
import com.opencode.myapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DetalleRecyclerAdapter extends RecyclerView.Adapter<DetalleRecyclerAdapter.ViewHolder>{

    private Context context;
    private ArrayList<Pedidosd> listDetalle = new ArrayList<>();
    private OnClickListener onClickListener = null;
    private boolean isOnTextChanged = false;
    private String empaqRest ="";

    public interface OnClickListener {
        void onEditar(View view, int position);
        void onTara(View view, int position);
    }

    //inicia comunicacion con fragmento contenedor del recycler view ...
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public DetalleRecyclerAdapter(Context context, ArrayList<Pedidosd> listDetalle, String empaqRest) {
        this.context = context;
        this.listDetalle = listDetalle;
        this.empaqRest = empaqRest;
    }

    @NonNull
    @Override
    public DetalleRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detalle_rv_card, parent, false);
        return new DetalleRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if(payloads.isEmpty())
        super.onBindViewHolder(holder, position, payloads);
        else {
            Bundle bundle = (Bundle) payloads.get(0);
            for(String key: bundle.keySet()){
                if(key.equals("tara")){
                    holder.editTara.setText(bundle.getString("tara"));
                }
                if(key.equals("pesototal")){
                    holder.editPesoTotalFila.setText(bundle.getString("pesototal"));
                }
                if(key.equals("pesaje")){
                    holder.editPesaje.setText(bundle.getString("pesaje"));
                }
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final DetalleRecyclerAdapter.ViewHolder holder, int position) {
        final Pedidosd item = listDetalle.get(holder.getAdapterPosition());
        Presentaciones item_pres = item.getPresentaciones();
        Productos item_prod = item.getProductos();
        PresentacionesHasProductos item_preshasprod;
        if(item.getPreshasprod() != null) {
             item_preshasprod = item.getPreshasprod();
        }else{
             PresentacionesHasProductos preshasprod = new PresentacionesHasProductos();
             listDetalle.get(holder.getAdapterPosition()).setPreshasprod(preshasprod);
             item_preshasprod = item.getPreshasprod();
        }
        if(!empaqRest.equals("")){
            holder.btnObtTara.setVisibility(View.GONE);
            holder.editTara.setVisibility(View.GONE);
            holder.editPesoTotalFila.setVisibility(View.GONE);
        }
        holder.viewCodigo.setText(String.valueOf(item.getCodigo()));
        holder.viewProducto.setText(item.getDetalle().toUpperCase(Locale.ROOT));
        holder.viewPresent.setText(item_pres.getNombre().toUpperCase(Locale.ROOT));
        holder.viewCantidad.setText(String.valueOf(item.getCantidad()));
        holder.viewUnidadM.setText(item.getUnidad());
        //
        holder.editObs.setEnabled(false);
        if(item.getObs() != null)
        holder.editObs.setText(item.getObs().toUpperCase(Locale.ROOT));
        holder.editObs.setTextColor(Color.BLACK);
        holder.editPesaje.setText(item.getReadPesaje());
        holder.editPesoTotalFila.setText(item.getPesototalfila());
        holder.editTara.setText(item.getTara());
        //
        if(item_preshasprod.getRendimiento() == 0){
            holder.editPesaje.setText(""+item.getCantidad());
            holder.editPesaje.setTextColor(Color.BLACK);
            holder.btnObtPeso.setVisibility(View.GONE);

            holder.chkCabeza.setVisibility(View.GONE);
            holder.chkEsquelon.setVisibility(View.GONE);

            holder.btnObtTara.setVisibility(View.GONE);
            holder.editTara.setVisibility(View.GONE);

            holder.editPesoTotalFila.setVisibility(View.GONE);
        } else{
            holder.editPesaje.setEnabled(false);
            holder.editPesaje.setText(""+item.getCantidadreal());
            //listDetalle.get(holder.getAdapterPosition()).setCantidadreal(0);
        }
        if(!item.getReadPesaje().equals("")){
            double pesaje = Double.parseDouble(item.getReadPesaje());
            if(item_preshasprod.getRendimiento() > 0) {
                //***/
                if(!empaqRest.equals("")) {
                    if (pesaje <= item.getMinPeso()) {
                        holder.editPesaje.setTextColor(Color.RED);
                    } else if (pesaje >= item.getMaxPeso()) {
                        holder.editPesaje.setTextColor(Color.GREEN);
                    } else {
                        holder.editPesaje.setTextColor(Color.BLACK);
                    }
                }
            }else{
                holder.editPesaje.setText(""+item.getCantidad());
                holder.editPesaje.setTextColor(Color.BLACK);
            }
        }

        holder.btnObtTara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onClickListener == null) return;
                onClickListener.onTara(view, holder.getAdapterPosition());
            }
        });

        holder.btnObtPeso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onClickListener == null) return;
                onClickListener.onEditar(view, holder.getAdapterPosition());
            }
        });

        if(item.getCabeza() > 0){
            holder.chkCabeza.setChecked(true);
        }else{
            holder.chkCabeza.setChecked(false);
        }

        if(item.getEsquelon() > 0){
            holder.chkEsquelon.setChecked(true);
        }else{
            holder.chkEsquelon.setChecked(false);
        }

        holder.chkCabeza.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    //
                    listDetalle.get(holder.getAdapterPosition()).setCabeza((short) 1);
                }else{
                    //
                    listDetalle.get(holder.getAdapterPosition()).setCabeza((short) 0);
                }
            }
        });

        holder.chkEsquelon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    //
                    listDetalle.get(holder.getAdapterPosition()).setEsquelon((short) 1);
                }else{
                    //
                    listDetalle.get(holder.getAdapterPosition()).setEsquelon((short) 0);
                }
            }
        });

        holder.chkAnular.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    //
                    listDetalle.get(holder.getAdapterPosition()).setAnulado((short) 1);
                }
            }
        });

        if(item.getAnulado() > 0){
            holder.rowDetalle.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return listDetalle.size();
    }

    public ArrayList<Pedidosd> getListDetalle(){
        return listDetalle;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView viewCodigo, viewProducto, viewCantidad, viewUnidadM, viewPresent, viewObtPeso;
        private EditText editPesaje, editObs, editDescto, editTara, editPesoTotal, editPesoTotalFila;
        private CheckBox chkCabeza, chkEsquelon, chkAnular;
        private TableRow rowDetalle;
        private Button btnObtPeso, btnObtTara;
        //private LinearLayout linearTara;
        //
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //linearTara = itemView.findViewById(R.id.linear_det_tara);
            editPesoTotalFila = itemView.findViewById(R.id.edit_det_peso_mas_tara_total);
            editPesoTotal = itemView.findViewById(R.id.edit_det_peso_total);
            editTara = itemView.findViewById(R.id.edit_det_tara);
            btnObtPeso = itemView.findViewById(R.id.btn_obtener_peso);
            btnObtTara = itemView.findViewById(R.id.btn_obtiene_tara);
            rowDetalle = itemView.findViewById(R.id.table_row_detalle);
            //viewObtPeso = itemView.findViewById(R.id.view_obtener_peso);
            chkAnular = itemView.findViewById(R.id.chk_det_anular);
            viewPresent = itemView.findViewById(R.id.view_det_presentacion);
            chkCabeza = itemView.findViewById(R.id.chk_det_cabeza);
            chkEsquelon = itemView.findViewById(R.id.chk_det_esquelon);
            editDescto = itemView.findViewById(R.id.edit_det_descto);
            editObs = itemView.findViewById(R.id.edit_det_obs);
            editPesaje = itemView.findViewById(R.id.edit_det_pesaje);
            viewCodigo = itemView.findViewById(R.id.view_det_codigo);
            viewProducto = itemView.findViewById(R.id.view_det_producto);
            viewCantidad = itemView.findViewById(R.id.view_det_cantidad);
            viewUnidadM = itemView.findViewById(R.id.view_det_unidadm);
        }
    }

    public void updatePedidosd(ArrayList<Pedidosd> newPedidosd){
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new PedidosdDiffCallback(this.listDetalle, newPedidosd));
        this.listDetalle.clear();
        this.listDetalle.addAll(newPedidosd);
        diffResult.dispatchUpdatesTo(this);
    }
}
