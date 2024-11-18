package com.example.logistics.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.logistics.ui.login.LoginScreen
import com.example.logistics.ui.menu.MenuScreen
import com.example.logistics.ui.product.ProductViewModel


@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val productViewModel: ProductViewModel = viewModel(factory = ProductViewModel.Factory)
//    val currentRoute = navController.currentRoute()
//    val productRoutes = listOf("addProduct", "editProduct", "lote")

//    val scrollState = rememberLazyListState()
//    var showBottomBar by remember { mutableStateOf(true) }
//
//    // Detectar el scroll y actualizar la visibilidad del bottom bar
//    LaunchedEffect(scrollState.firstVisibleItemIndex) {
//        // Mostrar el bottom bar si se está desplazando hacia arriba
//        showBottomBar = scrollState.isScrollingUp()
//    }

    Scaffold(
        topBar = {
            if (navController.currentRoute() != "login")
                AppTopBar(navController = navController)
        },
        bottomBar = {
            if (navController.currentRoute() != "login") {
//                if (showBottomBar)
                //AppBottomBar(navController = navController)
                NavigationBarSample(navController = navController)
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
                composable(route = "login") { LoginScreen(navController) }
                composable(route = "menu") { MenuScreen(navController, productViewModel) }
                composable(route = "products") { ProductNavigation(navController, productViewModel) }
            }
//        }
    }
}

@Composable
fun NavController.currentRoute(): String? {
    val navBackStackEntry by currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}