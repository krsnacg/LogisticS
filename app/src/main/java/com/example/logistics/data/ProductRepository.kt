package com.example.logistics.data

import com.example.logistics.model.ApiResponse
import com.example.logistics.model.Product
import com.example.logistics.model.ProductRequest
import com.example.logistics.service.ProductApiService

interface ProductRepository {
    suspend fun getProductLastCode(): String
    suspend fun getBatchLastCode(): String
    suspend fun saveProductAndBatches(productRequest: ProductRequest): ApiResponse<String>
    suspend fun getAllProducts(): ApiResponse<List<Product>>
    suspend fun updateProductAndBatches(productRequest: ProductRequest): ApiResponse<String>
}

class NetworkProductRepository(
    private val productApiService: ProductApiService
): ProductRepository {
    override suspend fun getProductLastCode(): String = productApiService
        .getProductLastCode().body()?.get("code") ?: "ERROR"

    override suspend fun getBatchLastCode(): String = productApiService
        .getBatchLastCode().body()?.get("code") ?: "ERROR"

    override suspend fun saveProductAndBatches(productRequest: ProductRequest): ApiResponse<String> {
        val response = productApiService.saveProductWithBatches(productRequest)
        return if (response.isSuccessful) {
            val productCode = response.body()?.code ?: "ERROR"
            ApiResponse(status = "success", message = null, data = productCode)
        } else {
            ApiResponse(status = "error", message = response.message(), data = null)
        }
    }

    override suspend fun getAllProducts(): ApiResponse<List<Product>> {
        val response = productApiService.getAllProducts()
        return if (response.isSuccessful) {
            val productList = response.body()?.map { it.toProduct() }
            ApiResponse(status = "success", message = null, data = productList)
        } else {
            ApiResponse(status = "error", message = response.message(), data = null)
        }
    }

    override suspend fun updateProductAndBatches(productRequest: ProductRequest): ApiResponse<String> {
        val response = productApiService.updateProductWithBatches(productRequest)
        return if (response.isSuccessful) {
            val productCode = response.body()?.code ?: "ERROR"
            ApiResponse(status = "success", message = null, data = productCode)
        } else {
            ApiResponse(status = "error", message = response.message(), data = null)
        }
    }

}

