package com.example.logistics.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.logistics.ui.login.LoginScreen
import com.example.logistics.ui.menu.MenuScreen
import com.example.logistics.ui.product.AddProductScreen
import com.example.logistics.ui.product.EditProductScreen
import com.example.logistics.ui.product.BatchScreen
import com.example.logistics.ui.product.ProductViewModel


@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val productViewModel: ProductViewModel = viewModel(factory = ProductViewModel.Factory)

    NavHost(navController = navController, startDestination = "login") {
        composable(route = "login") { LoginScreen(navController) }
        composable(route = "menu") { MenuScreen(navController, productViewModel) }
        composable(route = "addProduct") { AddProductScreen(navController, productViewModel) }
        composable(route = "editProduct") { EditProductScreen(navController, productViewModel) }
        composable(route = "lote") { BatchScreen(navController, productViewModel) }
    }
}