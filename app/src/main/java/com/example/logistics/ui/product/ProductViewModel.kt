package com.example.logistics.ui.product

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.logistics.model.Product
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {

    val productos = mutableStateListOf<Product>()
    var selectedProduct by mutableStateOf<Product?>(value = null)

    fun addProduct(producto: Product) {
        viewModelScope.launch {
            // Implementa la lógica de agregar el producto (solicitud POST)
        }
    }

    fun updateProduct(producto: Product) {
        viewModelScope.launch {
            // Implementa la lógica de actualizar el producto (solicitud PUT)
            // productos.addAll(/**/)
        }
    }

    fun loadProduct(productoId: String): Product {
        // Carga y devuelve el producto desde el backend para su edición
        return Product() // Ejemplo de retorno, remplazar con la lógica real
    }

    fun onProductSelected(producto: Product) {
        selectedProduct = producto
    }
}