package com.example.logistics.util.util_pedidos

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.logistics.ui.theme.LogisticSTheme

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    tituloPagina: String = "<NOMBRE DE PÁGINA>",
    modo: String = "Normal",
    botonPerfil: Boolean = false,
) {
    val close = Icons.Default.Close
    val retroceder = Icons.Default.ArrowBack
    var iconoAdecuado: ImageVector = close
    var colorFondo: Color = MaterialTheme.colorScheme.primaryContainer

    when (modo) {
        "Normal" -> iconoAdecuado = close
        "Retroceder" -> iconoAdecuado = retroceder
    }

    Row (
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Imagen izquierda
        Icon(
            imageVector = iconoAdecuado,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = tituloPagina,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(10f).padding(start = 2.dp, end = 2.dp)
        )

        Box(
            modifier = Modifier.align(Alignment.CenterVertically).weight(1.5f)
        ) {
            if (botonPerfil) {
                Canvas(modifier = Modifier.size(30.dp).align(Alignment.TopCenter)) {
                    drawCircle(
                        color = colorFondo,
                        radius = size.minDimension/2
                    )
                }
            }

            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = modifier.align(Alignment.Center).size(20.dp)
            )
        }

    }
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun TopBarLightPreview() {
    LogisticSTheme(darkTheme = false) { TopBar() }
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun TopBarDarkPreview() {
    LogisticSTheme(darkTheme = true) { TopBar() }
}