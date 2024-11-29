package com.example.logistics.ui.dashboard.venta

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.logistics.model.CustomerMetric
import com.example.logistics.model.ProductMetric
import com.example.logistics.model.SalesMetric
import com.example.logistics.ui.cotizacion.CotizacionViewModel
import com.example.logistics.ui.product.ProductViewModel
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import android.graphics.Color as AndroidColor
import androidx.compose.ui.graphics.Color as ComposeColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SalesDashboard(
    productViewModel: ProductViewModel,
    navController: NavController,
    cotizacionViewModel: CotizacionViewModel
) {
    // Datos de ejemplo
    val topProducts = productViewModel.topProducts.collectAsState().value
    val topCustomers = cotizacionViewModel.topCustomers.collectAsState().value
    val salesData = cotizacionViewModel.salesData.collectAsState().value
  /*  val salesData = listOf(
        SalesMetric("January", 50000.0),
        SalesMetric("Febrary", 55000.0),
        SalesMetric("March", 60000.0),
        SalesMetric("April", 65000.0),
        SalesMetric("May", 70000.0)
    )*/
/*
    val topCustomers = listOf(
        CustomerMetric("María Rodríguez", 35000.0),
        CustomerMetric("Carlos Gómez", 28000.0),
        CustomerMetric("Ana Martínez", 22000.0)
    )
    */

/*
    val topProducts = listOf(
        ProductMetric("Smartphone Pro", 120, 45000.0),
        ProductMetric("Laptop Ultra", 85, 38000.0),
        ProductMetric("Tablet Lite", 200, 25000.0)
    )
*/
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Panel de Ventas",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1A237E),
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        // Modificador de scroll vertical
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFF5F5F5),
                            Color(0xFFE0E0E0)
                        )
                    )
                )
                .padding(16.dp)
        ) {
            // Fila de Métricas Principales
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                MetricCard(
                    title = "Cliente Destacado",
                    value = topCustomers.first().name,
                    subValue = "$ ${topCustomers.first().totalPurchase.toInt()}",
                    icon = Icons.Default.Person,
                    backgroundColor = Color(0xFF2196F3)
                )

                MetricCard(
                    title = "Ventas Totales",
                    value = "$ ${salesData.sumOf { it.revenue }.toInt()}",
                    subValue = "${salesData.size} Meses",
                    icon = Icons.Default.AttachMoney,
                    backgroundColor = Color(0xFF4CAF50)
                )

                MetricCard(
                    title = "Producto Top",
                    value = topProducts.first().name,
                    subValue = "${topProducts.first().unitsSold} Unidades",
                    icon = Icons.Default.ShoppingCart,
                    backgroundColor = Color(0xFFFF5722)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Gráficas en columna
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Gráfica de Ventas Mensuales
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.medium)
                        .background(Color.White)
                        .padding(16.dp)
                ) {
                    Column {
                        Text(
                            text = "Ventas Mensuales",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        SalesLineChart(salesData)
                    }
                }

                // Gráfica de Distribución de Productos
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.medium)
                        .background(Color.White)
                        .padding(16.dp)
                ) {
                    Column {
                        Text(
                            text = "Distribución de Productos",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        ProductPieChart(topProducts)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Sección de Top Clientes y Productos
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TopCustomersCard(topCustomers)
                TopProductsCard(topProducts)
            }
        }
    }
}







@Composable
fun MetricCard(
    title: String,
    value: String,
    subValue: String,
    icon: ImageVector,
    backgroundColor: Color
) {
    Card(
        modifier = Modifier
            .width(120.dp)
            .height(160.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = Color.White,
                modifier = Modifier.size(40.dp)
            )
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.White.copy(alpha = 0.7f)
                )
                Text(
                    text = value,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = subValue,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.9f)
                )
            }
        }
    }
}

@Composable
fun TopCustomersCard(customers: List<CustomerMetric>) {
    Card(
        modifier = Modifier
            .width(200.dp)
            .height(250.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Top Clientes",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            customers.forEach { customer ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Text(
                        text = customer.name,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "$ ${customer.totalPurchase.toInt()}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Blue,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
                Divider(modifier = Modifier.padding(vertical = 4.dp))
            }
        }
    }
}

@Composable
fun TopProductsCard(products: List<ProductMetric>, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .width(200.dp)
            .wrapContentHeight(),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Top Productos",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .fillMaxWidth()
            )

            // Limit to top 5 products to prevent overcrowding
            products.take(5).forEachIndexed { index, product ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Text(
                        text = product.name,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 4.dp),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                    Text(
                        text = "${product.unitsSold} Uds",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Blue
                    )
                }

                // No divider after the last item
                if (index < products.size - 1) {
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                    )
                }
            }
        }
    }
}

// Gráfica de Líneas - Similar a la anterior
@Composable
fun SalesLineChart(salesData: List<SalesMetric>) {
    val entries = salesData.mapIndexed { index, metric ->
        Entry(index.toFloat(), metric.revenue.toFloat())
    }

    val dataSet = LineDataSet(entries, "Ventas Mensuales").apply {
        color = ComposeColor(0xFF1A237E).toArgb()
        valueTextColor = AndroidColor.RED
        setDrawValues(true)
        setDrawCircles(true)

        // Mejoras visuales
        lineWidth = 3f
        setCircleColor(ComposeColor(0xFF1A237E).toArgb())
        circleRadius = 5f
        setDrawFilled(true)
        fillColor = ComposeColor(0xFF1A237E).copy(alpha = 0.2f).toArgb()
    }

    val lineData = LineData(dataSet)

    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        factory = { context ->
            LineChart(context).apply {
                data = lineData
                description.isEnabled = false
                setTouchEnabled(true)
                isDragEnabled = true
                setScaleEnabled(true)

                // Configuraciones adicionales para mejorar el aspecto
                xAxis.isEnabled = true
                axisLeft.isEnabled = true
                axisRight.isEnabled = false

                animateX(1500)
            }
        }
    )
}

@Composable
fun ProductPieChart(products: List<ProductMetric>) {
    val entries = products.map {
        PieEntry(it.unitsSold.toFloat(), it.name)
    }

    val dataSet = PieDataSet(entries,"").apply {
        colors = listOf(
            ComposeColor(0xFF2196F3).toArgb(),
            ComposeColor(0xFF4CAF50).toArgb(),
            ComposeColor(0xFFFF5722).toArgb()
        )
        valueTextColor = AndroidColor.WHITE
        valueTextSize = 12f

        // Mejoras visuales
        sliceSpace = 3f
        selectionShift = 5f
    }

    val pieData = PieData(dataSet)

    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        factory = { context ->
            PieChart(context).apply {
                data = pieData
                description.isEnabled = false
                setUsePercentValues(true)
                isDrawHoleEnabled = true
                setEntryLabelColor(AndroidColor.WHITE)

                // Configuraciones adicionales
                centerText = "Distribución"
                setCenterTextSize(15f)
                setDrawEntryLabels(true)

                animateXY(1400, 1400)
            }
        }
    )
}
