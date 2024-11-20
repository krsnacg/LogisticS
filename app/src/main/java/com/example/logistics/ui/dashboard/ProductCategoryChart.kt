package com.example.logistics.ui.dashboard

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

@Composable
fun ProductCategoryChart(
    modifier: Modifier = Modifier,
    data: Map<String, Float> // Mapa de categoría y porcentaje
) {
    // Configuración de colores para las categorías
    val colors = listOf(
        Color(0xFF4CAF50), // Verde
        Color(0xFFFF9800), // Naranja
        Color(0xFF2196F3), // Azul
        Color(0xFFF44336)  // Rojo
    )

    AndroidView(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp) // Ajusta esta altura si es necesario
            .padding(16.dp)
            .border(2.dp, Color.Gray, shape = RoundedCornerShape(8.dp)), // Agregar borde para verificar el espacio
        factory = { context ->
            PieChart(context).apply {
                description.isEnabled = false // Desactivar descripción
                isDrawHoleEnabled = true      // Activar agujero en el centro
                holeRadius = 30f              // Ajustar tamaño del agujero
                setUsePercentValues(true)    // Mostrar porcentajes
                setEntryLabelColor(Color.Black.toArgb())
                setEntryLabelTextSize(12f)

                // Leyenda (si es necesario)
                legend.isEnabled = true
                legend.orientation = Legend.LegendOrientation.HORIZONTAL
                legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
                legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
            }
        },
        update = { pieChart ->
            // Crear entradas para las categorías
            val entries = data.map { (category, percentage) ->
                PieEntry(percentage, category)
            }

            // Crear y configurar el dataset
            val dataSet = PieDataSet(entries, "Categorías")
            dataSet.colors = colors.map { it.toArgb() } // Colores personalizados
            dataSet.valueTextSize = 14f
            dataSet.valueTextColor = Color.White.toArgb()

            // Asignar los datos a la gráfica
            pieChart.data = PieData(dataSet)
            pieChart.invalidate() // Actualizar el gráfico
        }
    )
}