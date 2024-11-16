package com.example.logistics.ui.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ProductStats() {
    // Gráfico de productos más vendidos
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        //elevation = 8.dp,
        //backgroundColor = MaterialTheme.colorScheme.primaryContainer
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Productos más vendidos",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )

            // Aquí puedes usar alguna librería como MPAndroidChart para gráficos, o usar alguna otra implementación de gráfico que te guste.
            // Este es solo un ejemplo de cómo se podría integrar un gráfico
            // Por ejemplo, un gráfico de barras, líneas o un gráfico de sectores.

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(MaterialTheme.colorScheme.onSurfaceVariant)
            ) {
                // Aquí iría el gráfico
            }
        }
    }
}