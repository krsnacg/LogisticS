package com.example.logistics.ui.product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
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
    code: String,
    product: Product,
    optionName: String,
    onNameChange: (String) -> Unit,
    onCategoryChange: (String) -> Unit,
    onTypeChange: (String) -> Unit,
    onPriceChange: (String) -> Unit,
    onConcentrationChange: (String) -> Unit,
    onPresentationChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onQuantityChange: (String) -> Unit,
    onCancelClick: () -> Unit,
    onSaveClick: () -> Unit,
    buttonText: String
) {
    var codigo by remember { mutableStateOf(product.codigo) }

    val categorias = listOf("Analgesico","Anestesico", "Ansiolitico")
    val tipos = listOf("Tableta","Solucion Oral", "Elixir","Jarabe")

    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = optionName + " Producto",
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = product.codigo,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Código") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = product.nombreProducto,
                    onValueChange = onNameChange,
                    label = { Text("Nombre del Producto") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                DropdownField(
                    label = "Categoria",
                    options = categorias,
                    selectedOption = product.categoria,
                    onOptionSelected = onCategoryChange
                )
                Spacer(modifier = Modifier.height(8.dp))
                DropdownField(
                    label = "Tipo",
                    options = tipos,
                    selectedOption = product.tipo,
                    onOptionSelected = onTypeChange
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = product.precio,
                    onValueChange = onPriceChange,
                    label = { Text("Precio") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = product.concentracion,
                    onValueChange = onConcentrationChange,
                    label = { Text("Concentración") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = product.presentacion,
                    onValueChange = onPresentationChange,
                    label = { Text("Presentación") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = product.descripcion,
                    onValueChange = onDescriptionChange,
                    label = { Text("Descripción") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = product.cantidad,
                    onValueChange = onQuantityChange,
                    label = { Text(stringResource(R.string.quantity_desc)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
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
    }

}
