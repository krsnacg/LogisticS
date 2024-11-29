package com.example.logistics.ui.factura

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.logistics.ui.cliente.ClienteViewModel
import com.example.logistics.ui.cotizacion.CotizacionViewModel

@Composable
fun VistaFacturas(navController: NavController,
                  viewModel: FacturaViewModel){
    MaterialTheme {
        GestionFacturaScreen()
    }

}

@Composable
fun GestionFacturaScreen() {
    val estados = listOf("Pendiente", "Pagado", "Cancelado")
    var selectedFactura by remember { mutableStateOf<Factura?>(null) } // Factura seleccionada
    var filtroCodigo by remember { mutableStateOf("") }
    var filtroEstado by remember { mutableStateOf("") }

    // Lista de facturas (simulando datos iniciales)
    val facturas = listOf(
        Factura("FV-001", "PE-001", "Pagado", "04/05/2024", "02/05/2024"),
        Factura("FV-002", "PE-002", "Pendiente", "13/05/2024", "10/05/2024"),
        Factura("FV-003", "PE-003", "Cancelado", "15/05/2024", "10/05/2024"),
        Factura("FV-004", "PE-004", "Pagado", "23/07/2024", "17/07/2024")
    )

    // Aplicar filtros
    val facturasFiltradas = facturas.filter {
        (filtroCodigo.isBlank() || it.pedido.contains(filtroCodigo, ignoreCase = true)) &&
                (filtroEstado.isBlank() || it.estadoPago == filtroEstado)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Gestión de Factura", fontSize = 24.sp, modifier = Modifier.padding(bottom = 16.dp))

        // Sección de Generar Factura
        Card(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                OutlinedTextField(
                    value = selectedFactura?.codigo ?: "",
                    onValueChange = {},
                    label = { Text("Código Factura") },
                    readOnly = true
                )
                OutlinedTextField(
                    value = selectedFactura?.pedido ?: "",
                    onValueChange = {},
                    label = { Text("Código Pedido") },
                    readOnly = true
                )
                OutlinedTextField(
                    value = selectedFactura?.fechaLimite ?: "",
                    onValueChange = {},
                    label = { Text("Fecha Límite") },
                    readOnly = true
                )
                DropdownMenuEstado(
                    selectedEstado = selectedFactura?.estadoPago ?: "",
                    estados = estados
                ) { estado ->
                    selectedFactura = selectedFactura?.copy(estadoPago = estado)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(onClick = { /* Guardar lógica */ }) {
                        Text("Guardar todo")
                    }
                    Button(
                        onClick = { /* Cancelar lógica */ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Text("Cancelar")
                    }
                }
            }
        }

        // Campos de búsqueda
        Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
            OutlinedTextField(
                value = filtroCodigo,
                onValueChange = { filtroCodigo = it },
                label = { Text("Buscar por Código de Pedido") },
                modifier = Modifier.weight(1f).padding(end = 8.dp)
            )
            DropdownMenuEstado(
                selectedEstado = filtroEstado,
                estados = estados
            ) { estado ->
                filtroEstado = estado
            }
        }

        // Tabla de Facturas
        Text("Facturas de Venta", fontSize = 20.sp, modifier = Modifier.padding(bottom = 8.dp))
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(facturasFiltradas) { factura ->
                FacturaRow(factura) {
                    selectedFactura = factura
                }
            }
        }
    }
}

@Composable
fun DropdownMenuEstado(
    selectedEstado: String,
    estados: List<String>,
    onEstadoSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
        OutlinedTextField(
            value = selectedEstado,
            onValueChange = {},
            label = { Text("Estado Pago") },
            modifier = Modifier.width(150.dp),
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { expanded = true }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Expandir menú"
                    )
                }
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(150.dp)
        ) {
            estados.forEach { estado ->
                DropdownMenuItem(
                    onClick = {
                        onEstadoSelected(estado)
                        expanded = false
                    },
                    text = {
                        Text(estado)
                    }
                )
            }
        }
    }
}

@Composable
fun FacturaRow(factura: Factura, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(factura.codigo)
        Text(factura.pedido)
        Text(factura.estadoPago)
        Text(factura.fechaLimite)
        Text(factura.fechaPago)
    }
}

data class Factura(
    val codigo: String,
    val pedido: String,
    val estadoPago: String,
    val fechaLimite: String,
    val fechaPago: String
)
