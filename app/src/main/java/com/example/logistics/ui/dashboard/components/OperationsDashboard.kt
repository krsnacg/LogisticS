package com.example.logistics.ui.dashboard.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun OperationsDashboard(
    modifier: Modifier = Modifier,
    onOperationClick: (String) -> Unit
) {
    val operations = listOf(
        "Registrar producto" to Icons.Default.Add,
        "Editar producto" to Icons.Default.Edit,
        "Gestionar lotes" to Icons.Default.Inventory,
        "Generar guía de remisión" to Icons.Default.Assignment
    )

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 160.dp),
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 200.dp, max = 400.dp),
           // .padding(10.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(operations) { (operation, icon) ->
            OperationCard(
                title = operation,
                icon = icon,
                onClick = { onOperationClick(operation) }
            )
        }
    }
}