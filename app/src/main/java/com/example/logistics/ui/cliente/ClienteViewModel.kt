package com.example.logistics.ui.cliente

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.logistics.model.Cliente
import com.example.logistics.model.IdClienteResponse
import com.example.logistics.service.ClientService
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ClienteViewModel(): ViewModel() {

    private val clienteService: ClientService

    private val _clientes = MutableStateFlow<List<Cliente>>(emptyList())
    val clientes: StateFlow<List<Cliente>> get() = _clientes

    private val _posibleCliente = MutableStateFlow<Cliente?>(null)
    val posibleCliente: StateFlow<Cliente?> get() = _posibleCliente

    private val _cotizacionCliente = MutableStateFlow<IdClienteResponse?>(null)
    val cotizacionCliente: StateFlow<IdClienteResponse?> get() = _cotizacionCliente


    init {
        val gson = GsonBuilder()
            .setLenient()  // Agrega esta línea para ser más tolerante con JSON malformado
            .create()
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:9000/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        clienteService = retrofit.create(ClientService::class.java)
    }

    fun getClientes() {
        viewModelScope.launch {
            try {
                _clientes.value = clienteService.getClientes().body()!!
            }catch (e: Exception) {
                Log.e("ERROR_LISTA_CLIENTE", e.message.toString())
            }
        }
    }

    fun createCliente(cliente: Cliente) {
        viewModelScope.launch {
            try {
                clienteService.createCliente(cliente)
            }catch (e: Exception) {
                Log.e("ERROR_GUARDAR_CLIENT", e.message.toString())
            }
        }
    }

    fun verificarCliente(cliente: Cliente, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                _cotizacionCliente.value = clienteService.findCliente(cliente.dni).body()

                Log.e("CLIENTE_RECUPERADO", "para "+ cliente.dni +" -> " + cotizacionCliente.value)
                callback(cotizacionCliente.value != null)
            } catch (e: Exception) {
                Log.e("ERROR_VERIFICAR_CLIENTE", e.message.toString())
                callback(false)
            }
        }
    }


    fun setPosibleCliente(cliente: Cliente) {
        _posibleCliente.value = cliente
    }

    fun clearPosibleCliente() {
        _posibleCliente.value = null
    }
}