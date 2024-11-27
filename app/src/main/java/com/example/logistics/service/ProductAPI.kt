package com.example.logistics.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ProductAPI {
    val api: ProductApiService by lazy {
        Retrofit.Builder()
            .baseUrl(ApiConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductApiService::class.java)
    }
}