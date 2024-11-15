package com.example.logistics.data.repository

import com.example.logistics.data.EmpleadoDto
import com.example.logistics.service.EmpleadoService
import retrofit2.Response

class EmpleadoRepository(private val empleadoService: EmpleadoService) {

    suspend fun fetchEmpleado(token: String, email: String): Response<EmpleadoDto> {
        return empleadoService.getEmpleado( email)
    }
}