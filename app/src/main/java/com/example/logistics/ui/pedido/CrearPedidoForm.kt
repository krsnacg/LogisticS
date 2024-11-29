package com.example.logistics.ui.pedido

import android.app.DatePickerDialog
import android.util.Log
import android.widget.DatePicker
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.logistics.model.EstadoEnvio
import com.example.logistics.model.MetodoEnvio
import com.example.logistics.model.Pedido
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearPedidoForm(viewModel: PedidoViewModel, navController: NavController) {

    var idPedido by remember { mutableStateOf("") }
    var idCotizacion by remember { mutableStateOf("") }
    var idCliente by remember { mutableStateOf("") }
    var metodoPagoSeleccionado by remember { mutableStateOf<MetodoEnvio?>(null) }
    var estadoEnvioSeleccionado by remember { mutableStateOf<EstadoEnvio?>(null) }
    var fechaEntrega by remember { mutableStateOf<Date?>(null) }
    val fechaEmision = remember { Date() } // Fecha actual para fechaEmision

    // Opciones de métodos de pago
    val metodosPago = listOf(
        MetodoEnvio(1, "Efectivo"),
        MetodoEnvio(2, "BBVA"),
        MetodoEnvio(3, "ScotiaBank"),
        MetodoEnvio(4, "BCP"),
        MetodoEnvio(5, "PayPal")
    )

    // Opciones de estados de envío
    val estadosEnvio = listOf(
        EstadoEnvio(1, "En preparación"),
        EstadoEnvio(2, "En transito"),
        EstadoEnvio(3, "Entregado"),
        EstadoEnvio(4, "Listo para enviar")
    )

    // Estados para Dropdowns
    var showMetodoPagoDropdown by remember { mutableStateOf(false) }
    var showEstadoEnvioDropdown by remember { mutableStateOf(false) }

    // Formulario
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Campos de texto
        OutlinedTextField(
            value = idPedido,
            onValueChange = { idPedido = it },
            label = { Text("ID Pedido") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = idCotizacion,
            onValueChange = { idCotizacion = it },
            label = { Text("ID Cotización") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = idCliente,
            onValueChange = { idCliente = it },
            label = { Text("ID Cliente") },
            modifier = Modifier.fillMaxWidth()
        )

        // Dropdown Método de Pago
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = metodoPagoSeleccionado?.metodo ?: "",
                onValueChange = {},
                label = { Text("Método de Pago") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                trailingIcon = {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null, Modifier.clickable { showMetodoPagoDropdown = true })
                }
            )
            DropdownMenu(
                expanded = showMetodoPagoDropdown,
                onDismissRequest = { showMetodoPagoDropdown = false }
            ) {
                metodosPago.forEach { metodo ->
                    DropdownMenuItem(
                        text = {  Text(metodo.metodo)},
                        onClick = {
                        metodoPagoSeleccionado = metodo
                        showMetodoPagoDropdown = false
                    })


                }
            }
        }

        // Dropdown Estado de Envío
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = estadoEnvioSeleccionado?.estado ?: "",
                onValueChange = {},
                label = { Text("Estado de Envío") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                trailingIcon = {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null, Modifier.clickable { showEstadoEnvioDropdown = true })
                }
            )
            DropdownMenu(
                expanded = showEstadoEnvioDropdown,
                onDismissRequest = { showEstadoEnvioDropdown = false }
            ) {
                estadosEnvio.forEach { estado ->
                    DropdownMenuItem(
                        text = {Text(estado.estado)},
                        onClick = {
                        estadoEnvioSeleccionado = estado
                        showEstadoEnvioDropdown = false
                    })
                }
            }
        }

        DatePickerField(
            selectedDate = fechaEntrega,
            onDateSelected = { fechaEntrega = it }
        )

        // Botones Guardar y Cancelar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = {

                val pedido = Pedido(

                    idpedido = idPedido,
                    idcotizacion = idCotizacion,
                    idcliente = idCliente,
                    idmetodopago = metodoPagoSeleccionado?.idmetodo,
                    idestadoenvio = estadoEnvioSeleccionado?.idestado,
                    idempleado = "EMP001",
                    fechaentrega = fechaEntrega,
                    fechaemision = fechaEmision,
                )
                // Mostrar log con los datos
                Log.d("Pedido", pedido.toString())

            }) {
                Text("Guardar")
            }

            Button(onClick = {
                // Limpiar todos los campos
                idPedido = ""
                idCotizacion = ""
                idCliente = ""
                metodoPagoSeleccionado = null
                estadoEnvioSeleccionado = null
                fechaEntrega = null
            }) {
                Text("Cancelar")
            }
        }
    }
}

// Composable personalizado para DropdownField
@Composable
fun DatePickerField(
    selectedDate: Date?,
    onDateSelected: (Date) -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    Button(onClick = {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                onDateSelected(calendar.time)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }) {
        Text(selectedDate?.toString() ?: "Seleccionar Fecha de Entrega")
    }
}