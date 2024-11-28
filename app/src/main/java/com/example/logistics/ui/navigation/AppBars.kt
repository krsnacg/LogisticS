package com.example.logistics.ui.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Article
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.automirrored.filled.ListAlt
import androidx.compose.material.icons.automirrored.outlined.Article
import androidx.compose.material.icons.automirrored.outlined.Assignment
import androidx.compose.material.icons.automirrored.outlined.ListAlt
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Article
import androidx.compose.material.icons.outlined.Assignment
import androidx.compose.material.icons.outlined.Inventory
import androidx.compose.material.icons.outlined.ListAlt
import androidx.compose.material.icons.outlined.Receipt
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.logistics.R
import com.example.logistics.model.LoginViewModel
import com.example.logistics.model.NavigationOptions
import com.example.logistics.ui.theme.AppTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(navController: NavController) {

    val currentRoute = navController.currentRoute() ?: "login"
    val title = when (currentRoute) {
        "menu" -> "Dashboard"
        "addProduct" -> "Registrar Producto"
        "editProduct" -> "Editar Producto"
        "products" -> "Productos"
        else -> "App"
    }

    CenterAlignedTopAppBar(
        title = { Text(text = title, style = AppTypography.titleLarge) },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = null
                )
            }
        },
        actions = {
            IconButton(onClick = {
                navController.navigate("login") {
                    popUpTo("login") { inclusive = true }
                }
            }) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null
                )
            }
        },

    )
}

@Composable
fun AppBottomBar(navController: NavController) {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.onPrimary,
    ) {
        Column {
            IconButton(onClick = { navController.navigate("menu") }) {
                Icon(
                    painter = painterResource(R.drawable.baseline_star_outline_24),
                    contentDescription = null
                )
            }
            Text(text = "Principal", style = AppTypography.labelSmall)
        }
        Spacer(modifier = Modifier.weight(1f))
        Column {
            IconButton(onClick = { navController.navigate("addProduct") }) {
                Icon(
                    painter = painterResource(R.drawable.outline_indeterminate_check_box_24),
                    contentDescription = null
                )
            }
            Text(text = "Producto", style = AppTypography.labelSmall)
        }
        Spacer(modifier = Modifier.weight(1f))
        Column {
            IconButton(onClick = {  }) {
                Icon(
                    painter = painterResource(R.drawable.baseline_check_box_outline_blank_24),
                    contentDescription = null
                )
            }
            Text(text = "Compra", style = AppTypography.labelSmall)
        }
        Spacer(modifier = Modifier.weight(1f))
        Column {
            IconButton(onClick = {  }) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = null,
                )
            }
            Text(text = "Ajustes", style = AppTypography.labelSmall)
        }
    }
}

@Composable
fun NavigationBarSample(
    navController: NavController,
    loginViewModel: LoginViewModel
) {
    val uiState by loginViewModel.uiState.collectAsState()

    // Definir las opciones de navegación basadas en el rol
    val navigationOptions = when (uiState.rol) {
        "ROL_VENTA" -> NavigationOptions(
            items = listOf("Principal", "Cotización", "Pedidos", "Facturas"),
            routes = listOf("menu", "cotizacion", "pedidos", "facturas"),
            selectedIcons = listOf(
                Icons.Filled.Star,
                Icons.AutoMirrored.Filled.Assignment,
                Icons.AutoMirrored.Filled.ListAlt,
                Icons.Filled.Receipt
            ),
            unselectedIcons = listOf(
                Icons.Outlined.Star,
                Icons.AutoMirrored.Outlined.Assignment,
                Icons.AutoMirrored.Outlined.ListAlt,
                Icons.Outlined.Receipt
            )
        )
        "ROL_ALMACEN" -> NavigationOptions(
            items = listOf("Principal", "Producto", "Documentos"),
            routes = listOf("menu", "products", "documentos"),
            selectedIcons = listOf(
                Icons.Filled.Star,
                Icons.Filled.Inventory,
                Icons.AutoMirrored.Filled.Article
            ),
            unselectedIcons = listOf(
                Icons.Outlined.Star,
                Icons.Outlined.Inventory,
                Icons.AutoMirrored.Outlined.Article
            )
        )
        "ROL_COMPRA" -> NavigationOptions(
            items = listOf("Cotización Compra", "Orden Compra", "Facturas Compra"),
            routes = listOf("cotizacion-compra", "orden-compra", "facturas-compra"),
            selectedIcons = listOf(
                Icons.AutoMirrored.Filled.Assignment,
                Icons.Filled.ShoppingCart,
                Icons.Filled.Receipt
            ),
            unselectedIcons = listOf(
                Icons.AutoMirrored.Outlined.Assignment,
                Icons.Outlined.ShoppingCart,
                Icons.Outlined.Receipt
            )
        )
        else -> NavigationOptions( // Opciones por defecto o para roles no definidos
            items = listOf("Principal"),
            routes = listOf("menu"),
            selectedIcons = listOf(Icons.Filled.Star),
            unselectedIcons = listOf(Icons.Outlined.Star)
        )
    }

    // Obtener la ruta actual y seleccionar el índice correspondiente
    val currentRoute = navController.currentRoute()
    var selectedItem by remember { mutableStateOf(navigationOptions.routes.indexOf(currentRoute).takeIf { it >= 0 } ?: 0) }

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        navigationOptions.items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = if (selectedItem == index) navigationOptions.selectedIcons[index]
                        else navigationOptions.unselectedIcons[index],
                        contentDescription = item,
                        tint = if (selectedItem == index)
                            MaterialTheme.colorScheme.onPrimary
                        else MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f)
                    )
                },
                label = {
                    Text(
                        text = item,
                        style = MaterialTheme.typography.bodySmall,
                        color = if (selectedItem == index)
                            MaterialTheme.colorScheme.onPrimary
                        else MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f)
                    )
                },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    navController.navigate(navigationOptions.routes[index]) {
                        popUpTo(navigationOptions.routes[index]) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                alwaysShowLabel = true
            )
        }
    }
}