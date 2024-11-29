package com.example.logistics.network
import com.example.logistics.data.pedido.Pedido
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path


interface PedidoApiService {
    //Listar un pedido
    @GET("listar/{id}")
    fun getPedido(@Path("id") id: String): Call<Pedido>

    //Actualizar un pedido
    @PUT("actualizar/{id}")
    fun updatePedido(@Path("id") id: String, @Body pedido: Pedido): Call<Pedido>

}