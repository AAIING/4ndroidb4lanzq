package com.opencode.myapp.Models;

import com.google.gson.annotations.SerializedName;

public class Login {

    @SerializedName("rut")
    private int rut;

    @SerializedName("empresa")
    private int empresa;

    @SerializedName("div")
    private String div;

    @SerializedName("nombre")
    private String nombre;

    @SerializedName("codigo")
    private String codigo;

    @SerializedName("clave")
    private String clave;

    public int getEmpresa() {
        return empresa;
    }

    public void setEmpresa(int empresa) {
        this.empresa = empresa;
    }

    public int getRut() {
        return rut;
    }

    public void setRut(int rut) {
        this.rut = rut;
    }

    public String getDiv() {
        return div;
    }

    public void setDiv(String div) {
        this.div = div;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
}
