package com.example.logistics.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://127.0.0.1:3002/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}