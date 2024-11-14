package com.example.logistics.service

import com.example.logistics.data.DemoResponse
import com.example.logistics.data.LoginRequest
import com.example.logistics.data.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST("/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("api/v1/demo")  // Cambiado a PO
    suspend fun getDemo(@Header("Authorization") bearerToken: String): Response<DemoResponse>
}