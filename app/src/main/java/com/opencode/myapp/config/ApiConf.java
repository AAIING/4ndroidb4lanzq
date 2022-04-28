package com.opencode.myapp.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiConf {

    //private static final String BASE_URL ="http://10.0.2.2:5000/";
    //private static final String BASE_URL ="http://10.0.2.2:64078/";
    //private static final String BASE_URL ="http://192.168.0.176:64078/";
    private static final String BASE_URL ="http://webbox_api.openpanel.cl/";

    public static CallInterface getData(){
        return getRetrofit(BASE_URL).create(CallInterface.class);
    }

    public static Retrofit getRetrofit(String url){
        //Gson gson = new GsonBuilder().setLenient().create();
        Gson gson = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                .serializeNulls().create();

        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}
