package com.example.logistics.ui.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Settings
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

import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.logistics.R
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
fun NavigationBarSample(navController: NavController) {

    val items = listOf("Principal", "Producto", "Compra", "Ajustes")
    val selectedIcons = listOf(
        painterResource(R.drawable.baseline_star_24),
        painterResource(R.drawable.baseline_indeterminate_check_box_24),
        painterResource(R.drawable.baseline_check_box_outline_blank_24),
        painterResource(R.drawable.baseline_settings_24)
    )
    val unselectedIcons = listOf(
        painterResource(R.drawable.baseline_star_outline_24),
        painterResource(R.drawable.outline_indeterminate_check_box_24),
        painterResource(R.drawable.baseline_check_box_outline_blank_24),
        painterResource(R.drawable.outline_settings_24)
    )
    val routes = listOf("menu", "products", "menu", "menu") // Fix with other routes

    val currentRoute = navController.currentRoute()
    var selectedItem = routes.indexOf(currentRoute).takeIf { it >= 0 } ?: 0

    NavigationBar(containerColor = MaterialTheme.colorScheme.primary) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        if (selectedItem == index) selectedIcons[index] else unselectedIcons[index],
                        contentDescription = item
                    )
                },
                label = { Text(item) },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    navController.navigate(routes[index]) {
                        popUpTo(routes[index]) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
    }

}