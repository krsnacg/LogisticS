package com.example.logistics.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ProductAPI {
    private const val BASE_URL = "http://localhost:9000/api/almacen/"
    val api: ProductApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductApiService::class.java)
    }
}