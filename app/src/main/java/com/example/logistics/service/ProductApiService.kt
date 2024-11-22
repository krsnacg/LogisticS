package com.example.logistics.service
import com.example.logistics.model.ProductRequest
import com.example.logistics.model.ProductResponse
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

}