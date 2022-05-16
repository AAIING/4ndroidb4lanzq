package com.opencode.myapp.config;

import com.google.gson.JsonObject;
import com.opencode.myapp.Models.Login;
import com.opencode.myapp.Models.Operarios;
import com.opencode.myapp.Models.Parametros;
import com.opencode.myapp.Models.Pedidos;
import com.opencode.myapp.Models.Pedidosd;
import com.opencode.myapp.Models.Sesiones;

import org.json.JSONObject;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface CallInterface {

    @GET("api/login/logsesion")
    Call<Login> getLogSesion(@Query("idoperario") int idoperario,
                             @Query("idsesion") int idsesion);

    @GET("api/login/listaoperarios")
    Call<List<Operarios>> getListaOperarios();

    @GET("api/pedidos/sesionempaque")
    Call<Sesiones> getSesionEmpaque(@Query("idsesionoperario") int idsesion,
                                    @Query("idoperario") int idoperario,
                                    @Query("idpedido") int idpedido,
                                    @Query("idsesionempaque") int idsesionempaque,
                                    @Query("status") int status,
                                    @Query("tiempoempaque") String tiempoempaque);

    @GET("api/login/loguser")
    Call<Login> getLogin(@Query("usr") String usr,
                         @Query("contrasena") String contrasena,
                         @Query("idsesion") int idsesion);

    @GET("api/pedidos/pendientes")
    Observable<List<Pedidos>> getPedidosPendientes(@Query("idoperario") int idoperario);

    @GET("api/pedidos/parametros")
    Observable<Parametros> getParametros();

    @GET("api/pedidos/detalle")
    Call<List<Pedidosd>> getPedidosdDetalle(@Query("registro") int registro);

    @GET("api/pedidos/cuentacomanda")
    Call<Pedidos> getCantComanda(@Query("registro") int registro,
                                  @Query("cantcomanda") int cantcomanda);

    //***/
    @PUT("api/pedidos/updtpedido")
    Call<Pedidos> postPedido(@Query("registro") int registro,
                             @Query("idoperario") int idoperario,
                             @Body RequestBody body);

    @PUT("api/pedidos/updtdetalle")
    Call<Pedidosd> postPedidod(@Query("registro") int registro,
                               @Query("codigo") int codigo,
                               @Body RequestBody body);


}



