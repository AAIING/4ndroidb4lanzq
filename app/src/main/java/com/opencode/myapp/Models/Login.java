package com.opencode.myapp.Models;

import com.google.gson.annotations.SerializedName;

public class Login {

    @SerializedName("Rut")
    private int rut;

    @SerializedName("IdSesion")
    private int idSesion;

    @SerializedName("Empresa")
    private int empresa;

    @SerializedName("UsuariosReferencia")
    private int UsuariosReferencia;

    @SerializedName("Vendedor")
    private int vendedor;

    @SerializedName("Div")
    private String div;

    @SerializedName("Nombre")
    private String nombre;

    @SerializedName("Codigo")
    private String codigo;

    @SerializedName("Clave")
    private String clave;

    @SerializedName("HoraInicio")
    private String HoraInicio;

    public String getHoraInicio() {
        return HoraInicio;
    }

    public void setHoraInicio(String horaInicio) {
        HoraInicio = horaInicio;
    }

    public int getUsuariosReferencia() {
        return UsuariosReferencia;
    }

    public void setUsuariosReferencia(int usuariosReferencia) {
        UsuariosReferencia = usuariosReferencia;
    }

    public int getIdSesion() {
        return idSesion;
    }

    public void setIdSesion(int idSesion) {
        this.idSesion = idSesion;
    }

    public int getEmpresa() {
        return empresa;
    }

    public void setEmpresa(int empresa) {
        this.empresa = empresa;
    }

    public int getVendedor() {
        return vendedor;
    }

    public void setVendedor(int vendedor) {
        this.vendedor = vendedor;
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
