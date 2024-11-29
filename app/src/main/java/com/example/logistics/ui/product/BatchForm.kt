package com.example.logistics.ui.product

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.logistics.R
import com.example.logistics.model.Batch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@Composable
fun BatchForm(
    batch: Batch,
    isEditable: Boolean = false,
    onOperativeStateChange: (String) -> Unit, // TODO: Añadir parámetro tipo Int
    onAvailabilityStateChange: (String) -> Unit,
    onSecurityStateChange: (String) -> Unit,
    onDateChange: (String) -> Unit
) {

    val opcionesEstadoOperativo = listOf("Completo", "Incompleto")
    val opcionesEstadoDisponibilidad = listOf("Disponible", "Agotado", "Reservado", "Vendido")
    val opcionesEstadoSeguridad = listOf("Vigente", "Caducado")
    // var fechaVencimiento by remember { mutableStateOf(batch.fechaVencimiento) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = batch.codigoLote,
            onValueChange = {},
            enabled = false,
            readOnly = true,
//            textStyle = AppTypography.bodyMedium,
            label = { Text("Código Batch") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        DropdownField(
            label = "Estado Operativo",
            options = opcionesEstadoOperativo,
            selectedOption = batch.estadoOperativo,
            onOptionSelected = onOperativeStateChange,
            enabled = true
        )
        Spacer(modifier = Modifier.height(8.dp))
        DropdownField(
            label = "Estado Disponibilidad",
            options = opcionesEstadoDisponibilidad,
            selectedOption = batch.estadoDisponibilidad,
            onOptionSelected = onAvailabilityStateChange,
            enabled = true
        )
        Spacer(modifier = Modifier.height(8.dp))
        DropdownField(
            label = "Estado Seguridad",
            options = opcionesEstadoSeguridad,
            selectedOption = batch.estadoSeguridad,
            onOptionSelected = onSecurityStateChange,
            enabled = true
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = batch.nombreProducto,
            onValueChange = {},
            enabled = false,
            readOnly = true,
//            textStyle = AppTypography.bodyMedium,
            label = { Text("Nombre del Producto") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = batch.stock,
            onValueChange = {},
            enabled = false,
            readOnly = true,
//            textStyle = AppTypography.bodyMedium,
            label = { Text("Stock") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(8.dp))
        DatePickerField(
            selectedDate = batch.fechaVencimiento,
            isEnabled = isEditable,
            onDateSelected = onDateChange
        )
    }
}

@Composable
fun DropdownField(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit, // TODO: Añadir parámetro tipo Int
    enabled: Boolean
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        TextField(
            value = selectedOption,
            onValueChange = { },
            label = { Text(text = label) },
            enabled = enabled,
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


@Composable
fun AlertDialogExample(
    isDismissEnabled: Boolean = false,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = { Icon(icon, contentDescription = "Example Icon") },
        title = { Text(text = dialogTitle) },
        text = { Text(text = dialogText) },
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            TextButton(onClick = { onConfirmation() }) {
                Text("Confirm")
            }
        },
        dismissButton = {
            if (isDismissEnabled)
                TextButton(onClick = { onDismissRequest() }) {
                    Text("Cancel")
                }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerField(
    selectedDate: String,
    isEnabled: Boolean,
    onDateSelected: (String) -> Unit
) {
    var showDatePicker by remember { mutableStateOf(value = false) }
    val datePickerState = rememberDatePickerState()

    Box{
        OutlinedTextField(
            value = selectedDate,
            onValueChange = { },
            enabled = isEnabled,
            readOnly = true,
            label = { Text("Fecha de Vencimiento") },
            trailingIcon = {
                IconButton(onClick = { showDatePicker = true }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Select date"
                    )
                }
            },
            modifier = Modifier
                .height(64.dp)
        )
        if (showDatePicker) {
            DatePickerModal(
                datePickerState = datePickerState,
                onDateSelected = { formattedDate ->
                    onDateSelected(formattedDate)
                    showDatePicker = false
                },
                onDismiss = { showDatePicker = false }
            )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    datePickerState: DatePickerState,
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit,
) {
    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    datePickerState.selectedDateMillis?.let {
                        val formattedDate = convertMillisToDate(it)
                        onDateSelected(formattedDate)
                    }
                    onDismiss()
                }
            ) { Text(text = "OK") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(R.string.cancel_button_desc))
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    formatter.timeZone = TimeZone.getDefault()
    return formatter.format(Date(millis))
}