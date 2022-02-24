package com.opencode.myapp.config;

import com.opencode.myapp.Models.Login;
import com.opencode.myapp.Models.Pedidos;
import com.opencode.myapp.Models.Pedidosd;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface CallInterface {

    @PUT("api/pedidos/updtdetalle")
    Call<Pedidosd> postPedidod(@Query("registro") int registro, @Body RequestBody body);

    @GET("api/login/loguser")
    Call<Login> getLogin(@Query("usr") String usr, @Query("contrasena") String contrasena);

    @GET("api/pedidos/pendientes")
    Call<List<Pedidos>> getPedidosPendientes();

    @GET("api/pedidos/detalle")
    Call<List<Pedidosd>> getPedidosdDetalle(@Query("registro") int registro);

}
