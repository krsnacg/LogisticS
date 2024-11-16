package com.example.logistics.service

import com.example.logistics.model.EmpleadoDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface EmpleadoService {

    @GET("api/rrhh/empleados/data/{email}")
    suspend fun getEmpleado(@Path("email") email: String): Response<EmpleadoDto>
}