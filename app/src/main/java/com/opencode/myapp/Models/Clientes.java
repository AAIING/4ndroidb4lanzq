package com.opencode.myapp.Models;

import com.google.gson.annotations.SerializedName;

public class Clientes {

    @SerializedName("Telefono")
    public String Telefono ;

    @SerializedName("Nombre")
    public String Nombre ;

    @SerializedName("Direccion")
    public String Direccion ;

    @SerializedName("Ciudad")
    public String Ciudad ;

    @SerializedName("Comuna")
    public String Comuna ;

    @SerializedName("Condominio")
    public String Condominio ;

    @SerializedName("Email")
    public String Email ;

    @SerializedName("Nombrecontacto")
    public String Nombrecontacto ;

    @SerializedName("Vendedor")
    public byte Vendedor ;

    @SerializedName("Obs")
    public String Obs ;

    @SerializedName("Rut")
    public int Rut ;

    @SerializedName("Dv")
    public String Dv ;

    @SerializedName("Giro")
    public String Giro ;

    @SerializedName("Categoria")
    public String Categoria ;

    @SerializedName("Bloqueado")
    public String Bloqueado ;

    @SerializedName("Usuariocall")
    public String Usuariocall ;

    @SerializedName("Fechacall")
    public String Fechacall ;

    @SerializedName("Fechacall2")
    public String Fechacall2 ;

    @SerializedName("Anotacionescall")
    public String Anotacionescall ;

    @SerializedName("Lista")
    public short Lista ;

    @SerializedName("Referencia")
    public int Referencia ;

    @SerializedName("Nuevo")
    public short Nuevo ;

    /***/
    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public String getCiudad() {
        return Ciudad;
    }

    public void setCiudad(String ciudad) {
        Ciudad = ciudad;
    }

    public String getComuna() {
        return Comuna;
    }

    public void setComuna(String comuna) {
        Comuna = comuna;
    }

    public String getCondominio() {
        return Condominio;
    }

    public void setCondominio(String condominio) {
        Condominio = condominio;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getNombrecontacto() {
        return Nombrecontacto;
    }

    public void setNombrecontacto(String nombrecontacto) {
        Nombrecontacto = nombrecontacto;
    }

    public byte getVendedor() {
        return Vendedor;
    }

    public void setVendedor(byte vendedor) {
        Vendedor = vendedor;
    }

    public String getObs() {
        return Obs;
    }

    public void setObs(String obs) {
        Obs = obs;
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

    public String getGiro() {
        return Giro;
    }

    public void setGiro(String giro) {
        Giro = giro;
    }

    public String getCategoria() {
        return Categoria;
    }

    public void setCategoria(String categoria) {
        Categoria = categoria;
    }

    public String getBloqueado() {
        return Bloqueado;
    }

    public void setBloqueado(String bloqueado) {
        Bloqueado = bloqueado;
    }

    public String getUsuariocall() {
        return Usuariocall;
    }

    public void setUsuariocall(String usuariocall) {
        Usuariocall = usuariocall;
    }

    public String getFechacall() {
        return Fechacall;
    }

    public void setFechacall(String fechacall) {
        Fechacall = fechacall;
    }

    public String getFechacall2() {
        return Fechacall2;
    }

    public void setFechacall2(String fechacall2) {
        Fechacall2 = fechacall2;
    }

    public String getAnotacionescall() {
        return Anotacionescall;
    }

    public void setAnotacionescall(String anotacionescall) {
        Anotacionescall = anotacionescall;
    }

    public short getLista() {
        return Lista;
    }

    public void setLista(short lista) {
        Lista = lista;
    }

    public int getReferencia() {
        return Referencia;
    }

    public void setReferencia(int referencia) {
        Referencia = referencia;
    }

    public short getNuevo() {
        return Nuevo;
    }

    public void setNuevo(short nuevo) {
        Nuevo = nuevo;
    }
}
