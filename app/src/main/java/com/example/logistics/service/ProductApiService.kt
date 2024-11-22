package com.example.logistics.service

import com.example.logistics.model.ProductRequest
import com.example.logistics.model.ProductResponse
import com.example.logistics.data.CategoryChart
import com.example.logistics.data.ExpiringProduct
import com.example.logistics.data.LowerStockProduct
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface ProductApiService {
    @GET("producto/listar")
    suspend fun getAllProducts(): Response<List<ProductResponse>>

    @POST("producto/guardar-p")
    suspend fun saveProductWithBatches(@Body product: ProductRequest): Response<ProductResponse>

    @PUT("producto/actualizar-p")
    suspend fun updateProductWithBatches(@Body product: ProductRequest): Response<ProductRequest>

    @GET("producto/last-code")
    suspend fun getProductLastCode(): Response<String> // Response<Map<String,String>>

    @GET("lotes/last-code")
    suspend fun getBatchLastCode(): Response<String> // Response<Map<String,String>>

    @GET("formas/for-com")
    suspend fun getFormas(): Response<List<String>>

    @GET("categorias/cat-com")
    suspend fun getCategories(): Response<List<String>>

    @GET("api/almacen/producto/lowerstock")
    suspend fun getLowerStockProduct(): Response<LowerStockProduct>

    @GET("api/almacen/producto/expiring")
    suspend fun getExpiringProduct(): Response<ExpiringProduct>

    @GET("api/almacen/producto/total")
    suspend fun getTotalProducts(): Response<Int>

    @GET("api/almacen/categorias/cantidad")
    suspend fun getQuantityByCategory(): Response<List<CategoryChart>>

    @GET("api/almacen/categorias/total")
    suspend fun getTotalCategorias(): Response<Int>

    @GET("api/almacen/lotes/total")
    suspend fun getTotalLotes(): Response<Int>

    @GET("api/almacen/producto/last-code")
    suspend fun getLastProductCode(): Response<String>
}