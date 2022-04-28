package com.opencode.myapp.Models;

import com.google.gson.annotations.SerializedName;

public class Sesiones {

    @SerializedName("Codigo")
    public int Codigo ;

    @SerializedName("UsuariosReferencia")
    public int UsuariosReferencia ;

    @SerializedName("CodigoSesionEmpaque")
    public int CodigoSesionEmpaque ;

    @SerializedName("FechaInicio")
    public String FechaInicio ;

    @SerializedName("HoraInicio")
    public String HoraInicio ;

    @SerializedName("FechaTermino")
    public String FechaTermino ;

    @SerializedName("HoraTermino")
    public String HoraTermino ;

    public int getCodigo() {
        return Codigo;
    }

    public void setCodigo(int codigo) {
        Codigo = codigo;
    }

    public int getUsuariosReferencia() {
        return UsuariosReferencia;
    }

    public void setUsuariosReferencia(int usuariosReferencia) {
        UsuariosReferencia = usuariosReferencia;
    }

    public int getCodigoSesionEmpaque() {
        return CodigoSesionEmpaque;
    }

    public void setCodigoSesionEmpaque(int codigoSesionEmpaque) {
        CodigoSesionEmpaque = codigoSesionEmpaque;
    }

    public String getFechaInicio() {
        return FechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        FechaInicio = fechaInicio;
    }

    public String getHoraInicio() {
        return HoraInicio;
    }

    public void setHoraInicio(String horaInicio) {
        HoraInicio = horaInicio;
    }

    public String getFechaTermino() {
        return FechaTermino;
    }

    public void setFechaTermino(String fechaTermino) {
        FechaTermino = fechaTermino;
    }

    public String getHoraTermino() {
        return HoraTermino;
    }

    public void setHoraTermino(String horaTermino) {
        HoraTermino = horaTermino;
    }
}
