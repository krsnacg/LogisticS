package com.example.logistics.service

import com.example.logistics.model.Cotizacion
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CotizacionService {

    @GET("/api/cotizacion/coti-v/last-code")
    suspend fun getLastCotizacionCode(): Response<String>

    @POST("/api/cotizacion/coti-v/calculate")
    suspend fun calculateMontos(@Body cotizacion: Cotizacion): Response<Cotizacion>

    @POST("/api/cotizacion/coti-v/create")
    suspend fun saveCotizacion(@Body cotizacion: Cotizacion): Response<Cotizacion>

    @GET("/api/cotizacion/coti-v/details-full")
    suspend fun getCotizaciones(): Response<List<Cotizacion>>

    @GET("/api/cotizacion/coti-v/get/{id}")
    suspend fun getCotizacionById(@Path("id") id: String): Response<Cotizacion>

    @PUT("/api/cotizacion/coti-v/update")
    suspend fun updateCotizacion(@Body cotizacion: Cotizacion): Response<Cotizacion>

    @GET("/api/cotizacion/coti-v/pdf/{id}")
    suspend fun generatePDF(@Path("id") id: String): Response<ResponseBody>
}