package com.example.logistics.service

import com.example.logistics.model.ProductRequest
import com.example.logistics.model.ProductResponse
import com.example.logistics.data.CategoryChart
import com.example.logistics.data.ExpiringProduct
import com.example.logistics.data.LowerStockProduct
import com.example.logistics.model.ReferralGuide
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductApiService {
    @GET("almacen/producto/listar")
    suspend fun getAllProducts(): Response<List<ProductResponse>>

    @GET("almacen/guias/listar")
    suspend fun getAllReferralGuides(): Response<List<ReferralGuide>>

    @POST("almacen/producto/guardar-p")
    suspend fun saveProductWithBatches(@Body product: ProductRequest): Response<ProductResponse>

    @POST("almacen/guias/save")
    suspend fun saveReferralGuide(@Body referralGuide: ReferralGuide): Response<ReferralGuide>

    @PUT("almacen/producto/actualizar-p")
    suspend fun updateProductWithBatches(@Body product: ProductRequest): Response<ProductRequest>

    @GET("almacen/producto/buscar-producto/{id}")
    suspend fun getProductWithBatches(@Path("id") id: String): Response<ProductRequest>

    @GET("almacen/producto/last-code")
    suspend fun getProductLastCode(): Response<String> // Response<Map<String,String>>

    @GET("almacen/lotes/last-code")
    suspend fun getBatchLastCode(): Response<String> // Response<Map<String,String>>

    @GET("almacen/guias/lastcode")
    suspend fun getReferralLastCode(): Response<String>

    @GET("almacen/formas/for-com")
    suspend fun getFormas(): Response<List<String>>

    @GET("almacen/categorias/cat-com")
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