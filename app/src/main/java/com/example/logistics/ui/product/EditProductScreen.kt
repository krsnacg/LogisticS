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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun EditProductScreen(navController: NavController, viewModel: ProductViewModel ) {

    val productos = viewModel.productos
    val selectedProduct = viewModel.selectedProduct

    val codigoState by viewModel.codeState.collectAsState()

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
                code = codigoState,
                product = producto,
                optionName = "Actualizar",
                onNameChange = { viewModel.updateProductName(it) },
                onCategoryChange = { viewModel.updateProductCategory(it) },
                onTypeChange = { viewModel.updateProductType(it) },
                onPriceChange = { viewModel.updateProductPrice(it) },
                onConcentrationChange = { viewModel.updateProductConcentration(it) },
                onPresentationChange = { viewModel.updateProductPresentation(it) },
                onDescriptionChange = { viewModel.updateProductDescription(it) },
                onQuantityChange = { viewModel.updateProductQuantity(it) },
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