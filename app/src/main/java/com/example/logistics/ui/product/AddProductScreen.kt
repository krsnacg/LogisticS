package com.example.logistics.ui.product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
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
import com.example.logistics.R
import com.example.logistics.model.Product
import com.example.logistics.model.ProductRequest

@Composable
fun AddProductScreen(navController: NavController, viewModel: ProductViewModel = ProductViewModel()) {
    var producto = remember { mutableStateOf(Product()) }

    ProductForm(
        product = producto.value,
        optionName = "Registrar",
        onProductChange = { updatedProduct -> producto.value = updatedProduct },
        onCancelClick = {
            navController.popBackStack()
        },
        onSaveClick = {
//            viewModel.addProduct(producto.value)
//            viewModel.onProductSelected(producto.value)
//            navController.navigate("lote")
            viewModel.generateLotes(producto.value.nombreProducto, producto.value.cantidad.toInt())
            viewModel.onProductSelected(producto.value)
            navController.navigate("lote")
        },
        buttonText = "Generar Lotes"
    )
}