package com.example.logistics.data

import com.example.logistics.model.EmpleadoDto

data class UiState(
    val token: String = "",
    val message: String = "",
    val isLoading: Boolean = false,
    val error: String = "",
    val empleado: EmpleadoDto? = null
)