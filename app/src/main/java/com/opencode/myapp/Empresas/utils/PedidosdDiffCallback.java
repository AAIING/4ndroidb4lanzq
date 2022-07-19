package com.opencode.myapp.Empresas.utils;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.opencode.myapp.Models.Login;
import com.opencode.myapp.Models.Pedidosd;

import java.util.ArrayList;
import java.util.List;

public class PedidosdDiffCallback extends DiffUtil.Callback{

   private ArrayList<Pedidosd> mOldPedidodList = new ArrayList<>();
   private ArrayList<Pedidosd> mNewPedidodList = new ArrayList<>();

   public PedidosdDiffCallback(ArrayList<Pedidosd> oldPedidosd, ArrayList<Pedidosd> newPedidos) {
         this.mOldPedidodList = oldPedidosd;
         this.mNewPedidodList = newPedidos;
   }

    @Override
    public int getOldListSize() {
        return mOldPedidodList != null ? mOldPedidodList.size() : 0;
    }

    @Override
    public int getNewListSize() {
        return mNewPedidodList != null ? mNewPedidodList.size() : 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
       return true;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        int result = mNewPedidodList.get(newItemPosition).compareTo(mOldPedidodList.get(oldItemPosition));
        if(result==0)
        return true;

        return false;
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {

        Pedidosd newPedidod = mNewPedidodList.get(newItemPosition);
        Pedidosd oldPedidod = mOldPedidodList.get(oldItemPosition);

        Bundle bundle = new Bundle();

        if(!newPedidod.getTara().equals(oldPedidod.getTara())){
            bundle.putString("tara", newPedidod.getTara());
        }

        if(!newPedidod.getPesototalfila().equals(oldPedidod.getPesototalfila())){
            bundle.putString("pesototal", newPedidod.getPesototalfila());
        }

        if(!newPedidod.getReadPesaje().equals(oldPedidod.getReadPesaje())){
           bundle.putString("pesaje", newPedidod.getReadPesaje());
        }

        if(bundle.size()==0)
        return null;

        return bundle;
        //return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
