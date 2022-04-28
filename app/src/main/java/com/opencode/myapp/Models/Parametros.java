package com.opencode.myapp.Models;

import com.google.gson.annotations.SerializedName;

public class Parametros {

    @SerializedName("Iva")
    public double Iva ;

    @SerializedName("Tramo1")
    public int Tramo1 ;

    @SerializedName("Tramo2")
    public int Tramo2 ;

    @SerializedName("Topedescuento")
    public int Topedescuento ;

    @SerializedName("Valort1")
    public int Valort1 ;

    @SerializedName("Valort2")
    public int Valort2 ;

    @SerializedName("Email")
    public String Email ;

    @SerializedName("Password")
    public String Password ;

    @SerializedName("Smtp")
    public String Smtp ;

    @SerializedName("Puerto")
    public int Puerto ;

    @SerializedName("Emailrespuesta")
    public String Emailrespuesta ;

    //@SerializedName("Encabezadoprecios")
    //public byte[] Encabezadoprecios ;

    //@SerializedName("Footerprecios")
    //public byte[] Footerprecios ;

    @SerializedName("Compraminima")
    public int Compraminima ;

    @SerializedName("TituloNotificacion")
    public String TituloNotificacion ;

    @SerializedName("ColorNotificacion")
    public String ColorNotificacion ;

    @SerializedName("Diasmorosos1")
    public int Diasmorosos1 ;

    @SerializedName("Diasmorosos2")
    public int Diasmorosos2 ;

    @SerializedName("Diasmorosos3")
    public int Diasmorosos3 ;

    @SerializedName("BandaInfo")
    public String BandaInfo ;

    @SerializedName("Pesotope")
    public double PesoTope ;

    public double getPesoTope() {
        return PesoTope;
    }

    public void setPesoTope(double pesoTope) {
        PesoTope = pesoTope;
    }

    public double getIva() {
        return Iva;
    }

    public void setIva(double iva) {
        Iva = iva;
    }

    public int getTramo1() {
        return Tramo1;
    }

    public void setTramo1(int tramo1) {
        Tramo1 = tramo1;
    }

    public int getTramo2() {
        return Tramo2;
    }

    public void setTramo2(int tramo2) {
        Tramo2 = tramo2;
    }

    public int getTopedescuento() {
        return Topedescuento;
    }

    public void setTopedescuento(int topedescuento) {
        Topedescuento = topedescuento;
    }

    public int getValort1() {
        return Valort1;
    }

    public void setValort1(int valort1) {
        Valort1 = valort1;
    }

    public int getValort2() {
        return Valort2;
    }

    public void setValort2(int valort2) {
        Valort2 = valort2;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getSmtp() {
        return Smtp;
    }

    public void setSmtp(String smtp) {
        Smtp = smtp;
    }

    public int getPuerto() {
        return Puerto;
    }

    public void setPuerto(int puerto) {
        Puerto = puerto;
    }

    public String getEmailrespuesta() {
        return Emailrespuesta;
    }

    public void setEmailrespuesta(String emailrespuesta) {
        Emailrespuesta = emailrespuesta;
    }

    public int getCompraminima() {
        return Compraminima;
    }

    public void setCompraminima(int compraminima) {
        Compraminima = compraminima;
    }

    public String getTituloNotificacion() {
        return TituloNotificacion;
    }

    public void setTituloNotificacion(String tituloNotificacion) {
        TituloNotificacion = tituloNotificacion;
    }

    public String getColorNotificacion() {
        return ColorNotificacion;
    }

    public void setColorNotificacion(String colorNotificacion) {
        ColorNotificacion = colorNotificacion;
    }

    public int getDiasmorosos1() {
        return Diasmorosos1;
    }

    public void setDiasmorosos1(int diasmorosos1) {
        Diasmorosos1 = diasmorosos1;
    }

    public int getDiasmorosos2() {
        return Diasmorosos2;
    }

    public void setDiasmorosos2(int diasmorosos2) {
        Diasmorosos2 = diasmorosos2;
    }

    public int getDiasmorosos3() {
        return Diasmorosos3;
    }

    public void setDiasmorosos3(int diasmorosos3) {
        Diasmorosos3 = diasmorosos3;
    }

    public String getBandaInfo() {
        return BandaInfo;
    }

    public void setBandaInfo(String bandaInfo) {
        BandaInfo = bandaInfo;
    }
}
