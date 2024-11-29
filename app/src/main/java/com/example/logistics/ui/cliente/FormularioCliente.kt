package com.example.logistics.ui.cliente

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.logistics.model.Cliente
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioCliente(
    clienteViewModel: ClienteViewModel
) {
    val posibleCliente = clienteViewModel.posibleCliente.collectAsState().value
    val departamentos = listOf("TUMBES", "PIURA","LAMBAYEQUE","LA LIBERTAD","ANCASH","LIMA",
        "ICA","AREQUIPA","MOQUEGUA","TACNA","SAN MARTIN","CAJAMARCA",
        "AMAZONAS","LORETO","AYACUCHO","JUNIN","UCAYALI","HUANCAVELICA",
        "PUNO","PASCO","APURIMAC","MADRE DE DIOS","HUANUCO","CUZCO" )
    // Estados para manejar los campos del formulario
    val idCliente = remember { mutableStateOf(generateRandomCode()) }
    var empresa by remember { mutableStateOf("") }
    var representante by remember { mutableStateOf(posibleCliente?.representante ?: "") }
    var dni by remember { mutableStateOf(posibleCliente?.dni ?: "") }
    var email by remember { mutableStateOf(posibleCliente?.email ?: "") }
    var telefono by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var departamentoSeleccionado by remember { mutableStateOf(posibleCliente?.nombre_departamento ?: "") }
    var expandirDropdown by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0F0F0))
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Crear Cliente",
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
            color = Color(0xFF6200EA)
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Campo de texto solo lectura para el código de cliente
        OutlinedTextField(
            value = idCliente.value,
            onValueChange = {},
            label = { Text(text = "ID Cliente") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF6200EA),
                cursorColor = Color(0xFF6200EA)
            ),
            readOnly = true // Hace el campo de solo lectura
        )
        // Campos de texto
       // CampoTexto(label = "ID Cliente", valor = idCliente, onValueChange = { idCliente = it })
        CampoTexto(label = "Empresa", valor = empresa, onValueChange = { empresa = it })
        CampoTexto(label = "Representante", valor = representante, onValueChange = { representante = it })
        CampoTexto(label = "DNI", valor = dni, onValueChange = { dni = it })
        CampoTexto(label = "Email", valor = email, onValueChange = { email = it })
        CampoTexto(label = "Teléfono", valor = telefono, onValueChange = { telefono = it })
        CampoTexto(label = "Dirección", valor = direccion, onValueChange = { direccion = it })

        // Dropdown de departamentos
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(
                onClick = { expandirDropdown = !expandirDropdown },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = if (departamentoSeleccionado.isEmpty()) "Seleccionar Departamento" else departamentoSeleccionado)
            }
            DropdownMenu(
                expanded = expandirDropdown,
                onDismissRequest = { expandirDropdown = false }
            ) {
                departamentos.forEach { departamento ->
                    DropdownMenuItem(
                        text = {Text(text = departamento)},
                        onClick = {
                        departamentoSeleccionado = departamento
                        expandirDropdown = false
                    })
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))

        // Botones de acción
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            // Botón Cancelar
            Button(
                onClick = {
                    idCliente.value = generateRandomCode()
                    empresa = ""
                    representante = ""
                    dni = ""
                    email = ""
                    telefono = ""
                    direccion = ""
                    departamentoSeleccionado = ""
                    clienteViewModel.clearPosibleCliente()
                },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
            ) {
                Text(text = "Cancelar", color = Color.White)
            }

            // Botón Guardar
            Button(
                onClick = {
                    val cliente = Cliente(
                        id_cliente = idCliente.value,
                        empresa = empresa,
                        representante = representante,
                        dni = dni,
                        email = email,
                        telefono = telefono,
                        direccion = direccion,
                        nombre_departamento = departamentoSeleccionado
                    )
                    Log.d("FormularioCliente", cliente.toString())
                    clienteViewModel.createCliente(cliente)
                    //onGuardar(cliente)
                },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
            ) {
                Text(text = "Guardar", color = Color.White)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampoTexto(label: String, valor: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = valor,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(0xFF6200EA),
            cursorColor = Color(0xFF6200EA)
        )
    )
}

fun generateRandomCode(): String {
    val randomNumber = (0..99999).random() // Genera un número entre 0 y 99999
    return "C${randomNumber.toString().padStart(5, '0')}" // Formatea el número
}

