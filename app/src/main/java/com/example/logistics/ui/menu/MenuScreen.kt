package com.example.logistics.ui.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun MenuScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Productos", fontWeight = FontWeight.SemiBold, fontSize = 32.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { navController.navigate("addProduct") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrar Productos")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { navController.navigate("editProduct") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Editar Productos")
        }
        // Agrega más botones para otros módulos de gestión
    }
}