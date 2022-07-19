package com.opencode.myapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pedidos {
    @SerializedName("Registro")
    private int Registro;

    @SerializedName("Comprobante")
    private int Comprobante ;

    @SerializedName("Fecha")
    private String Fecha ;

    @SerializedName("Telefono")
    private String Telefono ;

    @SerializedName("Cliente")
    private String Cliente ;

    @SerializedName("Direccionenvio")
    private String Direccionenvio ;

    @SerializedName("Comunaenvio")
    private String Comunaenvio ;

    @SerializedName("Condominioenvio")
    private String Condominioenvio ;

    @SerializedName("Vendedor")
    private short Vendedor ;

    @SerializedName("Fechaent")
    private String Fechaent ;

    @SerializedName("Horaent")
    private short Horaent ;

    @SerializedName("Am")
    private short Am ;

    @SerializedName("Pm")
    private short Pm ;

    @SerializedName("Entregaboleta")
    private short Entregaboleta ;

    @SerializedName("Obs")
    private String Obs ;

    @SerializedName("Ok")
    private short Ok ;

    @SerializedName("Cajas")
    private short Cajas ;

    @SerializedName("Bolsas")
    private short Bolsas ;

    @SerializedName("Pesototal")
    private double Pesototal ;

    @SerializedName("Anulado")
    private short Anulado ;

    @SerializedName("Factura")
    private short Factura ;

    @SerializedName("Afectoiva")
    private double Afectoiva ;

    @SerializedName("Subtotal")
    private double Subtotal ;

    @SerializedName("Exento")
    private double Exento ;

    @SerializedName("Iva")
    private double Iva ;

    @SerializedName("Total")
    private int Total ;

    @SerializedName("Operario")
    private short Operario ;

    @SerializedName("Estado")
    private short Estado ;

    @SerializedName("Avancereales")
    private short Avancereales ;

    @SerializedName("Facturado")
    private int Facturado ;

    @SerializedName("Tipodoc")
    private short Tipodoc ;

    @SerializedName("Numerodoc")
    private long Numerodoc ;

    @SerializedName("Web")
    private boolean Web ;

    @SerializedName("Ordenweb")
    private int Ordenweb ;

    @SerializedName("Token")
    private String Token ;

    @SerializedName("Indpago")
    private short Indpago ;

    @SerializedName("Indwebpedido")
    private short Indwebpedido ;

    @SerializedName("Fechapago")
    private String Fechapago ;

    @SerializedName("Horapago")
    private String Horapago ;

    @SerializedName("Transferencia")
    private int Transferencia ;

    @SerializedName("Efectivo")
    private int Efectivo ;

    @SerializedName("Chequedia ")
    private int Chequedia ;

    @SerializedName("Chequefecha")
    private int Chequefecha ;

    @SerializedName("Tarjetacr")
    private int Tarjetacr ;

    @SerializedName("Tarjetadb")
    private int Tarjetadb ;

    @SerializedName("Cuentacte")
    private int Cuentacte ;

    @SerializedName("Vuelto")
    private int Vuelto ;

    @SerializedName("Tipomov")
    private String Tipomov ;

    @SerializedName("Rut")
    private int Rut ;

    @SerializedName("Dv")
    private String Dv ;

    @SerializedName("Bodega")
    private short Bodega ;

    @SerializedName("Ordendecompra")
    private int Ordendecompra ;

    @SerializedName("Anotaciones")
    private String Anotaciones ;

    @SerializedName("Cantcomanda")
    private short Cantcomanda ;

    @SerializedName("Pedidopausa")
    private int Pedidopausa ;

    @SerializedName("Empaquerestaurant")
    private int Empaquerestaurant ;

    @SerializedName("Vendedores")
    @Expose
    private Vendedores vendedores;

    @SerializedName("Clientes")
    @Expose
    private Clientes clientes;

    @SerializedName("Fechaelab")
    private String Fechaelab ;

    public String getFechaelab() {
        return Fechaelab;
    }

    public void setFechaelab(String fechaelab) {
        Fechaelab = fechaelab;
    }

    public int getEmpaquerestaurant() {
        return Empaquerestaurant;
    }

    public void setEmpaquerestaurant(int empaquerestaurant) {
        Empaquerestaurant = empaquerestaurant;
    }

    public Clientes getClientes() {
        return clientes;
    }

    public void setClientes(Clientes clientes) {
        this.clientes = clientes;
    }

    public int getPedidopausa() {
        return Pedidopausa;
    }

    public void setPedidopausa(int pedidopausa) {
        Pedidopausa = pedidopausa;
    }

    public Vendedores getVendedores() {
        return vendedores;
    }

    public void setVendedores(Vendedores vendedores) {
        this.vendedores = vendedores;
    }

    public short getCantcomanda() {
        return Cantcomanda;
    }

    public void setCantcomanda(short cantcomanda) {
        Cantcomanda = cantcomanda;
    }


    public int getRegistro() {
        return Registro;
    }

    public void setRegistro(int registro) {
        Registro = registro;
    }

    public int getComprobante() {
        return Comprobante;
    }

    public void setComprobante(int comprobante) {
        Comprobante = comprobante;
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

    public String getDireccionenvio() {
        return Direccionenvio;
    }

    public void setDireccionenvio(String direccionenvio) {
        Direccionenvio = direccionenvio;
    }

    public String getComunaenvio() {
        return Comunaenvio;
    }

    public void setComunaenvio(String comunaenvio) {
        Comunaenvio = comunaenvio;
    }

    public String getCondominioenvio() {
        return Condominioenvio;
    }

    public void setCondominioenvio(String condominioenvio) {
        Condominioenvio = condominioenvio;
    }

    public short getVendedor() {
        return Vendedor;
    }

    public void setVendedor(short vendedor) {
        Vendedor = vendedor;
    }

    public String getFechaent() {
        return Fechaent;
    }

    public void setFechaent(String fechaent) {
        Fechaent = fechaent;
    }

    public short getHoraent() {
        return Horaent;
    }

    public void setHoraent(short horaent) {
        Horaent = horaent;
    }

    public short getAm() {
        return Am;
    }

    public void setAm(short am) {
        Am = am;
    }

    public short getPm() {
        return Pm;
    }

    public void setPm(short pm) {
        Pm = pm;
    }

    public short getEntregaboleta() {
        return Entregaboleta;
    }

    public void setEntregaboleta(short entregaboleta) {
        Entregaboleta = entregaboleta;
    }

    public String getObs() {
        return Obs;
    }

    public void setObs(String obs) {
        Obs = obs;
    }

    public short getOk() {
        return Ok;
    }

    public void setOk(short ok) {
        Ok = ok;
    }

    public short getCajas() {
        return Cajas;
    }

    public void setCajas(short cajas) {
        Cajas = cajas;
    }

    public short getBolsas() {
        return Bolsas;
    }

    public void setBolsas(short bolsas) {
        Bolsas = bolsas;
    }

    public double getPesototal() {
        return Pesototal;
    }

    public void setPesototal(double pesototal) {
        Pesototal = pesototal;
    }

    public short getAnulado() {
        return Anulado;
    }

    public void setAnulado(short anulado) {
        Anulado = anulado;
    }

    public short getFactura() {
        return Factura;
    }

    public void setFactura(short factura) {
        Factura = factura;
    }

    public double getAfectoiva() {
        return Afectoiva;
    }

    public void setAfectoiva(double afectoiva) {
        Afectoiva = afectoiva;
    }

    public double getSubtotal() {
        return Subtotal;
    }

    public void setSubtotal(double subtotal) {
        Subtotal = subtotal;
    }

    public double getExento() {
        return Exento;
    }

    public void setExento(double exento) {
        Exento = exento;
    }

    public double getIva() {
        return Iva;
    }

    public void setIva(double iva) {
        Iva = iva;
    }

    public int getTotal() {
        return Total;
    }

    public void setTotal(int total) {
        Total = total;
    }

    public short getOperario() {
        return Operario;
    }

    public void setOperario(short operario) {
        Operario = operario;
    }

    public short getEstado() {
        return Estado;
    }

    public void setEstado(short estado) {
        Estado = estado;
    }

    public short getAvancereales() {
        return Avancereales;
    }

    public void setAvancereales(short avancereales) {
        Avancereales = avancereales;
    }

    public int getFacturado() {
        return Facturado;
    }

    public void setFacturado(int facturado) {
        Facturado = facturado;
    }

    public short getTipodoc() {
        return Tipodoc;
    }

    public void setTipodoc(short tipodoc) {
        Tipodoc = tipodoc;
    }

    public long getNumerodoc() {
        return Numerodoc;
    }

    public void setNumerodoc(long numerodoc) {
        Numerodoc = numerodoc;
    }

    public boolean isWeb() {
        return Web;
    }

    public void setWeb(boolean web) {
        Web = web;
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

    public short getIndpago() {
        return Indpago;
    }

    public void setIndpago(short indpago) {
        Indpago = indpago;
    }

    public short getIndwebpedido() {
        return Indwebpedido;
    }

    public void setIndwebpedido(short indwebpedido) {
        Indwebpedido = indwebpedido;
    }

    public String getFechapago() {
        return Fechapago;
    }

    public void setFechapago(String fechapago) {
        Fechapago = fechapago;
    }

    public String getHorapago() {
        return Horapago;
    }

    public void setHorapago(String horapago) {
        Horapago = horapago;
    }

    public int getTransferencia() {
        return Transferencia;
    }

    public void setTransferencia(int transferencia) {
        Transferencia = transferencia;
    }

    public int getEfectivo() {
        return Efectivo;
    }

    public void setEfectivo(int efectivo) {
        Efectivo = efectivo;
    }

    public int getChequedia() {
        return Chequedia;
    }

    public void setChequedia(int chequedia) {
        Chequedia = chequedia;
    }

    public int getChequefecha() {
        return Chequefecha;
    }

    public void setChequefecha(int chequefecha) {
        Chequefecha = chequefecha;
    }

    public int getTarjetacr() {
        return Tarjetacr;
    }

    public void setTarjetacr(int tarjetacr) {
        Tarjetacr = tarjetacr;
    }

    public int getTarjetadb() {
        return Tarjetadb;
    }

    public void setTarjetadb(int tarjetadb) {
        Tarjetadb = tarjetadb;
    }

    public int getCuentacte() {
        return Cuentacte;
    }

    public void setCuentacte(int cuentacte) {
        Cuentacte = cuentacte;
    }

    public int getVuelto() {
        return Vuelto;
    }

    public void setVuelto(int vuelto) {
        Vuelto = vuelto;
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

    public String getDv() {
        return Dv;
    }

    public void setDv(String dv) {
        Dv = dv;
    }

    public short getBodega() {
        return Bodega;
    }

    public void setBodega(short bodega) {
        Bodega = bodega;
    }

    public int getOrdendecompra() {
        return Ordendecompra;
    }

    public void setOrdendecompra(int ordendecompra) {
        Ordendecompra = ordendecompra;
    }

    public String getAnotaciones() {
        return Anotaciones;
    }

    public void setAnotaciones(String anotaciones) {
        Anotaciones = anotaciones;
    }
}
