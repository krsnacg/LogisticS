package com.example.logistics.ui.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.logistics.ui.cliente.ClienteViewModel
import com.example.logistics.ui.cliente.FormularioCliente
import com.example.logistics.ui.cliente.ListaClientes
import com.example.logistics.ui.cotizacion.CotizacionScreen
import com.example.logistics.ui.cotizacion.CotizacionViewModel
import com.example.logistics.ui.cotizacion.EditCotizacion
import com.example.logistics.ui.cotizacion.QuotationForm
import com.example.logistics.ui.product.ProductViewModel

@Composable
fun ClienteNavigation(navController: NavController, clienteViewModel: ClienteViewModel) {

    val clienteNavController = rememberNavController()

    val tabs = listOf(
        "cliente/new" to "crear",
        "cliente/list" to "lista",
        "cliente/edit" to "actualizar"
    )

    Column {
        val currentDestination = clienteNavController.currentRoute()
        val selectedTabIndex = tabs.indexOfFirst { it.first == currentDestination }

        TabRow(
            selectedTabIndex = selectedTabIndex.takeIf { it>=0 } ?: 0
        ) {

            tabs.forEachIndexed { index, (screen,title) ->
                Tab(
                    selected = index == selectedTabIndex,
                    onClick = {
                        clienteNavController.navigate(screen)
                    },
                    text = { Text(text = title) }
                )
            }

        }

        NavHost(
            navController = clienteNavController,
            startDestination = "cliente/list"
        ) {
            composable(route = "cliente/new") {
                FormularioCliente(clienteViewModel)
            }
            composable(route = "cliente/list") {
                ListaClientes(clienteViewModel)
            }
            composable(route = "cliente/edit") {

            }
        }



    }


}