package com.example.logistics.ui.cotizacion

import android.util.Log
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.logistics.model.Cliente
import com.example.logistics.model.Cotizacion
import com.example.logistics.ui.cliente.ClienteViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun CotizacionScreen(
    navController: NavController,
   viewModel: CotizacionViewModel,
    navHostController: NavHostController,
    clienteViewModel: ClienteViewModel// Asume que usas Hilt para la inyección de dependencias
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

                    },
                    navHostController = navHostController,
                    clienteViewModel = clienteViewModel
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
fun CotizacionItem(
    viewModel: CotizacionViewModel,
    cotizacion: Cotizacion,
    onActionClick: (Cotizacion) -> Unit,
    navHostController: NavController,
    clienteViewModel: ClienteViewModel
) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primaryContainer,
                        MaterialTheme.colorScheme.surface
                    )
                )
            )
            .shadow(8.dp, RoundedCornerShape(16.dp)),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Cliente",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            MaterialTheme.colorScheme.primaryContainer,
                            CircleShape
                        )
                        .padding(8.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = cotizacion.nombrecliente,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = cotizacion.email,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Estado y monto
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatusChip(status = cotizacion.estado)
                Text(
                    text = "S/${cotizacion.montototal}",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Fecha
            Text(
                text = "Fecha: ${formatFecha(cotizacion.fechaemision)}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Acciones
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ActionButton(
                    text = "Editar",
                    icon = Icons.Default.Edit,
                    onClick = { onActionClick(cotizacion) }
                )

                ActionButton(
                    text = "PDF",
                    icon = Icons.Default.PictureAsPdf,
                    onClick = { viewModel.downloadAndSharePdf(context, cotizacion.idcotizacion) }
                )

                ActionButton(
                    text = "Pedido",
                    icon = Icons.Default.ShoppingCart,
                    onClick = {
                        val cliente = Cliente(
                            id_cliente = "",
                            empresa = "",
                            representante = cotizacion.nombrecliente,
                            dni = cotizacion.dni,
                            email = cotizacion.email,
                            telefono = "",
                            direccion = "",
                            nombre_departamento = cotizacion.departamento
                        )

                        clienteViewModel.verificarCliente(cliente) { resultado ->
                            if (resultado) {
                                Log.d("VERIFICAR_CLIENTE", "Cliente encontrado")
                            } else {
                                Log.d("NO_VERIFICAR_CLIENTE", "Cliente NO encontrado")
                                clienteViewModel.setPosibleCliente(cliente)
                                navHostController.navigate("clientes")
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun StatusChip(status: String) {
    val backgroundColor = when (status) {
        "Pendiente" -> MaterialTheme.colorScheme.tertiaryContainer
        "Completado" -> MaterialTheme.colorScheme.secondaryContainer
        "Cancelado" -> MaterialTheme.colorScheme.errorContainer
        else -> MaterialTheme.colorScheme.surface
    }

    val textColor = MaterialTheme.colorScheme.onSurface

    Box(
        modifier = Modifier
            .background(backgroundColor, RoundedCornerShape(16.dp))
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Text(
            text = status.uppercase(),
            style = MaterialTheme.typography.bodySmall,
            color = textColor
        )
    }
}


@Composable
fun ActionButton(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.primary,
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Icon(imageVector = icon, contentDescription = null, modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text)
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