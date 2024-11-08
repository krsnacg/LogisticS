package com.example.logistics.ui.product

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun EditProductScreen(navController: NavController, viewModel: ProductViewModel = ProductViewModel()) {

//    var producto = remember { Product() }
//    ProductForm(
//        product = producto,
//        optionName = "Actualizar",
//        onProductChange = { updatedProduct -> producto = updatedProduct },
//        onCancelClick = {
//            navController.popBackStack()
//        },
//        onSaveClick = {
//            viewModel.addProducto(producto)
//            navController.navigate("lote")
//        },
//        buttonText = "Generar Lotes"
//    )
    val productos = viewModel.productos
    val selectedProduct = viewModel.selectedProduct

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Selector de producto
        var expanded by remember { mutableStateOf(false) }

        Box {
            TextField(
                value = selectedProduct?.nombreProducto ?: "Seleccione un producto",
                onValueChange = {},
                readOnly = true,
                label = { Text("Producto") },
                modifier = Modifier.fillMaxWidth()
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                productos.forEach { producto ->
                    DropdownMenuItem(
                        text =  { Text(text = producto.nombreProducto) },
                        onClick = {
                            viewModel.onProductSelected(producto)
                            expanded = false
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.fillMaxWidth().clickable { expanded = true })
        }

        Spacer(modifier = Modifier.height(16.dp))

        selectedProduct?.let { producto ->
            ProductForm(
                product = producto,
                optionName = "Actualizar",
                onProductChange = { updatedProduct -> viewModel.onProductSelected(updatedProduct) },
                onCancelClick = { navController.popBackStack() },
                onSaveClick = {
                    viewModel.updateProduct(producto)
                    navController.navigate(route = "lote")
                },
                buttonText = "Generar Lotes"
            )
        }
    }
}