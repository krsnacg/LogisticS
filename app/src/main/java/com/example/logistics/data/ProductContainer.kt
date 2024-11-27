package com.example.logistics.data

import com.example.logistics.service.ApiConfig
import com.example.logistics.service.ProductApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface ProductContainer {
    val productRepository: ProductRepository
}

class DefaultProductContainer: ProductContainer {
    private val BASE_URL = "${ApiConfig.BASE_URL}:9000/api/almacen/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Crear el cliente OkHttp
    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val gson: Gson = GsonBuilder().setLenient().create()

    private val retrofit: Retrofit = Retrofit.Builder()
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl(BASE_URL)
        .build()

    private val retrofitService: ProductApiService by lazy {
        retrofit.create(ProductApiService::class.java)
    }

    override val productRepository: ProductRepository by lazy {
        NetworkProductRepository(retrofitService)
    }
}