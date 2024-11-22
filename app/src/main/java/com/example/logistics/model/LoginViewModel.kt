package com.example.logistics.model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.logistics.data.LoginRequest
import com.example.logistics.data.UiState
import com.example.logistics.data.repository.EmpleadoRepository

import com.example.logistics.service.ApiService
import com.example.logistics.service.EmpleadoService
import com.example.logistics.util.TokenUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginViewModel:  ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    private val apiService: ApiService
    private val empleadoService: EmpleadoService
    private val empleadoRepository: EmpleadoRepository

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8100/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        apiService = retrofit.create(ApiService::class.java)

        val retrofitv2 = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:9000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        empleadoService = retrofitv2.create(EmpleadoService::class.java)
        empleadoRepository = EmpleadoRepository(empleadoService)
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = "")
            try {
                val loginResponse = apiService.login(LoginRequest(email, password))
                if (loginResponse.isSuccessful && loginResponse.body() != null) {
                    val token = loginResponse.body()?.token ?: ""
                    _uiState.value = _uiState.value.copy(token = token)
                    fetchEmpleadoData(token)
                } else {
                    _uiState.value = _uiState.value.copy(
                        error = "Error de login: ${loginResponse.errorBody()?.string()}"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = "Error: ${e.message}")
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    private suspend fun fetchEmpleadoData(token: String) {
        val email = TokenUtils.extractEmailFromToken(token)
        if (email != null) {
            try {
                val response = empleadoRepository.fetchEmpleado(token, email)
                if (response.isSuccessful && response.body() != null) {
                    val empleado = response.body()!!
                    _uiState.value = _uiState.value.copy(error = "si obtuve datos: ${empleado.dni}",empleado = empleado)
                } else {
                    _uiState.value = _uiState.value.copy(
                        error = "Error al obtener datos del empleado: ${response.errorBody()?.string()}"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = "Error: ${e.message}")
            }
        } else {
            _uiState.value = _uiState.value.copy(error = "Error al extraer email del token")
        }
    }

    fun logout(navController: NavController) {
        viewModelScope.launch {
            // Limpiamos el estado
            _uiState.value = _uiState.value.copy(
                token = "",
                empleado = null,
                isLoading = false,
                error = ""
            )

            // Navegamos al login y limpiamos el back stack
            navController.navigate("login") {
                popUpTo(navController.graph.startDestinationId) { inclusive = true }
            }
        }
    }
}