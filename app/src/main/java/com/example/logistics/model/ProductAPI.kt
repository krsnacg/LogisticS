package com.example.logistics.model

import com.example.logistics.service.ProductApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ProductAPI {
    private const val BASE_URL = "http://localhost:9000/api/almacen/producto/"
    val api: ProductApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductApiService::class.java)
    }
}