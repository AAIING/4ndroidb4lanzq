package com.opencode.myapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Productos {

    @SerializedName("Codigo")
    public int Codigo ;

    @SerializedName("Nombre")
    public String Nombre ;

    @SerializedName("Nombrecorto")
    public String Nombrecorto ;

    @SerializedName("Unidad")
    public String Unidad ;

    @SerializedName("Pesogrs")
    public int Pesogrs ;

    @SerializedName("Insumo")
    public int Insumo ;

    @SerializedName("Ninsumo")
    public String Ninsumo ;

    @SerializedName("Precio")
    public int Precio ;

    @SerializedName("Costo")
    public int Costo ;

    @SerializedName("Presentacion")
    public boolean Presentacion ;

    @SerializedName("Oferta")
    public boolean Oferta ;

    @SerializedName("Preciooferta")
    public double Preciooferta ;

    @SerializedName("Preciorestaurant")
    public double Preciorestaurant ;

    @SerializedName("Observaciones")
    public String Observaciones ;

    @SerializedName("Ordenamiento")
    public short Ordenamiento ;

    @SerializedName("Iniciooferta")
    public String Iniciooferta ;

    @SerializedName("Venceoferta")
    public String Venceoferta ;

    @SerializedName("Familia")
    public int Familia ;

    @SerializedName("Nfamilia")
    public String Nfamilia ;

    @SerializedName("Agotado")
    public short Agotado ;

    @SerializedName("Indpagweb")
    public short Indpagweb ;

    @SerializedName("Indcabezaesquelon")
    public short Indcabezaesquelon ;

    @SerializedName("Inddescripcion")
    public short Inddescripcion ;

    @SerializedName("Titulodescripcion")
    public String Titulodescripcion ;

    @SerializedName("Descripcion")
    public String Descripcion ;

    @SerializedName("Titulovideo")
    public String Titulovideo ;

    @SerializedName("Codigovideo1")
    public String Codigovideo1 ;

    @SerializedName("Codigovideo2")
    public String Codigovideo2 ;

    @SerializedName("Cantidaddesde")
    public double Cantidaddesde ;

    @SerializedName("Codigopre")
    public short Codigopre ;

    @SerializedName("Lista")
    public short Lista ;

    @SerializedName("Nuevooc")
    public short Nuevooc ;

    @SerializedName("Ultmodificacion")
    public String Ultmodificacion ;

    @SerializedName("Pack")
    public short Pack ;

    @SerializedName("Rendimiento")
    public double Rendimiento ;

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

    public String getNombrecorto() {
        return Nombrecorto;
    }

    public void setNombrecorto(String nombrecorto) {
        Nombrecorto = nombrecorto;
    }

    public String getUnidad() {
        return Unidad;
    }

    public void setUnidad(String unidad) {
        Unidad = unidad;
    }

    public int getPesogrs() {
        return Pesogrs;
    }

    public void setPesogrs(int pesogrs) {
        Pesogrs = pesogrs;
    }

    public int getInsumo() {
        return Insumo;
    }

    public void setInsumo(int insumo) {
        Insumo = insumo;
    }

    public String getNinsumo() {
        return Ninsumo;
    }

    public void setNinsumo(String ninsumo) {
        Ninsumo = ninsumo;
    }

    public int getPrecio() {
        return Precio;
    }

    public void setPrecio(int precio) {
        Precio = precio;
    }

    public int getCosto() {
        return Costo;
    }

    public void setCosto(int costo) {
        Costo = costo;
    }

    public boolean isPresentacion() {
        return Presentacion;
    }

    public void setPresentacion(boolean presentacion) {
        Presentacion = presentacion;
    }

    public boolean isOferta() {
        return Oferta;
    }

    public void setOferta(boolean oferta) {
        Oferta = oferta;
    }

    public double getPreciooferta() {
        return Preciooferta;
    }

    public void setPreciooferta(double preciooferta) {
        Preciooferta = preciooferta;
    }

    public double getPreciorestaurant() {
        return Preciorestaurant;
    }

    public void setPreciorestaurant(double preciorestaurant) {
        Preciorestaurant = preciorestaurant;
    }

    public String getObservaciones() {
        return Observaciones;
    }

    public void setObservaciones(String observaciones) {
        Observaciones = observaciones;
    }

    public short getOrdenamiento() {
        return Ordenamiento;
    }

    public void setOrdenamiento(short ordenamiento) {
        Ordenamiento = ordenamiento;
    }

    public String getIniciooferta() {
        return Iniciooferta;
    }

    public void setIniciooferta(String iniciooferta) {
        Iniciooferta = iniciooferta;
    }

    public String getVenceoferta() {
        return Venceoferta;
    }

    public void setVenceoferta(String venceoferta) {
        Venceoferta = venceoferta;
    }

    public int getFamilia() {
        return Familia;
    }

    public void setFamilia(int familia) {
        Familia = familia;
    }

    public String getNfamilia() {
        return Nfamilia;
    }

    public void setNfamilia(String nfamilia) {
        Nfamilia = nfamilia;
    }

    public short getAgotado() {
        return Agotado;
    }

    public void setAgotado(short agotado) {
        Agotado = agotado;
    }

    public short getIndpagweb() {
        return Indpagweb;
    }

    public void setIndpagweb(short indpagweb) {
        Indpagweb = indpagweb;
    }

    public short getIndcabezaesquelon() {
        return Indcabezaesquelon;
    }

    public void setIndcabezaesquelon(short indcabezaesquelon) {
        Indcabezaesquelon = indcabezaesquelon;
    }

    public short getInddescripcion() {
        return Inddescripcion;
    }

    public void setInddescripcion(short inddescripcion) {
        Inddescripcion = inddescripcion;
    }

    public String getTitulodescripcion() {
        return Titulodescripcion;
    }

    public void setTitulodescripcion(String titulodescripcion) {
        Titulodescripcion = titulodescripcion;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getTitulovideo() {
        return Titulovideo;
    }

    public void setTitulovideo(String titulovideo) {
        Titulovideo = titulovideo;
    }

    public String getCodigovideo1() {
        return Codigovideo1;
    }

    public void setCodigovideo1(String codigovideo1) {
        Codigovideo1 = codigovideo1;
    }

    public String getCodigovideo2() {
        return Codigovideo2;
    }

    public void setCodigovideo2(String codigovideo2) {
        Codigovideo2 = codigovideo2;
    }

    public double getCantidaddesde() {
        return Cantidaddesde;
    }

    public void setCantidaddesde(double cantidaddesde) {
        Cantidaddesde = cantidaddesde;
    }

    public short getCodigopre() {
        return Codigopre;
    }

    public void setCodigopre(short codigopre) {
        Codigopre = codigopre;
    }

    public short getLista() {
        return Lista;
    }

    public void setLista(short lista) {
        Lista = lista;
    }

    public short getNuevooc() {
        return Nuevooc;
    }

    public void setNuevooc(short nuevooc) {
        Nuevooc = nuevooc;
    }

    public String getUltmodificacion() {
        return Ultmodificacion;
    }

    public void setUltmodificacion(String ultmodificacion) {
        Ultmodificacion = ultmodificacion;
    }

    public short getPack() {
        return Pack;
    }

    public void setPack(short pack) {
        Pack = pack;
    }

    public double getRendimiento() {
        return Rendimiento;
    }

    public void setRendimiento(double rendimiento) {
        Rendimiento = rendimiento;
    }
}
