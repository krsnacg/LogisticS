package com.example.logistics.ui.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.logistics.ui.product.ProductViewModel

@Composable
fun DashboardContent(
    modifier: Modifier = Modifier,
    navController: NavController,
    productViewModel: ProductViewModel
) {
    // Aquí usamos un Scroll para que si hay muchos elementos, se pueda desplazar
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Títulos del Dashboard
        Text(
            text = "Dashboard",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        // Tarjetas de acción (Agregar, Editar, Lotes)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DashboardActionCard(
                modifier = Modifier.weight(1f),
                title = "Agregar Producto",
                icon = Icons.Default.AddCircle,
                onClick = { navController.navigate("addProduct") }
            )
            DashboardActionCard(
                modifier = Modifier.weight(1f),
                title = "Editar Producto",
                icon = Icons.Default.Edit,
                onClick = { navController.navigate("editProduct") }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DashboardActionCard(
                modifier = Modifier.weight(1f),
                title = "Gestionar Lotes",
                icon = Icons.Default.Inventory2,
                onClick = { navController.navigate("lote") }
            )
            DashboardActionCard(
                modifier = Modifier.weight(1f),
                title = "Estadísticas",
                icon = Icons.Default.Assessment,
                onClick = { /* Implementar navegación a estadísticas */ }
            )
        }

        // Sección de estadísticas (cantidad de productos, lotes, etc.)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DashboardStatsCard(
                modifier = Modifier.weight(1f),
                title = "Productos",
                count = 1234, // Este valor lo puedes obtener desde tu viewmodel
                icon = Icons.Default.Inventory
            )
            DashboardStatsCard(
                modifier = Modifier.weight(1f),
                title = "Lotes",
                count = 456, // Este valor lo puedes obtener desde tu viewmodel
                icon = Icons.Default.Apps
            )
        }

    }
}