package com.opencode.myapp.Models;

import com.google.gson.annotations.SerializedName;

public class Vendedores {
    @SerializedName("Referencia")
    private short Referencia ;

    @SerializedName("Nombre")
    private String Nombre ;

    @SerializedName("Comision1")
    private double Comision1;

    @SerializedName("Comision2")
    private double Comision2 ;

    @SerializedName("Comision3")
    private double Comision3 ;

    @SerializedName("Comision4")
    private double Comision4 ;

    @SerializedName("Rut")
    private int Rut ;

    @SerializedName("Dv")
    private String Dv ;

    @SerializedName("Rutboleta")
    private int Rutboleta ;

    @SerializedName("Dvboleta")
    private String Dvboleta ;

    @SerializedName("Local")
    private byte Local ;

    @SerializedName("Meta")
    private int Meta ;

    @SerializedName("Callcenter")
    private boolean Callcenter ;

    @SerializedName("Email")
    private String Email ;

    @SerializedName("Caja")
    private String Caja ;
    //private sbyte Canal ;

    @SerializedName("Margenexigido")
    private double Margenexigido ;

    public short getReferencia() {
        return Referencia;
    }

    public void setReferencia(short referencia) {
        Referencia = referencia;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public double getComision1() {
        return Comision1;
    }

    public void setComision1(double comision1) {
        Comision1 = comision1;
    }

    public double getComision2() {
        return Comision2;
    }

    public void setComision2(double comision2) {
        Comision2 = comision2;
    }

    public double getComision3() {
        return Comision3;
    }

    public void setComision3(double comision3) {
        Comision3 = comision3;
    }

    public double getComision4() {
        return Comision4;
    }

    public void setComision4(double comision4) {
        Comision4 = comision4;
    }

    public int getRut() {
        return Rut;
    }

    public void setRut(int rut) {
        Rut = rut;
    }

    public String getDv() {
        return Dv;
    }

    public void setDv(String dv) {
        Dv = dv;
    }

    public int getRutboleta() {
        return Rutboleta;
    }

    public void setRutboleta(int rutboleta) {
        Rutboleta = rutboleta;
    }

    public String getDvboleta() {
        return Dvboleta;
    }

    public void setDvboleta(String dvboleta) {
        Dvboleta = dvboleta;
    }

    public byte getLocal() {
        return Local;
    }

    public void setLocal(byte local) {
        Local = local;
    }

    public int getMeta() {
        return Meta;
    }

    public void setMeta(int meta) {
        Meta = meta;
    }

    public boolean isCallcenter() {
        return Callcenter;
    }

    public void setCallcenter(boolean callcenter) {
        Callcenter = callcenter;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getCaja() {
        return Caja;
    }

    public void setCaja(String caja) {
        Caja = caja;
    }

    public double getMargenexigido() {
        return Margenexigido;
    }

    public void setMargenexigido(double margenexigido) {
        Margenexigido = margenexigido;
    }
}
