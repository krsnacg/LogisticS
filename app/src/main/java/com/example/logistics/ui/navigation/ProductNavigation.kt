package com.example.logistics.ui.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.logistics.ui.product.AddProductScreen
import com.example.logistics.ui.product.BatchScreen
import com.example.logistics.ui.product.EditProductScreen
import com.example.logistics.ui.product.ProductViewModel

@Composable
fun ProductNavigation(navController: NavController, productViewModel: ProductViewModel = viewModel()): NavController {

    val productNavController = rememberNavController()
    val tabs = listOf(
        "products/addProduct" to "Registrar",
        "products/editProduct" to "Editar"
    )

    Column {
        val currentDestination = productNavController.currentRoute()
        val selectedTabIndex = tabs.indexOfFirst { it.first == currentDestination }
        TabRow(
            selectedTabIndex = selectedTabIndex.takeIf { it >= 0 } ?: 0
        ) {
            tabs.forEachIndexed { index, (screen, title) ->
                Tab(
                    selected = index == selectedTabIndex,
                    onClick = { productNavController.navigate(screen) },
                    text = { Text(text = title) }
                )
            }
        }
        NavHost(
            navController = productNavController,
            startDestination = "products/addProduct"
        ) {
            composable(route = "products/addProduct") {
                AddProductScreen(productNavController, productViewModel)
            }
            composable(route = "products/editProduct") {
                EditProductScreen(productNavController, productViewModel)
            }
            composable(route = "products/addProduct/batchScreen") {
                BatchScreen(productNavController, productViewModel)
            }
        }

    }

    return productNavController
}