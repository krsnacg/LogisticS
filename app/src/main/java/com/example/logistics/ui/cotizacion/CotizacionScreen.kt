package com.example.logistics.ui.cotizacion

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.logistics.model.Cotizacion
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun CotizacionScreen(
    navController: NavController,
   viewModel: CotizacionViewModel// Asume que usas Hilt para la inyección de dependencias
) {
    val cotizaciones by viewModel.cotizaciones.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var filterEstado by remember { mutableStateOf("Todos") }

    val filteredCotizaciones = cotizaciones.filter { cotizacion ->
        (filterEstado == "Todos" || cotizacion.estado == filterEstado) &&
                (cotizacion.nombrecliente.contains(searchQuery, ignoreCase = true) ||
                        cotizacion.email.contains(searchQuery, ignoreCase = true))
    }

    LaunchedEffect(Unit) {
        viewModel.fetchCotizaciones()
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Buscar por cliente o email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        DropdownMenuFilter(
            selectedOption = filterEstado,
            options = listOf("Todos", "Pendiente", "Completado", "Cancelado"),
            onOptionSelected = { filterEstado = it }
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            items(filteredCotizaciones) { cotizacion -> // Cambia a filteredCotizaciones
                CotizacionItem(
                    viewModel = viewModel,
                    cotizacion = cotizacion,
                    onActionClick = { clickedCotizacion ->
                        viewModel.setSelectedCotizacion(clickedCotizacion)
                        Log.e("COTI SELECCIONADA", clickedCotizacion.toString())
                        navController.navigate("cotizacion/edit")

                    }
                )
            }
        }
    }
}

@Composable
fun DropdownMenuFilter(
    selectedOption: String,
    options: List<String>,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedButton(onClick = { expanded = true }) {
            Text(text = selectedOption)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {Text(option)},
                    onClick = {
                    onOptionSelected(option)
                    expanded = false
                })
            }
        }
    }
}

@Composable
fun CotizacionItem(viewModel: CotizacionViewModel,cotizacion: Cotizacion, onActionClick: (Cotizacion) -> Unit) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Información del cliente
            Text("Cliente: ${cotizacion.nombrecliente}", style = MaterialTheme.typography.labelSmall)
            Text("Email: ${cotizacion.email}", style = MaterialTheme.typography.labelMedium)
            Text("Estado: ${cotizacion.estado}", style = MaterialTheme.typography.labelMedium)
            Text("Monto Total: S/${cotizacion.montototal}", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
            Text("Fecha: ${formatFecha(cotizacion.fechaemision)}", style = MaterialTheme.typography.labelMedium)

            Spacer(modifier = Modifier.height(16.dp))

            // Botón alineado al final
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(onClick = { onActionClick(cotizacion) }) {
                    Text("Editar")
                }

                Button(onClick = {
                    viewModel.downloadAndSharePdf(context, cotizacion.idcotizacion)
                }) {
                    Text("pdf")
                }
            }
        }
    }
}

fun formatFecha(fecha: String): String {
    return try {
        val parser = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val formatter = SimpleDateFormat("dd 'de' MMMM 'de' yyyy", Locale.getDefault())
        val parsedDate = parser.parse(fecha)
        formatter.format(parsedDate ?: fecha)
    } catch (e: Exception) {
        fecha // Devuelve la fecha original si falla el formato
    }
}