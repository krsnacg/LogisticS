package com.example.logistics.service
import com.example.logistics.data.ExpiringProduct
import com.example.logistics.data.LowerStockProduct
import com.example.logistics.model.ProductRequest
import com.example.logistics.model.ProductResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface ProductApiService {
    @GET("producto/listar")
    suspend fun listProducts(): Response<List<ProductRequest>>

    @POST("producto/guardar-p")
    suspend fun saveProductWithBatches(@Body product: ProductRequest): Response<ProductResponse>

    @PUT("producto/actualizar-p")
    suspend fun updateProductWithBatches(@Body product: ProductRequest): Response<ProductRequest>

    @GET("producto/last-code")
    suspend fun getProductLastCode(): Response<Map<String, String>>

    @GET("lotes/last-code")
    suspend fun getBatchLastCode(): Response<Map<String,String>>


    @GET("api/almacen/producto/lowerstock")
    suspend fun getLowerStockProduct(): Response<LowerStockProduct>

    @GET("api/almacen/producto/expiring")
    suspend fun getExpiringProduct(): Response<ExpiringProduct>

}