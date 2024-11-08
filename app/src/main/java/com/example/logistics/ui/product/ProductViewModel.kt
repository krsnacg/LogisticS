package com.example.logistics.ui.product

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.logistics.model.Lote
import com.example.logistics.model.LoteRequest
import com.example.logistics.model.Product
import com.example.logistics.model.ProductAPI
import com.example.logistics.model.ProductRequest
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {

    val productos = mutableStateListOf<Product>()
    var selectedProduct by mutableStateOf<Product?>(value = null)

    val products: MutableState<List<ProductRequest>> = mutableStateOf(emptyList())
    val lots: MutableState<List<Lote>> = mutableStateOf(emptyList())

    private val _productos = MutableLiveData<List<Product>>()
    val productoss: LiveData<List<Product>> get() = _productos

    private val _lotes = MutableLiveData<List<Lote>>()
    val lotes: LiveData<List<Lote>> get() = _lotes

    fun updateProduct(producto: Product) {
        viewModelScope.launch {
            // Implementa la lógica de actualizar el producto (solicitud PUT)
            // productos.addAll(/**/)
        }
    }

    fun onProductSelected(producto: Product) {
        selectedProduct = producto
    }

    fun listarProductos() {
        viewModelScope.launch {
            try {
                val response = ProductAPI.api.listarProductos()
                if (response.isSuccessful) {
                    _productos.value = response.body()
                } else {
                    Log.e("Error", "Error al listar productos: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("Exception", e.message.toString())
            }
        }
    }

    fun guardarProductoConLotes(productoRequest: ProductRequest) {
        viewModelScope.launch {
            try {
                val response = ProductAPI.api.createProductoWithLots(productoRequest)
                if (response.isSuccessful) {
                    Log.d("Success", "Producto guardado exitosamente")
                } else {
                    Log.e("Error", "Error al guardar producto: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("Exception", e.message.toString())
            }
        }
    }

    fun generateLotes(productName: String, productStock: Int) {
        val lotesCount = (productStock / 200) + if (productStock % 200 > 0) 1 else 0
        _lotes.value = List(lotesCount) { index ->
            Lote(
                codigoLote = "L00${(index + 968)}",
                nombreProducto = productName,
                stock = if (index == lotesCount - 1) (productStock % 200).toString() else "200"
            )
        }
    }

}