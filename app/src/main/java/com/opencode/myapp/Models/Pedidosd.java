package com.opencode.myapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Pedidosd implements Comparable, Cloneable{

    @Override
    public Pedidosd clone(){
        Pedidosd clone;
        try{
            clone = (Pedidosd) super.clone();
        }catch(CloneNotSupportedException e){
            throw new RuntimeException(e); //should not happen
        }
        //
        return clone;
    }

    @Override
    public int compareTo(Object obj) {
        Pedidosd pedidosd = (Pedidosd) obj;
        //
        if(pedidosd.getReadPesaje().equals(this.readPesaje) &&
                pedidosd.getCalcPesaje().equals(this.CalcPesaje) &&
                    pedidosd.getPesototalfila().equals(this.Pesototalfila) &&
                        pedidosd.getTara().equals(this.Tara))
            return 0;

        return 1;
    }

    @SerializedName("Registro")
    public int Registro ;

    @SerializedName("Numerodoc")
    public int Numerodoc ;

    @SerializedName("Tipodoc")
    public int Tipodoc ;

    @SerializedName("Fecha")
    public String Fecha ;

    @SerializedName("Telefono")
    public String Telefono ;

    @SerializedName("Cliente")
    public String Cliente ;

    @SerializedName("Vendedor")
    public short Vendedor ;

    @SerializedName("Codigo")
    public int Codigo ;

    @SerializedName("Detalle")
    public String Detalle ;

    @SerializedName("Cantidad")
    public double Cantidad ;

    @SerializedName("Cantidadreal")
    public double Cantidadreal ;

    @SerializedName("Unidad")
    public String Unidad ;

    @SerializedName("Precio")
    public int Precio ;

    @SerializedName("Valor")
    public int Valor ;

    @SerializedName("Costo")
    public int Costo ;

    @SerializedName("Presentacion")
    public short Presentacion ;

    @SerializedName("Esquelon")
    public short Esquelon ;

    @SerializedName("Cabeza")
    public short Cabeza ;

    @SerializedName("Ok")
    public short Ok ;

    @SerializedName("Anulado")
    public short Anulado ;

    @SerializedName("Abastec")
    public int Abastec ;

    @SerializedName("Obs")
    public String Obs ;

    @SerializedName("Ordenweb")
    public int Ordenweb ;

    @SerializedName("Token")
    public String Token ;

    @SerializedName("Referencia")
    public int Referencia ;

    @SerializedName("Descuento1")
    public double Descuento1 ;

    @SerializedName("Bodega")
    public short Bodega ;

    @SerializedName("Comprobante")
    public int Comprobante ;

    @SerializedName("Numeroitem")
    public int Numeroitem ;

    @SerializedName("Tipomov")
    public String Tipomov ;

    @SerializedName("Rut")
    public int Rut ;

    @SerializedName("Codigov")
    public int Codigov ;

    @SerializedName("Cantidadv")
    public double Cantidadv ;

    @SerializedName("Unidadv")
    public String Unidadv ;

    @SerializedName("Preciov")
    public int Preciov ;

    @SerializedName("Saldou")
    public double Saldou ;

    @SerializedName("Saldov")
    public double Saldov ;

    @SerializedName("Valorv")
    public double Valorv ;

    @SerializedName("Entradas")
    public double Entradas ;

    @SerializedName("Salidas")
    public double Salidas ;

    @SerializedName("Nrodocref")
    public int Nrodocref ;

    @SerializedName("Presentaciones")
    @Expose
    public Presentaciones presentaciones;

    @SerializedName("Productos")
    @Expose
    public Productos productos;

    @SerializedName("PresentacionesHasProductos")
    @Expose
    public PresentacionesHasProductos preshasprod;

    public String Tara ="0";

    public String getTara() {
        return Tara;
    }

    public void setTara(String tara) {
        Tara = tara;
    }

    public String Pesototalfila = "" ;

    public String getPesototalfila() {
        return Pesototalfila;
    }

    public void setPesototalfila(String pesototalfila) {
        Pesototalfila = pesototalfila;
    }

    public PresentacionesHasProductos getPreshasprod() {
        return preshasprod;
    }

    public void setPreshasprod(PresentacionesHasProductos preshasprod) {
        this.preshasprod = preshasprod;
    }

    public String CalcPesaje = "";

    public double minPeso;

    public double maxPeso;

    public double getMinPeso() {
        return minPeso;
    }

    public void setMinPeso(double minPeso) {
        this.minPeso = minPeso;
    }

    public double getMaxPeso() {
        return maxPeso;
    }

    public void setMaxPeso(double maxPeso) {
        this.maxPeso = maxPeso;
    }

    public String getCalcPesaje() {
        return CalcPesaje;
    }

    public void setCalcPesaje(String calcPesaje) {
        CalcPesaje = calcPesaje;
    }

    public Productos getProductos() {
        return productos;
    }

    public void setProductos(Productos productos) {
        this.productos = productos;
    }

    public double Pesaje;

    public String readPesaje = "";

    public String getReadPesaje() {
        return readPesaje;
    }

    public void setReadPesaje(String readPesaje) {
        this.readPesaje = readPesaje;
    }

    public double getPesaje() {
        return Pesaje;
    }

    public void setPesaje(double pesaje) {
        Pesaje = pesaje;
    }

    public Presentaciones getPresentaciones() {
        return presentaciones;
    }

    public void setPresentaciones(Presentaciones presentaciones) {
        this.presentaciones = presentaciones;
    }

    public int getRegistro() {
        return Registro;
    }

    public void setRegistro(int registro) {
        Registro = registro;
    }

    public int getNumerodoc() {
        return Numerodoc;
    }

    public void setNumerodoc(int numerodoc) {
        Numerodoc = numerodoc;
    }

    public int getTipodoc() {
        return Tipodoc;
    }

    public void setTipodoc(int tipodoc) {
        Tipodoc = tipodoc;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public String getCliente() {
        return Cliente;
    }

    public void setCliente(String cliente) {
        Cliente = cliente;
    }

    public short getVendedor() {
        return Vendedor;
    }

    public void setVendedor(short vendedor) {
        Vendedor = vendedor;
    }

    public int getCodigo() {
        return Codigo;
    }

    public void setCodigo(int codigo) {
        Codigo = codigo;
    }

    public String getDetalle() {
        return Detalle;
    }

    public void setDetalle(String detalle) {
        Detalle = detalle;
    }

    public double getCantidad() {
        return Cantidad;
    }

    public void setCantidad(double cantidad) {
        Cantidad = cantidad;
    }

    public double getCantidadreal() {
        return Cantidadreal;
    }

    public void setCantidadreal(double cantidadreal) {
        Cantidadreal = cantidadreal;
    }

    public String getUnidad() {
        return Unidad;
    }

    public void setUnidad(String unidad) {
        Unidad = unidad;
    }

    public int getPrecio() {
        return Precio;
    }

    public void setPrecio(int precio) {
        Precio = precio;
    }

    public int getValor() {
        return Valor;
    }

    public void setValor(int valor) {
        Valor = valor;
    }

    public int getCosto() {
        return Costo;
    }

    public void setCosto(int costo) {
        Costo = costo;
    }

    public short getPresentacion() {
        return Presentacion;
    }

    public void setPresentacion(short presentacion) {
        Presentacion = presentacion;
    }

    public short getEsquelon() {
        return Esquelon;
    }

    public void setEsquelon(short esquelon) {
        Esquelon = esquelon;
    }

    public short getCabeza() {
        return Cabeza;
    }

    public void setCabeza(short cabeza) {
        Cabeza = cabeza;
    }

    public short getOk() {
        return Ok;
    }

    public void setOk(short ok) {
        Ok = ok;
    }

    public short getAnulado() {
        return Anulado;
    }

    public void setAnulado(short anulado) {
        Anulado = anulado;
    }

    public int getAbastec() {
        return Abastec;
    }

    public void setAbastec(int abastec) {
        Abastec = abastec;
    }

    public String getObs() {
        return Obs;
    }

    public void setObs(String obs) {
        Obs = obs;
    }

    public int getOrdenweb() {
        return Ordenweb;
    }

    public void setOrdenweb(int ordenweb) {
        Ordenweb = ordenweb;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public int getReferencia() {
        return Referencia;
    }

    public void setReferencia(int referencia) {
        Referencia = referencia;
    }

    public double getDescuento1() {
        return Descuento1;
    }

    public void setDescuento1(double descuento1) {
        Descuento1 = descuento1;
    }

    public short getBodega() {
        return Bodega;
    }

    public void setBodega(short bodega) {
        Bodega = bodega;
    }

    public int getComprobante() {
        return Comprobante;
    }

    public void setComprobante(int comprobante) {
        Comprobante = comprobante;
    }

    public int getNumeroitem() {
        return Numeroitem;
    }

    public void setNumeroitem(int numeroitem) {
        Numeroitem = numeroitem;
    }

    public String getTipomov() {
        return Tipomov;
    }

    public void setTipomov(String tipomov) {
        Tipomov = tipomov;
    }

    public int getRut() {
        return Rut;
    }

    public void setRut(int rut) {
        Rut = rut;
    }

    public int getCodigov() {
        return Codigov;
    }

    public void setCodigov(int codigov) {
        Codigov = codigov;
    }

    public double getCantidadv() {
        return Cantidadv;
    }

    public void setCantidadv(double cantidadv) {
        Cantidadv = cantidadv;
    }

    public String getUnidadv() {
        return Unidadv;
    }

    public void setUnidadv(String unidadv) {
        Unidadv = unidadv;
    }

    public int getPreciov() {
        return Preciov;
    }

    public void setPreciov(int preciov) {
        Preciov = preciov;
    }

    public double getSaldou() {
        return Saldou;
    }

    public void setSaldou(double saldou) {
        Saldou = saldou;
    }

    public double getSaldov() {
        return Saldov;
    }

    public void setSaldov(double saldov) {
        Saldov = saldov;
    }

    public double getValorv() {
        return Valorv;
    }

    public void setValorv(double valorv) {
        Valorv = valorv;
    }

    public double getEntradas() {
        return Entradas;
    }

    public void setEntradas(double entradas) {
        Entradas = entradas;
    }

    public double getSalidas() {
        return Salidas;
    }

    public void setSalidas(double salidas) {
        Salidas = salidas;
    }

    public int getNrodocref() {
        return Nrodocref;
    }

    public void setNrodocref(int nrodocref) {
        Nrodocref = nrodocref;
    }


}
