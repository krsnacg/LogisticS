package com.example.logistics.service
import com.example.logistics.model.Product
import com.example.logistics.model.ProductRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface ProductApiService {
    @GET("listar")
    suspend fun listarProductos(): Response<List<Product>>

    @POST("guardar-p")
    suspend fun createProductoWithLots(@Body producto: ProductRequest): Response<Product>

    @PUT("actualizar-p")
    suspend fun updateProductoWithLots(@Body producto: ProductRequest): Response<Product>

}