package com.example.logistics.ui.cliente

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.logistics.model.Cliente
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateClienteForm(clienteViewModel: ClienteViewModel, navClienteController: NavController) {

    val selectedCliente by clienteViewModel.selectedCliente.collectAsState()
    val departmentOptions = listOf("Lima", "Arequipa", "Cusco", "Otros")
    var isDropdownExpanded by remember { mutableStateOf(false) }

    val (empresa, setEmpresa) = remember { mutableStateOf(selectedCliente?.empresa ?: "") }
    val (representante, setRepresentante) = remember { mutableStateOf(selectedCliente?.representante ?: "") }
    val (dni, setDni) = remember { mutableStateOf(selectedCliente?.dni ?: "") }
    val (email, setEmail) = remember { mutableStateOf(selectedCliente?.email ?: "") }
    val (telefono, setTelefono) = remember { mutableStateOf(selectedCliente?.telefono ?: "") }
    val (direccion, setDireccion) = remember { mutableStateOf(selectedCliente?.direccion ?: "") }
    var selectedDepartment by remember { mutableStateOf(selectedCliente?.nombre_departamento ?: "Lima") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Título
        Text("Actualizar Cliente", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        // Campos del formulario
        OutlinedTextField(
            value = empresa,
            onValueChange = setEmpresa,
            label = { Text("Empresa") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = representante,
            onValueChange = setRepresentante,
            label = { Text("Representante") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = dni,
            onValueChange = setDni,
            label = { Text("DNI") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = email,
            onValueChange = setEmail,
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = telefono,
            onValueChange = setTelefono,
            label = { Text("Teléfono") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = direccion,
            onValueChange = setDireccion,
            label = { Text("Dirección") },
            modifier = Modifier.fillMaxWidth()
        )

        Text("Departamento", style = MaterialTheme.typography.labelMedium)
        Spacer(modifier = Modifier.height(8.dp))

        // Campo desplegable para Departamento
        ExposedDropdownMenuBox(
            expanded = isDropdownExpanded,
            onExpandedChange = { isDropdownExpanded = it }
        ) {
            OutlinedTextField(
                value = selectedDepartment,
                onValueChange = {},
                label = { Text("Departamento") },
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
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
                departmentOptions.forEach { department ->
                    DropdownMenuItem(
                        text = { Text(department) },
                        onClick = {
                            selectedDepartment = department
                            isDropdownExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botones Finales
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    // Limpiar los campos
                    setEmpresa("")
                    setRepresentante("")
                    setDni("")
                    setEmail("")
                    setTelefono("")
                    setDireccion("")
                    selectedDepartment = "Lima"
                    clienteViewModel.clearClienteSelected()
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Cancelar")
            }
            Button(
                onClick = {
                    val cliente = Cliente(
                        id_cliente = selectedCliente?.id_cliente ?: "",
                        empresa = empresa,
                        representante = representante,
                        dni = dni,
                        email = email,
                        telefono = telefono,
                        direccion = direccion,
                        nombre_departamento = selectedDepartment
                    )
                    clienteViewModel.updateCliente(cliente)

                    navClienteController.navigate("cliente/list") {
                        popUpTo("cliente/list") { inclusive = true }
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Guardar")
            }
        }
    }

}
