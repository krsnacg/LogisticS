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
import com.example.logistics.ui.product.ProductViewModel

@Composable
fun MenuScreen(navController: NavController, productViewModel: ProductViewModel) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Productos", fontWeight = FontWeight.SemiBold, fontSize = 32.sp)
        Spacer(modifier = Modifier.height(16.dp))
//        Button(
//            onClick = {
//                productViewModel.toggleEditable(true)
//                productViewModel.getProductLastCode()
//                navController.navigate("addProduct")
//            },
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text("Productos")
//        }
//        Spacer(modifier = Modifier.height(16.dp))
//        Button(
//            onClick = {
//                productViewModel.toggleEditable(false)
//                productViewModel.getAllProducts()
//                navController.navigate("editProduct")
//            },
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text("Editar Productos")
//        }
        // Agregar más botones para otros módulos de gestión

        Button(
            onClick = { navController.navigate("products") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Seccion de productos")
        }
    }
}