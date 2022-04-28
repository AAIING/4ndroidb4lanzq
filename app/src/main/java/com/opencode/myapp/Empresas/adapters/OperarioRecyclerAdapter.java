package com.opencode.myapp.Empresas.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.opencode.myapp.Models.Operarios;
import com.opencode.myapp.R;

import java.util.List;
import java.util.Locale;

public class OperarioRecyclerAdapter extends RecyclerView.Adapter<OperarioRecyclerAdapter.ViewHolder>{

    private Context context;
    private List<Operarios> listOperarios;
    private int indexPos = -1;
    private OnClickListener onClickListener = null;

    public interface OnClickListener {
        void onSelect(View view, int position);
    }

    //inicia comunicacion con fragmento contenedor del recycler view ...
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public OperarioRecyclerAdapter(Context context, List<Operarios> listOperarios) {
        this.context = context;
        this.listOperarios = listOperarios;
    }

    @NonNull
    @Override
    public OperarioRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.operario_rv_card, parent, false);
        return new OperarioRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OperarioRecyclerAdapter.ViewHolder holder, int position) {
        Operarios item = listOperarios.get(holder.getAdapterPosition());
        holder.nombreOperario.setText(item.Nombre.toUpperCase(Locale.ROOT));

        holder.nombreOperario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(onClickListener == null) return;
                onClickListener.onSelect(view, holder.getAdapterPosition());
                indexPos = holder.getAdapterPosition();
                notifyDataSetChanged();
            }
        });

        if(indexPos == holder.getAdapterPosition()){
            holder.nombreOperario.setBackgroundColor(context.getResources().getColor(R.color.lightGreen));
        }else{
            holder.nombreOperario.setBackgroundColor(context.getResources().getColor(R.color.white));
        }

    }

    @Override
    public int getItemCount() {
        return listOperarios.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView nombreOperario;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //
            nombreOperario = itemView.findViewById(R.id.view_nombre_operario);
        }
    }
}
