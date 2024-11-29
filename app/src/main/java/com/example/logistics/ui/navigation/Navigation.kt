package com.example.logistics.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.logistics.model.LoginViewModel
import com.example.logistics.ui.cliente.ClienteViewModel
import com.example.logistics.ui.cotizacion.CotizacionViewModel
import com.example.logistics.ui.dashboard.venta.SalesDashboard
import com.example.logistics.ui.login.LoginScreen
import com.example.logistics.ui.product.AddProductScreen
import com.example.logistics.ui.product.BatchScreen
import com.example.logistics.ui.product.EditProductScreen
import com.example.logistics.ui.screens.menu.MenuScreen
import com.example.logistics.ui.product.ProductViewModel


@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val productViewModel: ProductViewModel = viewModel(factory = ProductViewModel.Factory)

    val loginViewModel: LoginViewModel = viewModel()

    var productNavController by remember { mutableStateOf<NavController?>(null) }

    val cotizacionViewModel: CotizacionViewModel = viewModel()
    val clienteViewModel: ClienteViewModel = viewModel()

    Scaffold(
        /*topBar = {
            if (navController.currentRoute() != "login")
                AppTopBar(navController = navController)
        },*/
        bottomBar = {
            if (navController.currentRoute() != "login") {
//                if (showBottomBar)
                //AppBottomBar(navController = navController)
                NavigationBarSample(navController = navController, loginViewModel = loginViewModel)
            }
        }
    ) { innerPadding ->
//        Column(modifier = Modifier.padding(innerPadding)) {
//            if (currentRoute in productRoutes)
            NavHost(
                navController = navController,
                startDestination = "login",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(route = "login") { LoginScreen(navController, loginViewModel) }
                composable(route = "menu") {  MenuScreen(navController, loginViewModel, productViewModel,cotizacionViewModel) }
                //composable(route = "menu_venta") { SalesDashboard()  }
                composable(route = "products") { productNavController = ProductNavigation(navController, productViewModel) }
                composable(route = "cotizaciones") { CotizacionNavigation(navController, cotizacionViewModel, productViewModel, clienteViewModel) }
                composable(route = "clientes") { ClienteNavigation(navController,clienteViewModel)  }
            }
//        }
    }
}

@Composable
fun NavController.currentRoute(): String? {
    val navBackStackEntry by currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}