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
import com.example.logistics.ui.cotizacion.CotizacionScreen
import com.example.logistics.ui.cotizacion.CotizacionViewModel
import com.example.logistics.ui.cotizacion.EditCotizacion

import com.example.logistics.ui.cotizacion.QuotationForm
import com.example.logistics.ui.product.ProductViewModel

@Composable
fun CotizacionNavigation(navController: NavController, cotizacionViewModel: CotizacionViewModel, productViewModel: ProductViewModel) {

    val cotizacionNavController = rememberNavController()

    val tabs = listOf(
        "cotizacion/new" to "crear",
        "cotizacion/list" to "lista",
        "cotizacion/edit" to "actualizar"
    )

    Column {
        val currentDestination = cotizacionNavController.currentRoute()
        val selectedTabIndex = tabs.indexOfFirst { it.first == currentDestination }

        TabRow(
            selectedTabIndex = selectedTabIndex.takeIf { it>=0 } ?: 0
        ) {

            tabs.forEachIndexed { index, (screen,title) ->
                Tab(
                    selected = index == selectedTabIndex,
                    onClick = {
                        cotizacionNavController.navigate(screen)
                    },
                    text = { Text(text = title) }
                )
            }

        }

        NavHost(
            navController = cotizacionNavController,
            startDestination = "cotizacion/new"
        ) {
            composable(route = "cotizacion/new") {
                QuotationForm(cotizacionViewModel, productViewModel)
            }
            composable(route = "cotizacion/list") {
                CotizacionScreen(cotizacionNavController,cotizacionViewModel)
            }
            composable(route = "cotizacion/edit") {
                EditCotizacion( cotizacionNavController,cotizacionViewModel, productViewModel)
            }
        }



    }


}