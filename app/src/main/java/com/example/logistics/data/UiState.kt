package com.example.logistics.data

data class UiState(
    val token: String = "",
    val message: String = "",
    val isLoading: Boolean = false,
    val error: String = "",
    val empleado: EmpleadoDto? = null
)
