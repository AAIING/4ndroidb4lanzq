package com.opencode.myapp.Models;

import com.google.gson.annotations.SerializedName;

public class Presentaciones {

    @SerializedName("Codigo")
    public short Codigo ;

    @SerializedName("Nombre")
    public String Nombre ;

    @SerializedName("Codigopre")
    public short Codigopre ;

    @SerializedName("Webbox")
    public short Webbox ;

    public short getCodigo() {
        return Codigo;
    }

    public void setCodigo(short codigo) {
        Codigo = codigo;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public short getCodigopre() {
        return Codigopre;
    }

    public void setCodigopre(short codigopre) {
        Codigopre = codigopre;
    }

    public short getWebbox() {
        return Webbox;
    }

    public void setWebbox(short webbox) {
        Webbox = webbox;
    }
}
