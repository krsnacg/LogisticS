package com.example.logistics.ui.cotizacion

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.logistics.model.Cotizacion
import com.example.logistics.model.DetalleCotizacion
import com.example.logistics.ui.product.ProductViewModel
import com.google.gson.Gson
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuotationForm(cotizacionViewModel: CotizacionViewModel, productViewModel: ProductViewModel) {
    val context = LocalContext.current
    val calculatedCotizacion by cotizacionViewModel.calculatedCotizacion.collectAsState()

    val todayDate = remember { SimpleDateFormat("dd/MM/yyyy").format(Date()) }
    val (dni, setDni) = remember { mutableStateOf("") }
    val (clientName, setClientName) = remember { mutableStateOf("") }
    val (email, setEmail) = remember { mutableStateOf("") }
    val (department, setDepartment) = remember { mutableStateOf("") }
    val products = remember { mutableStateListOf<DetalleCotizacion>() }

    val defaultCode = cotizacionViewModel.lastCode.collectAsState().value
    val defaultState = "Pendiente"
    var montoProductos by remember { mutableStateOf(0.00) }
    var montoImpuestos by remember { mutableStateOf(0.00) }
    var montoTotal by remember { mutableStateOf(0.00) }

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(
                        "Formulario de Cotización",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary // Aquí puedes personalizar el color
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                // Sección de Información General
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 4.dp
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        defaultCode?.let {
                            OutlinedTextField(
                                value = it,
                                onValueChange = {},
                                label = { Text("Código") },
                                enabled = false,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        OutlinedTextField(
                            value = defaultState,
                            onValueChange = {},
                            label = { Text("Estado") },
                            enabled = false,
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = dni,
                            onValueChange = setDni,
                            label = { Text("DNI") },
                            modifier = Modifier.fillMaxWidth(),
                            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) }
                        )
                        OutlinedTextField(
                            value = clientName,
                            onValueChange = setClientName,
                            label = { Text("Nombre del Cliente") },
                            modifier = Modifier.fillMaxWidth(),
                            leadingIcon = { Icon(Icons.Default.PersonOutline, contentDescription = null) }
                        )
                        OutlinedTextField(
                            value = email,
                            onValueChange = setEmail,
                            label = { Text("Email") },
                            modifier = Modifier.fillMaxWidth(),
                            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) }
                        )
                        DropdownMenuComponent(
                            selectedDepartment = department,
                            onDepartmentSelected = setDepartment
                        )
                    }
                }
            }

            item {
                // Sección de Montos
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 4.dp
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        OutlinedTextField(
                            value = montoProductos.toString(),
                            onValueChange = {},
                            label = { Text("Monto Productos") },
                            enabled = false,
                            modifier = Modifier.fillMaxWidth(),
                            leadingIcon = { Icon(Icons.Default.ShoppingCart, contentDescription = null) }
                        )
                        OutlinedTextField(
                            value = montoImpuestos.toString(),
                            onValueChange = {},
                            label = { Text("Monto Impuestos") },
                            enabled = false,
                            modifier = Modifier.fillMaxWidth(),
                            leadingIcon = { Icon(Icons.Default.AttachMoney, contentDescription = null) }
                        )
                        OutlinedTextField(
                            value = montoTotal.toString(),
                            onValueChange = {},
                            label = { Text("Monto Total") },
                            enabled = false,
                            modifier = Modifier.fillMaxWidth(),
                            leadingIcon = { Icon(Icons.Default.Money, contentDescription = null) }
                        )
                        OutlinedTextField(
                            value = todayDate,
                            onValueChange = {},
                            label = { Text("Fecha de Emisión") },
                            enabled = false,
                            modifier = Modifier.fillMaxWidth(),
                            leadingIcon = { Icon(Icons.Default.CalendarToday, contentDescription = null) }
                        )
                    }
                }
            }

            item {
                // Sección de Productos
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 4.dp
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Productos", style = MaterialTheme.typography.titleMedium)
                        products.forEachIndexed { index, product ->
                            ProductFormComposable(
                                product = product,
                                onRemove = { products.removeAt(index) },
                                viewModel = productViewModel,
                                idcotizacion = defaultCode
                            )
                        }
                        Button(
                            onClick = { products.add(DetalleCotizacion()) },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Agregar Producto")
                        }
                    }
                }
            }

            item {
                // Botones Finales
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(onClick = { /* Reset logic */ }, modifier = Modifier.weight(1f)) {
                        Text("Cancelar")
                    }
                    Button(onClick = { /* Calculate logic */ }, modifier = Modifier.weight(1f)) {
                        Text("Calcular Montos")
                    }
                    Button(onClick = { /* Save logic */ }, modifier = Modifier.weight(1f)) {
                        Text("Guardar")
                    }
                }
            }
        }
    }
}


