package com.opencode.myapp.Models;

import com.google.gson.annotations.SerializedName;

public class Operarios {

    @SerializedName("Codigo")
    public int Codigo ;

    @SerializedName("Nombre")
    public String Nombre ;

    public int getCodigo() {
        return Codigo;
    }

    public void setCodigo(int codigo) {
        Codigo = codigo;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }
}
