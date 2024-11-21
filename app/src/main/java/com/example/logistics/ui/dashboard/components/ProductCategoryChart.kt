package com.example.logistics.ui.dashboard.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
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
    data: Map<String, Float>
) {

    val colors = listOf(
        Color(0xFF4CAF50),
        Color(0xFFFF9800),
        Color(0xFF2196F3),
        Color(0xFFF44336)
    )

    AndroidView(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(16.dp)
            .border(2.dp, Color.Gray, shape = RoundedCornerShape(8.dp)),
        factory = { context ->
            PieChart(context).apply {
                description.isEnabled = false
                isDrawHoleEnabled = true
                holeRadius = 30f
                setUsePercentValues(true)
                setEntryLabelColor(Color.Black.toArgb())
                setEntryLabelTextSize(12f)


                legend.isEnabled = true
                legend.orientation = Legend.LegendOrientation.HORIZONTAL
                legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
                legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
            }
        },
        update = { pieChart ->

            val entries = data.map { (nombre, cantidad) ->
                PieEntry(cantidad,nombre)
            }


            val dataSet = PieDataSet(entries, "Categorías")
            dataSet.colors = colors.map { it.toArgb() }
            dataSet.valueTextSize = 14f
            dataSet.valueTextColor = Color.White.toArgb()


            pieChart.data = PieData(dataSet)
            pieChart.invalidate() // Actualizar el gráfico
        }
    )
}