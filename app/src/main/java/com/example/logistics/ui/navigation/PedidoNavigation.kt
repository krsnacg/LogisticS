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
import com.example.logistics.ui.cliente.UpdateClienteForm
import com.example.logistics.ui.pedido.CrearPedidoForm
import com.example.logistics.ui.pedido.PedidoViewModel

@Composable
fun PedidoNavigation(navController: NavController, pedidoViewModel: PedidoViewModel) {

    val clienteNavController = rememberNavController()

    val tabs = listOf(
        "pedido/new" to "crear",
        "pedido/list" to "lista",
        "pedido/edit" to "actualizar"
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
            startDestination = "pedido/new"
        ) {
            composable(route = "pedido/new") {
                CrearPedidoForm(pedidoViewModel,navController)
            }
            composable(route = "pedido/list") {

            }
            composable(route = "pedido/edit") {

            }
        }



    }


}