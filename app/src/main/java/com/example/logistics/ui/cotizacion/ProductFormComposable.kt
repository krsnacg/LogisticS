package com.example.logistics.ui.cotizacion

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.logistics.model.DetalleCotizacion
import com.example.logistics.ui.product.ProductViewModel

@Composable
fun ProductFormComposable(product: DetalleCotizacion, onRemove: () -> Unit, viewModel: ProductViewModel, idcotizacion: String?) {
    //val allProducts = listOf("Paracetamol", "Ibuprofeno", "Aspirina", "Amoxicilina", "Diclofenaco")
    val productList by viewModel.filteredProductList.collectAsState()
    var searchQuery by remember { mutableStateOf(product.producto ?: "") }
    var isDropdownExpanded by remember { mutableStateOf(false) }

    var cantidad by remember { mutableStateOf(product.cantidad.toString()) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        // Campo de búsqueda con sugerencias
        Box {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { query ->
                    searchQuery = query
                    viewModel.updateSearchQuery(query)
                    isDropdownExpanded = query.isNotEmpty()
                },
                label = { Text("Nombre del Producto") },
                modifier = Modifier.fillMaxWidth()
            )

            DropdownMenu(
                expanded = isDropdownExpanded,
                onDismissRequest = { isDropdownExpanded = false }
            ) {
                productList.forEach { suggestion ->
                    DropdownMenuItem(
                        text = { Text("${suggestion.nombreProducto} (${suggestion.concentracion})") },
                        onClick = {
                            product.producto = suggestion.nombreProducto
                            product.concentracion = suggestion.concentracion
                            if (idcotizacion != null) {
                                product.idcotizacion = idcotizacion
                            }
                            searchQuery = suggestion.nombreProducto
                            viewModel.updateSearchQuery(suggestion.nombreProducto)
                            isDropdownExpanded = false
                        }
                    )
                }
            }
        }

        // Campo de cantidad
        OutlinedTextField(
            value = cantidad,
            onValueChange = { newValue ->
                // Validar que solo se ingresen números enteros
                cantidad = newValue
                product.cantidad = newValue.toIntOrNull() ?: 0
            },
            label = { Text("Cantidad") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )

        // Campo de precio
        OutlinedTextField(
            value = product.total.toString(),
            onValueChange = { newValue ->
                // Asegurarse de que el precio sea flotante y válido
                product.total = newValue.toFloatOrNull() ?: 0f
            },
            label = { Text("Precio") },
            enabled = false,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal) // Números decimales
        )

        // Botón para eliminar
        Button(onClick = onRemove, modifier = Modifier.align(Alignment.End)) {
            Text("Eliminar Producto")
        }
    }
}