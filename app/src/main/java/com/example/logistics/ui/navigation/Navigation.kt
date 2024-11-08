package com.example.logistics.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.logistics.ui.login.LoginScreen
import com.example.logistics.ui.menu.MenuScreen
import com.example.logistics.ui.product.AddProductScreen
import com.example.logistics.ui.product.EditProductScreen


@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable(route = "login") { LoginScreen(navController) }
        composable(route = "menu") { MenuScreen(navController) }
        composable(route = "addProduct") { AddProductScreen(navController) }
        composable(route = "editProduct") { EditProductScreen(navController) }
        composable(route = "lote") {  }
    }
}