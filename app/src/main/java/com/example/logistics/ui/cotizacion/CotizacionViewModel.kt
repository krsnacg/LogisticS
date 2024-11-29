package com.example.logistics.ui.cotizacion

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.logistics.model.Cotizacion
import com.example.logistics.model.CustomerMetric
import com.example.logistics.model.Product
import com.example.logistics.model.SalesMetric
import com.example.logistics.service.CotizacionService
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileOutputStream

class CotizacionViewModel(): ViewModel() {

    private val cotizacionService: CotizacionService

    private val _lastCode = MutableStateFlow<String?>(null)
    val lastCode: StateFlow<String?> get() = _lastCode

    private val _calculatedCotizacion = MutableStateFlow<Cotizacion?>(null)
    val calculatedCotizacion: StateFlow<Cotizacion?> get() = _calculatedCotizacion

    private val _cotizaciones = MutableStateFlow<List<Cotizacion>>(emptyList())
    val cotizaciones: StateFlow<List<Cotizacion>> get() = _cotizaciones

    private val _selectedCotizacion = MutableStateFlow<Cotizacion?>(null)
    val selectedCotizacion: StateFlow<Cotizacion?> get() = _selectedCotizacion

    private val _topCustomer = MutableStateFlow<List<CustomerMetric>>(emptyList())
    val topCustomers: StateFlow<List<CustomerMetric>> get() = _topCustomer

    private val _salesData = MutableStateFlow<List<SalesMetric>>(emptyList())
    val salesData: StateFlow<List<SalesMetric>> get() = _salesData

    init {
        val gson = GsonBuilder()
            .setLenient()  // Agrega esta línea para ser más tolerante con JSON malformado
            .create()
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:9000/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        cotizacionService = retrofit.create(CotizacionService::class.java)

        //getLastCotizacionCode()
        //fetchCotizaciones()
        getTopCustomers()
        getSalesData()
    }

    fun getSalesData() {
        viewModelScope.launch {
            try {
                _salesData.value = cotizacionService.getSalesData().body()!!
            }catch (e: Exception) {
                Log.e("TOP CUSTOMER ERROR", e.message.toString())
            }
        }
    }

    fun getTopCustomers() {
        viewModelScope.launch {
            try {
                _topCustomer.value = cotizacionService.getTopCustomers().body()!!
            }catch (e: Exception) {
                Log.e("TOP CUSTOMER ERROR", e.message.toString())
            }
        }
    }

    fun getLastCotizacionCode() {
        viewModelScope.launch {
            try {
                _lastCode.value = cotizacionService.getLastCotizacionCode().body()
            } catch (e: Exception) {
                Log.e("ERRORCODIGOCOTI", e.message.toString())
            }
        }
    }

    fun calculateCotizacion(cotizacion: Cotizacion) {
        viewModelScope.launch {
            try {
                val response = cotizacionService.calculateMontos(cotizacion)
                if (response.isSuccessful) {
                    _calculatedCotizacion.value = response.body()
                    Log.e("RESPUESTA DEL CALCULO", response.body().toString())
                } else {
                    Log.e("ERROR_CALCULAR", "Error al calcular montos: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("ERROR_CALCULAR", e.message.toString())
            }
        }
    }

    fun createCotizacion(cotizacion: Cotizacion) {
        viewModelScope.launch {
            try {
                cotizacionService.saveCotizacion(cotizacion)
            }catch (e: Exception) {
                Log.e("ERROR_GUARDAR_COTIZA", e.message.toString())
            }
        }
    }

    fun fetchCotizaciones() {
        viewModelScope.launch {
            try {
                val response = cotizacionService.getCotizaciones()
                if (response.isSuccessful) {
                    _cotizaciones.value = response.body() ?: emptyList()
                } else {
                    Log.e("ERROR_FETCH", "Error al obtener cotizaciones: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("ERROR_FETCH", e.message.toString())
            }
        }
    }

    fun updateCotizacion(cotizacion: Cotizacion) {
        viewModelScope.launch {
            try {
                val response = cotizacionService.getCotizaciones()
                if (response.isSuccessful) {
                    val nuevasCotizaciones = response.body() ?: emptyList()
                    _cotizaciones.value = emptyList() // Emite primero una lista vacía
                    _cotizaciones.value = nuevasCotizaciones // Luego, emite la nueva lista
                } else {
                    Log.e("ERROR_FETCH", "Error al obtener cotizaciones: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("ERROR_FETCH", e.message.toString())
            }
        }
    }

    fun setSelectedCotizacion(cotizacion: Cotizacion) {
        _selectedCotizacion.value = cotizacion
    }

    fun downloadAndSharePdf(context: Context, id: String) {
        viewModelScope.launch {
            try {
                val response = cotizacionService.generatePDF(id)
                if (response.isSuccessful) {
                    response.body()?.let { body ->
                        // Guarda el PDF en el almacenamiento del dispositivo
                        val fileName = "cotizacion_$id.pdf"
                        val file = File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "cotizacion_CV-011.pdf")
                        val uri = FileProvider.getUriForFile(
                            context,
                            "${context.packageName}.fileprovider",  // Autoridad definida en el Manifest
                            file
                        )


                        Log.d("PDF_DOWNLOAD", "PDF descargado en: ${file.absolutePath}")

                        // Llama a la función para compartir el PDF
                        sharePdf(context, file)
                    }
                } else {
                    Log.e("PDF_DOWNLOAD", "Error al descargar el PDF: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("PDF_DOWNLOAD", "Error: ${e.message}")
            }
        }
    }

    fun sharePdf(context: Context, file: File) {
        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",  // Debe coincidir con el Manifest
            file
        )

        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "application/pdf"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) // Permiso temporal para la app receptora
        }
        context.startActivity(Intent.createChooser(shareIntent, "Compartir PDF"))
    }


}