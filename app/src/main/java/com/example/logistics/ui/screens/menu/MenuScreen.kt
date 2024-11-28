package com.example.logistics.ui.screens.menu

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.navigation.NavController
import com.example.logistics.R
import com.example.logistics.model.LoginViewModel
import com.example.logistics.model.MenuItem
import com.example.logistics.ui.screens.menu.components.DrawerContent
import com.example.logistics.ui.dashboard.DashboardContent
import com.example.logistics.ui.product.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(
    navController: NavController,
    loginViewModel: LoginViewModel,
    productViewModel: ProductViewModel
) {
    val uiState by loginViewModel.uiState.collectAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val menuItems = listOf(
        MenuItem("Gestionar Productos", Icons.Default.AddCircle, "products"),
        MenuItem("Ver documentos", Icons.Default.Edit, "products"),
    )

    val selectedItem = remember { mutableStateOf(menuItems[0]) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerContent(
                    empleado = uiState.empleado,
                    menuItems = menuItems,
                    selectedItem = selectedItem.value,
                    navController = navController,
                    onItemSelected = { menuItem ->
                        selectedItem.value = menuItem
                        scope.launch {
                            drawerState.close()
                            navController.navigate(menuItem.route)
                        }
                    },
                    onCloseDrawer = {
                        scope.launch {
                            drawerState.close()
                        }
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Dashboard", fontFamily = FontFamily(Font(R.font.montserrat_regular))) },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    },
                    actions = {
                        IconButton(onClick = { loginViewModel.logout(navController) }) {
                            Icon(Icons.Default.ExitToApp, contentDescription = "Logout")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                        actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            },
            content = { paddingValues ->

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    DashboardContent(
                        modifier = Modifier.fillMaxWidth(),
                        paddingValues = PaddingValues(0.dp),
                        empleado = uiState.empleado,
                        productViewModel = productViewModel,
                        navController = navController
                    )
                }
            }
        )
    }
}