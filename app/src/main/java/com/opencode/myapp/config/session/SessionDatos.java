package com.opencode.myapp.config.session;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionDatos {

    private final SharedPreferences preferences;
    private final SharedPreferences.Editor editor;
    Context mContext;

    public SessionDatos(Context mContext) {
        this.mContext = mContext;
        preferences=mContext.getSharedPreferences(SessionKeys.data_app.name(), Context.MODE_PRIVATE);
        editor=preferences.edit();
    }

    public HashMap<SessionKeys, String> getRecord(){
        HashMap<SessionKeys, String> map = new HashMap<>();
        //comentario observacion
        map.put(SessionKeys.idRegistroPedido, preferences.getString(SessionKeys.idRegistroPedido.name(), "0"));
        map.put(SessionKeys.pesaMac, preferences.getString(SessionKeys.pesaMac.name(), ""));

        return  map;
    }

    public void IdRegistro(String registro){
        editor.putString(SessionKeys.idRegistroPedido.name(), registro);
        editor.commit();
    }

    public void setLogin(){
        editor.putBoolean(SessionKeys.isLoggedIn.name(), true);
        editor.commit();
    }

    public void setPesa(String mac){
        editor.putBoolean(SessionKeys.pesaOn.name(), true);
        editor.putString(SessionKeys.pesaMac.name(), mac);
        editor.commit();
    }

    public void pesaOff(){
        editor.putBoolean(SessionKeys.pesaOn.name(), false);
        editor.commit();
    }

    public void cleanSesion(){
        editor.clear();
        editor.commit();
    }

    public boolean CheckPesa(){
        boolean isPesaOn;

        if(preferences.getBoolean(SessionKeys.pesaOn.name(),false)){
            isPesaOn = true;
        }else {
            isPesaOn = false;
        }

        return isPesaOn;
    }

    public boolean CheckSession(){
        boolean islog;

        if(preferences.getBoolean(SessionKeys.isLoggedIn.name(),false)){
            islog = true;
        }else {
            islog = false;
        }

        return islog;
    }
}
