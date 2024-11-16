package com.example.logistics.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.logistics.model.EmpleadoDto
import com.example.logistics.model.MenuItem

@Composable
fun DrawerContent(
    empleado: EmpleadoDto?,
    menuItems: List<MenuItem>,
    selectedItem: MenuItem,
    navController: NavController,
    onItemSelected: (MenuItem) -> Unit,
    onCloseDrawer: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(300.dp)
            .background(MaterialTheme.colorScheme.surface)
            .padding(vertical = 24.dp)
    ) {
        // Header con información del usuario
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.primaryContainer
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.size(40.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "${empleado?.nombre} ${empleado?.apellido}",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "${empleado?.cargo}",
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Menú Items
        menuItems.forEach { item ->
            val isSelected = item == selectedItem
            val itemColor = if (isSelected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.onSurface
            }

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 4.dp),
                onClick = {
                    onItemSelected(item)
                    navController.navigate(item.route) // Navega a la ruta específica de cada item
                },
                color = if (isSelected) {
                    MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.1f)
                } else {
                    Color.Transparent
                },
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null,
                        tint = itemColor
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.bodyLarge,
                        color = itemColor
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Perfil y Configuración
        DrawerButton(
            icon = Icons.Default.Person,
            text = "Ver Perfil",
            onClick = { /* Implementar navegación al perfil */ }
        )

        DrawerButton(
            icon = Icons.Default.Settings,
            text = "Configuración",
            onClick = { /* Implementar navegación a configuración */ }
        )
    }
}