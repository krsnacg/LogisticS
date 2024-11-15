package com.example.logistics.objects

import com.example.logistics.service.EmpleadoService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object EmpleadoClient {

    private const val BASE_URL = "http://10.0.2.2:9000/"


    val empleadoService: EmpleadoService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EmpleadoService::class.java)
    }
}