package com.example.logistics.ui.product

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.logistics.R
import com.example.logistics.model.Product

@Composable
fun ProductForm(
    // code: String,
    product: Product,
    categoryList: List<String>,
    formList: List<String>,
    isEditable: Boolean = false,
    isFormValid: Boolean,
    isProductUpdatable: Boolean,
    onNameChange: (String) -> Unit,
    onCategoryChange: (String) -> Unit,
    onTypeChange: (String) -> Unit,
    onPriceChange: (String) -> Unit,
    onConcentrationChange: (String) -> Unit,
    onPresentationChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onQuantityChange: (String) -> Unit,
    onUpdateClick: () -> Unit,
    onSaveClick: () -> Unit,
) {

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
                    enabled = false,
                    readOnly = !isEditable,
                    label = { Text(text = "Código") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = product.nombreProducto,
                    onValueChange = onNameChange,
                    enabled = isEditable,
                    readOnly = !isEditable,
                    label = {
                        Text(
                            text = "Nombre del Producto",
                        )
                    },
                )
                Spacer(modifier = Modifier.height(8.dp))
                DropdownField(
                    label = "Categoria",
                    options = categoryList,
                    selectedOption = product.categoria,
                    onOptionSelected = onCategoryChange,
                    enabled = isEditable
                )
                Spacer(modifier = Modifier.height(8.dp))
                DropdownField(
                    label = "Tipo",
                    options = formList,
                    selectedOption = product.tipo,
                    onOptionSelected = onTypeChange,
                    enabled = isEditable
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = product.precio,
                    onValueChange = onPriceChange,
                    enabled = isEditable,
                    readOnly = !isEditable,
                    label = { Text(text = "Precio") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = product.concentracion,
                    onValueChange = onConcentrationChange,
                    enabled = isEditable,
                    readOnly = !isEditable,
                    label = { Text(text = "Concentración") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = product.presentacion,
                    onValueChange = onPresentationChange,
                    label = { Text(text = "Presentación") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = product.descripcion,
                    onValueChange = onDescriptionChange,
                    label = { Text(text = "Descripción") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = product.cantidad,
                    onValueChange = onQuantityChange,
                    label = {
                        Text(
                            text = stringResource(R.string.quantity_desc),
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.height(16.dp))

            }

        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomEnd)
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Column {
                if (!isEditable) {
                    Button(
                        onClick = onUpdateClick,
                        enabled = isProductUpdatable
                    ) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = null)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Actualizar", fontSize = 14.sp)
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                }
                Button(
                    onClick = onSaveClick,
                    enabled = isFormValid
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = stringResource(R.string.generate_batches_button), fontSize = 14.sp)
                }
            }
        }
    }
}

@Composable
fun LoadingIndicator(isLoading: Boolean) {
    if (isLoading) {
        Log.d("LoadingIndicator", "Rendering LoadingIndicator")
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}
