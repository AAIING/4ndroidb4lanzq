package com.opencode.myapp.Models;

import com.google.gson.annotations.SerializedName;

public class PresentacionesHasProductos {

    @SerializedName("Presentacionescodigo")
    public int Presentacionescodigo ;

    @SerializedName("Productoscodigo")
    public int Productoscodigo ;

    @SerializedName("Rendimiento")
    public double Rendimiento ;

    public int getPresentacionescodigo() {
        return Presentacionescodigo;
    }

    public void setPresentacionescodigo(int presentacionescodigo) {
        Presentacionescodigo = presentacionescodigo;
    }

    public int getProductoscodigo() {
        return Productoscodigo;
    }

    public void setProductoscodigo(int productoscodigo) {
        Productoscodigo = productoscodigo;
    }

    public double getRendimiento() {
        return Rendimiento;
    }

    public void setRendimiento(double rendimiento) {
        Rendimiento = rendimiento;
    }
}
