package com.example.logistics.ui.cotizacion

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
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
fun EditCotizacion(navController: NavController ,cotizacionViewModel: CotizacionViewModel, productViewModel: ProductViewModel,  cotizacionId: String? = null) {

    val calculatedCotizacion by cotizacionViewModel.calculatedCotizacion.collectAsState()
    val selectedCotizacion by cotizacionViewModel.selectedCotizacion.collectAsState()
    val estadoOptions = listOf("Pendiente", "Aceptado", "Rechazado")

    var selectedEstado by remember { mutableStateOf(selectedCotizacion?.estado ?: "Pendiente") }
    var isDropdownExpanded by remember { mutableStateOf(false) }

    val todayDate = remember { SimpleDateFormat("dd/MM/yyyy").format(Date()) }
    val (dni, setDni) = remember { mutableStateOf(selectedCotizacion?.dni ?: "") }
    val (clientName, setClientName) = remember { mutableStateOf(selectedCotizacion?.nombrecliente ?: "") }
    val (email, setEmail) = remember { mutableStateOf(selectedCotizacion?.email ?: "") }
    val (department, setDepartment) = remember { mutableStateOf(selectedCotizacion?.departamento ?: "") }
    val products = remember {
        mutableStateListOf<DetalleCotizacion>().apply {
            selectedCotizacion?.detalles?.let { addAll(it) }
        }
    }

    val defaultCode = selectedCotizacion?.idcotizacion ?: cotizacionViewModel.lastCode.collectAsState().value.orEmpty()
    val defaultState = selectedCotizacion?.estado ?: "Pendiente"
    var montoProductos by remember { mutableStateOf(selectedCotizacion?.montoproducto?.toDouble() ?: 0.00) }
    var montoImpuestos by remember { mutableStateOf(selectedCotizacion?.montoimpuesto?.toDouble() ?: 0.00) }
    var montoTotal by remember { mutableStateOf(selectedCotizacion?.montototal?.toDouble() ?: 0.00) }


    LaunchedEffect(calculatedCotizacion) {
        calculatedCotizacion?.let { cotizacion ->
            montoProductos = BigDecimal(cotizacion.montoproducto?.toDouble() ?: 0.0)
                .setScale(2, RoundingMode.HALF_UP)
                .toDouble()

            montoImpuestos = BigDecimal(cotizacion.montoimpuesto?.toDouble() ?: 0.0)
                .setScale(2, RoundingMode.HALF_UP)
                .toDouble()

            montoTotal = BigDecimal(cotizacion.montototal?.toDouble() ?: 0.0)
                .setScale(2, RoundingMode.HALF_UP)
                .toDouble()

            // Actualizar los productos si llegan
            products.clear()
            products.addAll(cotizacion.detalles ?: emptyList())
        }
    }


    LaunchedEffect(Unit) {
        if (cotizacionId != null) {
            val cotizacion = cotizacionViewModel.cotizaciones.value.find { it.idcotizacion == cotizacionId }
            if (cotizacion != null) {
                cotizacionViewModel.setSelectedCotizacion(cotizacion)
            }
        } else {
            cotizacionViewModel.getLastCotizacionCode()
        }
        productViewModel.getAllProducts()


    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Título
        Text("Formulario de Cotización", style = MaterialTheme.typography.labelSmall)

        Spacer(modifier = Modifier.height(16.dp))

        // Sección Principal
        if (defaultCode != null) {
            OutlinedTextField(
                value = defaultCode,
                onValueChange = {},
                label = { Text("Código") },
                enabled = false,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Text("Estado", style = MaterialTheme.typography.labelMedium)
        Spacer(modifier = Modifier.height(8.dp))

        // Campo desplegable para Estado
        ExposedDropdownMenuBox(
            expanded = isDropdownExpanded,
            onExpandedChange = { isDropdownExpanded = it }
        ) {
            OutlinedTextField(
                value = selectedEstado,
                onValueChange = {},
                label = { Text("Estado") },
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(), // Importante para posicionar el menú
                trailingIcon = {
                    Icon(
                        imageVector = if (isDropdownExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                        contentDescription = null,
                        modifier = Modifier.clickable { isDropdownExpanded = !isDropdownExpanded }
                    )
                }
            )
            ExposedDropdownMenu(
                expanded = isDropdownExpanded,
                onDismissRequest = { isDropdownExpanded = false }
            ) {
                estadoOptions.forEach { estado ->
                    DropdownMenuItem(
                        text = { Text(estado) },
                        onClick = {
                            selectedEstado = estado
                            isDropdownExpanded = false
                        }
                    )
                }
            }
        }


        OutlinedTextField(
            value = dni,
            onValueChange = setDni,
            label = { Text("DNI") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = clientName,
            onValueChange = setClientName,
            label = { Text("Nombre del Cliente") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = montoProductos.toString(),
            onValueChange = {},
            label = { Text("Monto Productos") },
            enabled = false,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = montoImpuestos.toString(),
            onValueChange = {},
            label = { Text("Monto Impuestos") },
            enabled = false,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = montoTotal.toString(),
            onValueChange = {},
            label = { Text("Monto Total") },
            enabled = false,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = todayDate,
            onValueChange = {},
            label = { Text("Fecha de Emisión") },
            enabled = false,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = email,
            onValueChange = setEmail,
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        DropdownMenuComponent(
            selectedDepartment = department,
            onDepartmentSelected = setDepartment
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Lista de Productos
        Text("Productos", style = MaterialTheme.typography.titleMedium)
        products.forEachIndexed { index, product ->
            ProductFormComposable(
                product = product,
                onRemove = { products.removeAt(index) },
                viewModel = productViewModel,
                idcotizacion = defaultCode
            )
        }

        // Botones para productos
        Button(onClick = { products.add(DetalleCotizacion()) }, modifier = Modifier.fillMaxWidth()) {
            Text("Agregar Producto")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botones Finales
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    // Limpiar los campos y productos
                    setDni("")
                    setClientName("")
                    setEmail("")
                    setDepartment("")
                    products.clear()
                    montoProductos = 0.00
                    montoImpuestos = 0.00
                    montoTotal = 0.00
                    cotizacionViewModel.clearCotizacionSelected()
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Cancelar")
            }
            Button(
                onClick = {
                    // Calcular montos
                    montoProductos = products.sumOf { it.cantidad * it.total.toDouble() }

                    montoImpuestos = montoProductos * 0.18
                    montoTotal = montoProductos + montoImpuestos
                    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
                    val formattedDate = dateFormat.format(Date()) // Convierte la fecha actual al formato requerido

                    val cotizacion = Cotizacion(
                        idcotizacion = defaultCode ?: "0",  // Asegúrate de tener un código válido
                        idempleado = "EMP001",  // Asume que el empleado es fijo o viene de algún lado
                        estado = selectedEstado,
                        nombrecliente = clientName,
                        montoproducto = montoProductos.toFloat(),
                        fechaemision = formattedDate,  // Aquí puedes poner la fecha actual o la que necesites
                        email = email,
                        montoimpuesto = montoImpuestos.toFloat(),
                        montototal = montoTotal.toFloat(),
                        departamento = department,
                        detalles = products,  // Lista de detalles de cotización
                        dni = dni
                    )

                    val gson = Gson()
                    val jsonCotizacion = gson.toJson(cotizacion)

                    Log.d("Cotizacion JSON CREADAAA", cotizacion.toString())
                    cotizacionViewModel.calculateCotizacion(cotizacion)

                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Calcular Montos")
            }
            Button(onClick = {
                val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
                val formattedDate = dateFormat.format(Date())
                val cotizacion = Cotizacion(
                    idcotizacion = defaultCode ?: "0",  // Asegúrate de tener un código válido
                    idempleado = "EMP001",  // Asume que el empleado es fijo o viene de algún lado
                    estado = selectedEstado,
                    nombrecliente = clientName,
                    montoproducto = montoProductos.toFloat(),
                    fechaemision = formattedDate,  // Aquí puedes poner la fecha actual o la que necesites
                    email = email,
                    montoimpuesto = montoImpuestos.toFloat(),
                    montototal = montoTotal.toFloat(),
                    departamento = department,
                    detalles = products,  // Lista de detalles de cotización
                    dni = dni
                )
                val gson = Gson()
                val jsonCotizacion = gson.toJson(cotizacion)

                Log.d("Cotizacion JSON CREADAAA", jsonCotizacion)
                //cotizacionViewModel.createCotizacion(cotizacion)
                cotizacionViewModel.updateCotizacion(cotizacion)

                setDni("")
                setClientName("")
                setEmail("")
                setDepartment("")
                products.clear()
                montoProductos = 0.00
                montoImpuestos = 0.00
                montoTotal = 0.00

                navController.navigate("cotizacion/list") {
                    popUpTo("cotizacion/list") { inclusive = true } // Limpia el historial de navegación para evitar duplicados
                }


            }, modifier = Modifier.weight(1f)) {
                Text("Guardar")
            }
        }
    }
}