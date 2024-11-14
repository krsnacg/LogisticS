package com.example.logistics.model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.logistics.data.LoginRequest
import com.example.logistics.data.UiState
import com.example.logistics.objects.ApiClient
import com.example.logistics.service.ApiService
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

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://d0dd-38-25-122-7.ngrok-free.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)
    }

    suspend fun login(email: String, password: String) {
        try {
            _uiState.value = _uiState.value.copy(isLoading = true, error = "")
            val loginResponse = apiService.login(LoginRequest(email, password))

            if (loginResponse.isSuccessful && loginResponse.body() != null) {
                val token = loginResponse.body()?.token ?: ""
                _uiState.value = _uiState.value.copy(token = token)
                fetchDemoMessage(token)
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

    private suspend fun fetchDemoMessage(token: String) {
        try {
            val bearerToken = "Bearer $token"
            Log.d("API_CALL", "Llamando a demo con token: $bearerToken")

            val demoResponse = apiService.getDemo(bearerToken)

            if (demoResponse.isSuccessful && demoResponse.body() != null) {
                val message = demoResponse.body()?.message ?: ""
                _uiState.value = _uiState.value.copy(message = message)
            } else {
                Log.e("API_ERROR", "Error demo: ${demoResponse.errorBody()?.string()}")
                _uiState.value = _uiState.value.copy(
                    error = "Error al obtener demo: ${demoResponse.errorBody()?.string()}"
                )
            }
        } catch (e: Exception) {
            Log.e("API_ERROR", "Exception en demo", e)
            _uiState.value = _uiState.value.copy(
                error = "Error al obtener mensaje demo: ${e.message}"
            )
        }
    }
}