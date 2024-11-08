package com.example.logistics.ui.product

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.logistics.R
import com.example.logistics.model.Lote
import com.example.logistics.model.LoteRequest
import com.example.logistics.model.Product
import com.example.logistics.model.ProductRequest

@Composable
fun LoteForm(
    lote: Lote,
    onLoteChange: (Lote) -> Unit
) {
    var codigoLote by remember { mutableStateOf(lote.codigoLote) }
    var estadoOperativo by remember { mutableStateOf(lote.estadoOperativo) }
    var estadoDisponibilidad by remember { mutableStateOf(lote.estadoDisponibilidad) }
    var estadoSeguridad by remember { mutableStateOf(lote.estadoSeguridad) }
    var nombreProducto by remember { mutableStateOf(lote.nombreProducto) }
    var stock by remember { mutableStateOf(lote.stock) }
    var fechaVencimiento by remember { mutableStateOf(lote.fechaVencimiento) }

    val opcionesEstadoOperativo = listOf("Completo", "Incompleto")
    val opcionesEstadoDisponibilidad = listOf("Disponible", "Agotado", "Reservado", "Vendido")
    val opcionesEstadoSeguridad = listOf("Vigente", "Caducado")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        TextField(
            value = codigoLote,
            onValueChange = {
                codigoLote = it
                onLoteChange(lote.copy(codigoLote = it))
            },
            label = { Text("Código Lote") }
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Dropdown para Estado Operativo
        DropdownField(
            label = "Estado Operativo",
            options = opcionesEstadoOperativo,
            selectedOption = estadoOperativo,
            onOptionSelected = { selected ->
                estadoOperativo = selected
                onLoteChange(lote.copy(estadoOperativo = selected))
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Dropdown para Estado Disponibilidad
        DropdownField(
            label = "Estado Disponibilidad",
            options = opcionesEstadoDisponibilidad,
            selectedOption = estadoDisponibilidad,
            onOptionSelected = { selected ->
                estadoDisponibilidad = selected
                onLoteChange(lote.copy(estadoDisponibilidad = selected))
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Dropdown para Estado Seguridad
        DropdownField(
            label = "Estado Seguridad",
            options = opcionesEstadoSeguridad,
            selectedOption = estadoSeguridad,
            onOptionSelected = { selected ->
                estadoSeguridad = selected
                onLoteChange(lote.copy(estadoSeguridad = selected))
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = nombreProducto,
            onValueChange = {
                nombreProducto = it
                onLoteChange(lote.copy(nombreProducto = it))
            },
            label = { Text("Nombre del Producto") },
            readOnly = true
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = stock,
            onValueChange = {
                stock = it
                onLoteChange(lote.copy(stock = it))
            },
            label = { Text("Stock") },
            readOnly = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = fechaVencimiento,
            onValueChange = {
                fechaVencimiento = it
                onLoteChange(lote.copy(fechaVencimiento = it))
            },
            label = { Text("Fecha de Vencimiento") }
        )

    }
}

// Reusable DropdownField Composable
@Composable
fun DropdownField(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = { },
            label = { Text(label) },
            readOnly = true,
            trailingIcon = {
                Icon(Icons.Default.ArrowDropDown, contentDescription = null,
                    Modifier.clickable { expanded = !expanded })
            },
            modifier = Modifier.fillMaxWidth()
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
fun LoteScreen(
    navController: NavController,
    viewModel: ProductViewModel = ProductViewModel(),
) {
    val producto = viewModel.selectedProduct
    val lotes = viewModel.lotes.value // Lotes generados

    // val lotesState = remember { mutableStateListOf(*lotes.toTypedArray()) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Producto: ${producto?.nombreProducto}", style = MaterialTheme.typography.headlineMedium)
        Text("Stock total: ${producto?.cantidad}", style = MaterialTheme.typography.headlineSmall)

        LazyColumn {
            itemsIndexed(lotes ?: emptyList()) { index, lote ->
                LoteForm(
                    lote = lote,
                    onLoteChange = { updatedLote ->
                        //lotesState[index] = updatedLote
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Button(onClick = { navController.popBackStack() }) {
                Text(text = stringResource(R.string.cancel_button_desc))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = {
                if (producto != null && lotes != null) {
                    guardar(producto, lotes, viewModel)
                    navController.popBackStack()
                    navController.popBackStack()
                }
            }) {
                Text("Guardar")
            }
        }
    }
}


fun guardar(producto: Product, lotes: List<Lote>, viewModel: ProductViewModel) {
    val productoRequest = ProductRequest(
        code = producto.codigo,
        category = producto.categoria,
        type = producto.tipo,
        name = producto.nombreProducto,
        price = producto.precio.toDouble(),
        concentracion = producto.concentracion,
        presentation = producto.presentacion,
        description = producto.descripcion,
        stock = producto.cantidad.toInt(),
        lots = lotes.map {
            LoteRequest(
                code = it.codigoLote,
                operativeStatus = it.estadoOperativo,
                disponibilityState = it.estadoDisponibilidad,
                securityState = it.estadoSeguridad,
                stock = it.stock.toInt(),
                expiredDate = it.fechaVencimiento
            )
        }
    )
    viewModel.guardarProductoConLotes(productoRequest)
}

