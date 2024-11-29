package com.example.logistics.service

import com.example.logistics.model.Cliente
import com.example.logistics.model.IdClienteResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ClientService {

    @GET("/api/cliente/clientes/listarDto")
    suspend fun getClientes(): Response<List<Cliente>>

    @GET("/api/cliente/clientes/{id}")
    suspend fun findCliente(@Path("id") id: String): Response<IdClienteResponse>

    @POST("/api/cliente/clientes/insertar")
    suspend fun createCliente(@Body cliente: Cliente): Response<Cliente>
}