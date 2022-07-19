package com.opencode.myapp.Models;

import com.google.gson.annotations.SerializedName;

public class Itemsid {

    @SerializedName("Codigo")
    public String Codigo ;

    @SerializedName("Tipoitem")
    public String Tipoitem ;

    @SerializedName("Pedidosregistro")
    public int Pedidosregistro ;

    public int Escaneado ;

    public int EscaneadoEntregado ;

    public int getEscaneadoEntregado() {
        return EscaneadoEntregado;
    }

    public void setEscaneadoEntregado(int escaneadoEntregado) {
        EscaneadoEntregado = escaneadoEntregado;
    }

    public int getEscaneado() {
        return Escaneado;
    }

    public void setEscaneado(int escaneado) {
        Escaneado = escaneado;
    }

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String codigo) {
        Codigo = codigo;
    }

    public String getTipoitem() {
        return Tipoitem;
    }

    public void setTipoitem(String tipoitem) {
        Tipoitem = tipoitem;
    }

    public int getPedidosregistro() {
        return Pedidosregistro;
    }

    public void setPedidosregistro(int pedidosregistro) {
        Pedidosregistro = pedidosregistro;
    }
}
