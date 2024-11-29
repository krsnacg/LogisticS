package com.example.logistics.service

import com.example.logistics.model.Cliente
import com.example.logistics.model.Factura
import com.example.logistics.model.IdClienteResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT



interface FacturaService {
    @GET("/api/cliente/facturas/listar")
    suspend fun getFactura(): Response<List<Factura>>

    @POST("/api/cliente/facturas/crear")
    suspend fun createFactura(@Body factura: Factura): Response<Factura>

    @PUT("/api/cliente/facturas/actualizar")
    suspend fun updateFactura(@Body cliente: Factura): Response<Factura>
}