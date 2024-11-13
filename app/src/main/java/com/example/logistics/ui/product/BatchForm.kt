package com.example.logistics.ui.product

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.logistics.model.Batch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun BatchForm(
    batch: Batch,
    onOperativeStateChange: (String) -> Unit, // TODO: Añadir parámetro tipo Int
    onAvailabilityStateChange: (String) -> Unit,
    onSecurityStateChange: (String) -> Unit,
    onDateChange: (String) -> Unit
) {

    val opcionesEstadoOperativo = listOf("Completo", "Incompleto")
    val opcionesEstadoDisponibilidad = listOf("Disponible", "Agotado", "Reservado", "Vendido")
    val opcionesEstadoSeguridad = listOf("Vigente", "Caducado")

    var fechaVencimiento by remember { mutableStateOf(batch.fechaVencimiento) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        TextField(
            value = batch.codigoLote,
            onValueChange = {},
            readOnly = true,
            label = { Text("Código Batch") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        DropdownField(
            label = "Estado Operativo",
            options = opcionesEstadoOperativo,
            selectedOption = batch.estadoOperativo,
            onOptionSelected = onOperativeStateChange
        )
        Spacer(modifier = Modifier.height(8.dp))
        DropdownField(
            label = "Estado Disponibilidad",
            options = opcionesEstadoDisponibilidad,
            selectedOption = batch.estadoDisponibilidad,
            onOptionSelected = onAvailabilityStateChange
        )
        Spacer(modifier = Modifier.height(8.dp))
        DropdownField(
            label = "Estado Seguridad",
            options = opcionesEstadoSeguridad,
            selectedOption = batch.estadoSeguridad,
            onOptionSelected = onSecurityStateChange
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = batch.nombreProducto,
            onValueChange = {},
            label = { Text("Nombre del Producto") },
            readOnly = true
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = batch.stock,
            onValueChange = {},
            label = { Text("Stock") },
            readOnly = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(8.dp))
        DatePickerDocked(
            selectedDate = batch.fechaVencimiento,
            onDateSelected = onDateChange
        )
    }
}

@Composable
fun DropdownField(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit // TODO: Añadir parámetro tipo Int
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = { },
            label = { Text(label) },
            readOnly = true,
            trailingIcon = {
                Icon(
                    Icons.Default.ArrowDropDown, contentDescription = null,
                    Modifier.clickable { expanded = !expanded })
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDocked(
    selectedDate: String,
    onDateSelected: (String) -> Unit
) {
    var showDatePicker by remember { mutableStateOf(value = false) }
    val datePickerState = rememberDatePickerState()

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = selectedDate,
            onValueChange = { },
            label = { Text("Fecha de Vencimiento") },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { showDatePicker = !showDatePicker }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Select date"
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
        )
    }

    if (showDatePicker) {
        Popup(
            onDismissRequest = { showDatePicker = false },
            alignment = Alignment.TopStart
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = 64.dp)
                    .shadow(elevation = 4.dp)
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp)
            ) {
                DatePicker(
                    state = datePickerState,
                    showModeToggle = false
                )
            }
        }
        datePickerState.selectedDateMillis?.let {
            val formattedDate = convertMillisToDate(it)
            onDateSelected(formattedDate)
        }
    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}