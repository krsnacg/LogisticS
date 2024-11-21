package com.example.logistics.ui.dashboard

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.logistics.R
import com.example.logistics.model.EmpleadoDto
import com.example.logistics.ui.product.ProductViewModel

@Composable
fun DashboardContent(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    empleado: EmpleadoDto?,
    productViewModel: ProductViewModel
) {

    val categorias = mapOf(
        "Analgésicos" to 30f,
        "Antibióticos" to 25f,
        "Vitaminas" to 20f,
        "Otros" to 25f
    )

    val lowerStockProduct = productViewModel.lowerStockProduct.collectAsState().value
    val expiringProduct = productViewModel.expiringProduct.collectAsState().value




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
                "Registrar producto" -> {}
                "Editar producto" -> {}
                "Gestionar lotes" -> {}
                "Generar guía de remisión" -> {}
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        ProductSummary(
            modifier = Modifier.fillMaxWidth(),
            totalProducts = 127
        )


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
            data = categorias,
            //onCategoryClick = onCategoryClick
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}
