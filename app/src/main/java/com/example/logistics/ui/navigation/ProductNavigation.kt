package com.example.logistics.ui.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.logistics.ui.product.AddProductScreen
import com.example.logistics.ui.product.AlertDialogExample
import com.example.logistics.ui.product.BatchScreen
import com.example.logistics.ui.product.EditProductScreen
import com.example.logistics.ui.product.ProductViewModel

@Composable
fun ProductNavigation(
    navController: NavController,
    productViewModel: ProductViewModel = viewModel()
): NavController {

    val productNavController = rememberNavController()
    val tabs = listOf(
        "products/addProduct" to "Registrar",
        "products/editProduct" to "Editar"
    )
    var showDialog by remember { mutableStateOf(false) }
    var route by remember { mutableStateOf("") }

    Column {
        val currentDestination = productNavController.currentRoute()
//        val selectedTabIndex = tabs.indexOfFirst { it.first == currentDestination }
        val selectedTabIndex = when {
            currentDestination?.startsWith("products/addProduct") == true -> 0
            currentDestination?.startsWith("products/editProduct") == true -> 1
            else -> 0
        }
        TabRow(
            selectedTabIndex = selectedTabIndex.takeIf { it >= 0 } ?: 0
        ) {
            tabs.forEachIndexed { index, (screen, title) ->
                Tab(
                    selected = index == selectedTabIndex,
                    onClick = {
                        if (productViewModel.isProductModified()) {
                            showDialog = true
                            route = screen
                        } else {
                            productNavController.navigate(screen)
                        }
                    },
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
            composable(route = "products/editProduct/{productId}") { backStackEntry ->
                val productId = backStackEntry.arguments?.getString("productId") ?: ""
                AddProductScreen(productNavController, productViewModel)
            }
            composable(route = "products/editProduct/batchScreen") {
                BatchScreen(productNavController, productViewModel)
            }
            composable(route = "products/batchScreen") {
                BatchScreen(productNavController, productViewModel)
            }
        }
        if (showDialog && productViewModel.isProductModified()) {
            AlertDialogExample(
                isDismissEnabled = true,
                onDismissRequest = { showDialog = false },
                onConfirmation = {
                    showDialog = false
                    productViewModel.resetBatches()
                    productViewModel.resetProduct()
                    productViewModel.toggleEditable(true)
                    productNavController.navigate(route) {
                        popUpTo(route) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                dialogTitle = "Advertencia",
                dialogText = "¿Desea abortar la operación?",
                icon = Icons.Default.Info
            )
        }
    }

    return productNavController
}