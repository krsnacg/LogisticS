package com.example.logistics.ui.product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.logistics.R
import com.example.logistics.model.Product

@Composable
fun ProductForm(
    product: Product,
    optionName: String,
    onProductChange: (Product) -> Unit,
    onCancelClick: () -> Unit,
    onSaveClick: () -> Unit,
    buttonText: String
) {
    var codigo by remember { mutableStateOf(product.codigo) }
    var nombreProducto by remember { mutableStateOf(product.nombreProducto) }
    var categoria by remember { mutableStateOf(product.categoria) }
    var tipo by remember { mutableStateOf(product.tipo) }
    var precio by remember { mutableStateOf(product.precio) }
    var concentracion by remember { mutableStateOf(product.concentracion) }
    var presentacion by remember { mutableStateOf(product.presentacion) }
    var descripcion by remember { mutableStateOf(product.descripcion) }
    var cantidad by remember { mutableStateOf(product.cantidad) }

    val categorias = listOf("Analgesico","Anestesico", "Ansiolitico")
    val tipos = listOf("Tableta","Solucion Oral", "Elixir","Jarabe")

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = optionName + " Producto",
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp
        )
        TextField(
            value = codigo,
            onValueChange = {
                codigo = it
                onProductChange(product.copy(codigo = it))
            },
            label = { Text("Código") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = nombreProducto,
            onValueChange = {
                nombreProducto = it
                onProductChange(product.copy(nombreProducto = it)) },
            label = { Text("Nombre del Producto") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        DropdownField(
            label = "Categoria",
            options = categorias,
            selectedOption = categoria,
            onOptionSelected = { selected ->
                categoria = selected
                onProductChange(product.copy(categoria = selected))
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        DropdownField(
            label = "Tipo",
            options = tipos,
            selectedOption = tipo,
            onOptionSelected = { selected ->
                tipo = selected
                onProductChange(product.copy(tipo = selected))
            },
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = precio,
            onValueChange = {
                precio = it
                onProductChange(product.copy(precio = it)) },
            label = { Text("Precio") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = concentracion,
            onValueChange = {
                concentracion = it
                onProductChange(product.copy(concentracion = it)) },
            label = { Text("Concentración") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = presentacion,
            onValueChange = {
                presentacion = it
                onProductChange(product.copy(presentacion = it)) },
            label = { Text("Presentación") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = descripcion,
            onValueChange = {
                descripcion = it
                onProductChange(product.copy(descripcion = it)) },
            label = { Text("Descripción") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = cantidad,
            onValueChange = {
                cantidad = it
                onProductChange(product.copy(cantidad = it)) },
            label = { Text(stringResource(R.string.quantity_desc)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Button(onClick = onCancelClick) {
                Text(text = stringResource(R.string.cancel_button_desc))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = onSaveClick) {
                Text(buttonText)
            }
        }
    }
}
