package com.example.logistics.ui.cliente

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.logistics.model.Cliente

@Composable
fun ListaClientes(clienteViewModel: ClienteViewModel, clienteNavController: NavController) {

    val clientes = clienteViewModel.clientes.collectAsState().value

    LaunchedEffect(Unit) {
        clienteViewModel.getClientes()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0F0F0))
            .padding(16.dp)
    ) {
        items(clientes) { cliente ->
            ClienteCard(cliente, clienteViewModel, clienteNavController)
        }
    }
}

@Composable
fun ClienteCard(cliente: Cliente, clienteViewModel: ClienteViewModel, navClienteController: NavController) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = cliente.empresa,
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                color = Color(0xFF6200EA)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Representante: ${cliente.representante}",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Phone, contentDescription = "Teléfono", tint = Color(0xFF6200EA))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = cliente.telefono, style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Email, contentDescription = "Email", tint = Color(0xFF6200EA))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = cliente.email, style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.LocationOn, contentDescription = "Dirección", tint = Color(0xFF6200EA))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = cliente.direccion, style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Place, contentDescription = "Departamento", tint = Color(0xFF6200EA))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = cliente.nombre_departamento, style = MaterialTheme.typography.bodyMedium)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón Editar con fondo gradiente
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(Color(0xFF6200EA), Color(0xFF3700B3))
                            )
                        )
                        .clickable {
                            clienteViewModel.selectedCliente(cliente)
                            navClienteController.navigate("cliente/edit")
                        }
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Editar",
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Editar",
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}
