package com.example.logistics.ui.factura

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.logistics.model.Cliente
import com.example.logistics.model.Factura
import com.example.logistics.service.ClientService
import com.example.logistics.service.FacturaService
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FacturaViewModel(): ViewModel() {
    private val facturaService: FacturaService

    private val _facturas = MutableStateFlow<List<Factura>>(emptyList())
    val facturas: StateFlow<List<Factura>> get() = _facturas


    private val _posibleFactura = MutableStateFlow<Factura?>(null)
    val posibleFactura: StateFlow<Factura?> get() = _posibleFactura

    private val _selectedFactura = MutableStateFlow<Factura?>(null)
    val selectedFactura: StateFlow<Factura?> get() = _selectedFactura


    init {
        val gson = GsonBuilder()
            .setLenient()  // Agrega esta línea para ser más tolerante con JSON malformado
            .create()
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:9000/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        facturaService = retrofit.create(FacturaService::class.java)
    }

    fun getFactura(){
        viewModelScope.launch {
            try {
                _facturas.value = facturaService.getFactura().body()!!
            }catch (e: Exception) {
                Log.e("ERROR_LISTA_FACTURA", e.message.toString())
            }

        }
    }

    fun createFactura(factura: Factura) {
        viewModelScope.launch {
            try {
                facturaService.createFactura(factura)
            } catch (e: Exception) {
                Log.e("ERROR_GUARDAR_FACTURA", e.message.toString())
            }
        }
    }

    fun updateFactura(factura: Factura) {
        viewModelScope.launch {
            try {
                facturaService.updateFactura(factura)
            } catch (e: Exception) {
                Log.e("ERROR_ACTUALIZAR_FACTURA", e.message.toString())
            }
        }
    }

    fun setPosibleCliente(factura: Factura) {
        _posibleFactura.value = factura
    }

    fun clearPosibleCliente() {
        _posibleFactura.value = null
    }

    fun selectedCliente(factura: Factura) {
        _selectedFactura.value = factura
    }

    fun clearFacturaSelected(){
        _posibleFactura.value = null
    }
}



