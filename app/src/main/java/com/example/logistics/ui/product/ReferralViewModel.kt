package com.example.logistics.ui.product

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.logistics.ProductApplication
import com.example.logistics.data.ProductRepository
import com.example.logistics.model.ReferralGuide
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY

class ReferralViewModel (private val productRepository: ProductRepository): ViewModel() {

    private val _saveState = MutableStateFlow<Result<String>?>(null)
    val saveState: StateFlow<Result<String>?> = _saveState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _referralList = MutableStateFlow<List<ReferralGuide>>(emptyList())
    val referralList = _referralList.asStateFlow()

    private val _pendingList = MutableStateFlow<List<ReferralGuide>>(emptyList())
    val pendingList = _pendingList.asStateFlow()

    var referralGuide by mutableStateOf(ReferralGuide())
        private set

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as ProductApplication)
                val productRepository = application.container.productRepository
                ReferralViewModel(productRepository)
            }
        }
    }

    fun getLastReferralCode() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
//                _codeState.value = incrementCode(productRepository.getLastCodeProducto())
//                Log.d("viewModelGetCodeSuccess", _codeState.value)
                referralGuide = referralGuide.copy(
                    idguiaremision = incrementCode(productRepository.getReferralLastCode())
                )
                Log.d("viewModelGetCodeSuccess", referralGuide.idguiaremision)
            } catch (e: IOException) {
                referralGuide = referralGuide.copy(idguiaremision = "ERROR")
                Log.d("viewModelGetCodeError", e.message.toString())
            } finally {
                _isLoading.value = false
            }

        }
    }

    fun getAllReferralGuides() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = productRepository.getAllReferralGuides()
                if (response.status == "success" && response.data?.isNotEmpty() == true) {
                    _referralList.value = response.data.sortedBy {
                        it.idguiaremision.filter { char -> char.isDigit() }.toInt()
                    }
                } else {
                    Log.e("Error", "Error obtaining referral guides: ${response.message}")
                }
            } catch (e: Exception) {
                Log.e("ExceptionGettingReferrals", e.message.toString())
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getAllPending() {

    }

    fun saveReferralGuide() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
//                val response: ApiResponse<String> = if (_editableState.value)
//                    productRepository.saveProductAndBatches(buildProductSaveRequest())
//                else
//                    productRepository.updateProductAndBatches(buildProductSaveRequest())
                val response = productRepository.saveReferralGuide(referralGuide)
                if (response.status == "success") {
                    _saveState.value = Result.success("Producto guardado exitosamente")
                    Log.d("Success", "Producto guardado exitosamente $response")
                } else {
                    _saveState.value = Result.failure(Exception("Error al guardar el producto"))
                    Log.e("Error", "Error al guardar producto: $response")
                }
            } catch (e: Exception) {
                _saveState.value = Result.failure(e)
                Log.e("ExceptionAtSavingProduct", e.message.toString())
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun isReferralGuideComplete(): Boolean {
        return referralGuide.idguiaremision.isNotBlank() &&
                referralGuide.idempleado.isNotBlank() &&
                referralGuide.idpedido.isNotBlank() &&
                referralGuide.fecha_envio.isNotBlank()
    }

    fun editPendingSelected(index: Int) {
        referralGuide = referralGuide.copy(idpedido = referralList.value[index].idpedido)
    }

    fun updateDate(date: String) {
        referralGuide = referralGuide.copy(fecha_envio = date)
    }

    private fun incrementCode(code: String): String {
        val regex = Regex(pattern = "([A-Z]+)(\\d+)")
        val matchResult = regex.matchEntire(code)
        if (matchResult != null) {
            val (prefix, numberStr) = matchResult.destructured
            val number = numberStr.toIntOrNull()

            if (number != null) {
                val newNumber = number + 1
                val newNumberStr = newNumber.toString().padStart(numberStr.length,'0')
                return "$prefix$newNumberStr"
            }
            else {
                Log.d("IncrementCode","No se pudo convertir la parte numérica del código: $code")
            }
        }
        throw IllegalArgumentException("El código no tiene el formato esperado: $code")
    }

}