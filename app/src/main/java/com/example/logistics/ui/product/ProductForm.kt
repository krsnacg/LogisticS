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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import com.example.logistics.ui.theme.AppTypography

@Composable
fun ProductForm(
    // code: String,
    product: Product,
    // optionName: String,
    isEditable: Boolean = false,
    isFormValid: Boolean,
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
) {
    var codigo by remember { mutableStateOf(product.codigo) }

    val categorias = listOf("Analgesico","Anestesico", "Ansiolitico")
    val tipos = listOf("Tableta","Solucion Oral", "Elixir","Jarabe")
    val stocks = listOf("200", "400", "600", "800", "1000")

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
//                    textStyle = AppTypography.bodyMedium,
                    onValueChange = {},
                    enabled = false,
                    readOnly = true,
                    label = { Text(text = "Código") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = product.nombreProducto,
                    onValueChange = onNameChange,
                    enabled = isEditable,
                    readOnly = !isEditable,
//                    textStyle = AppTypography.bodyMedium,
                    label = {
                        Text(
                            text = "Nombre del Producto",
//                            style = AppTypography.labelSmall
                        )
                    },
                )
                Spacer(modifier = Modifier.height(8.dp))
                DropdownField(
                    label = "Categoria",
                    options = categorias,
                    selectedOption = product.categoria,
                    onOptionSelected = onCategoryChange,
                    enabled = isEditable
                )
                Spacer(modifier = Modifier.height(8.dp))
                DropdownField(
                    label = "Tipo",
                    options = tipos,
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
//                    textStyle = AppTypography.bodyMedium,
                    label = { Text(text = "Precio") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = product.concentracion,
                    onValueChange = onConcentrationChange,
                    enabled = isEditable,
                    readOnly = !isEditable,
//                    textStyle = AppTypography.bodyMedium,
                    label = { Text(text = "Concentración") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = product.presentacion,
                    onValueChange = onPresentationChange,
//                    textStyle = AppTypography.bodyMedium,
                    label = { Text(text = "Presentación") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = product.descripcion,
                    onValueChange = onDescriptionChange,
//                    textStyle = AppTypography.bodyMedium,
                    label = { Text(text = "Descripción") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = product.cantidad,
                    onValueChange = onQuantityChange,
//                    textStyle = AppTypography.bodyMedium,
                    label = {
                        Text(
                            text = stringResource(R.string.quantity_desc),
//                            style = AppTypography.labelSmall
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
//            Button(onClick = onCancelClick) {
//                Text(text = stringResource(R.string.cancel_button_desc))
//            }
            Button(
                onClick = onSaveClick,
                enabled = isFormValid
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = stringResource(R.string.generate_batches_button))
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