package com.example.logistics.ui.cotizacion

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun DropdownMenuComponent(selectedDepartment: String, onDepartmentSelected: (String) -> Unit) {
    val departments = listOf("TUMBES", "PIURA","LAMBAYEQUE","LA LIBERTAD","ANCASH","LIMA",
        "ICA","AREQUIPA","MOQUEGUA","TACNA","SAN MARTIN","CAJAMARCA",
        "AMAZONAS","LORETO","AYACUCHO","JUNIN","UCAYALI","HUANCAVELICA",
        "PUNO","PASCO","APURIMAC","MADRE DE DIOS","HUANUCO","CUZCO" )
    var expanded by remember { mutableStateOf(false) }

    Box {
        OutlinedTextField(
            value = selectedDepartment,
            onValueChange = {},
            label = { Text("Departamento") },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { expanded = !expanded }
                )
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            departments.forEach { department ->
                DropdownMenuItem(
                    text = { Text(department) },
                    onClick = {
                        onDepartmentSelected(department)
                        expanded = false
                    }
                )
            }
        }
    }
}
