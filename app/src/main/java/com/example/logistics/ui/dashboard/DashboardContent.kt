package com.example.logistics.ui.dashboard

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.logistics.R
import com.example.logistics.data.CategoryChart
import com.example.logistics.model.EmpleadoDto
import com.example.logistics.ui.dashboard.components.ExpiringSoonCard
import com.example.logistics.ui.dashboard.components.LowStockCard
import com.example.logistics.ui.dashboard.components.OperationsDashboard
import com.example.logistics.ui.dashboard.components.ProductCategoryChart
import com.example.logistics.ui.dashboard.components.ProductSummary
import com.example.logistics.ui.product.ProductViewModel

@Composable
fun DashboardContent(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    empleado: EmpleadoDto?,
    productViewModel: ProductViewModel,
    navController: NavController
) {

    val categorias = mapOf(
        "Analgésicos" to 30f,
        "Antibióticos" to 25f,
        "Vitaminas" to 20f,
        "Otros" to 25f
    )

    val lowerStockProduct = productViewModel.lowerStockProduct.collectAsState().value
    val expiringProduct = productViewModel.expiringProduct.collectAsState().value
    val quantityProducts = productViewModel.quantityProducts.collectAsState().value
    val categoriesQuantity = productViewModel.categoriesQuantity.collectAsState().value
    val totalCategories = productViewModel.totalCategorias.collectAsState().value
    val totalLotes = productViewModel.totalLotes.collectAsState().value


    Column(
        modifier = modifier
            .padding(paddingValues)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {

        Text(
            text = "Bienvenido, ${empleado?.nombre}",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 4.dp),
            fontFamily = FontFamily(Font(R.font.montserrat_bold))
        )


        Text(
            text = "Operaciones",
            style = MaterialTheme.typography.bodyLarge, // Estilo del subtítulo
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
            fontFamily = FontFamily(Font(R.font.montserrat_regular))
        )


        OperationsDashboard(
            modifier = Modifier.fillMaxWidth()
        ) { operation ->
            when (operation) {
                "Registrar producto" -> {
                    try {
                        productViewModel.toggleEditable(true)
                        navController.navigate("products")
                    } catch (e: Exception) {
                        Log.e("NavigationError", "Error navigating: ${e.message}")
                    }
                }
                "Editar producto" -> {
                    try {
                        productViewModel.toggleEditable(true)
                        navController.navigate("products")
                    } catch (e: Exception) {
                        Log.e("NavigationError", "Error navigating: ${e.message}")
                    }
                }
                "Gestionar lotes" -> {}
                "Generar guía de remisión" -> {}
            }
        }



        Spacer(modifier = Modifier.height(16.dp))

        if(quantityProducts != null) {
            ProductSummary(
                modifier = Modifier.fillMaxWidth(),
                totalProducts = quantityProducts,
                text = "Total productos"
            )
        }

        if(totalCategories != null) {
            ProductSummary(
                modifier = Modifier.fillMaxWidth(),
                totalProducts = totalCategories,
                text = "Total categorias"
            )
        }

        if(totalLotes != null) {
            ProductSummary(
                modifier = Modifier.fillMaxWidth(),
                totalProducts = totalLotes,
                text = "Total de lotes"
            )
        }


        if (lowerStockProduct != null) {
            LowStockCard(
                productName ="${lowerStockProduct.nombre} ${lowerStockProduct.concentracion}",
                stockCount = lowerStockProduct.stock,
                modifier = Modifier
            )
        }


        Spacer(modifier = Modifier.height(8.dp))

        if(expiringProduct != null ){
            ExpiringSoonCard(
                productName = "${expiringProduct.nombre} ${expiringProduct.concentracion}",
                expirationDate = expiringProduct.fechaExpiracion,
                loteId = expiringProduct.loteId,
                modifier = Modifier
            )
        }





        ProductCategoryChart(
            data = convertListToMap(categoriesQuantity),
            //onCategoryClick = onCategoryClick
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

fun convertListToMap(products: List<CategoryChart>): Map<String, Float> {
    return products.associate { it.nombre to it.cantidad }
}