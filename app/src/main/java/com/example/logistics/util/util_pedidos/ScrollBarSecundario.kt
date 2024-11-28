package com.example.logistics.util.util_pedidos

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.logistics.ui.theme.LogisticSTheme

@Composable
fun ScrollBarSecundario(
    modifier: Modifier = Modifier,
    listaElementos: List<String> = listOf("Elemento 1", "Elemento 2", "Elemento 3"),
    indiceInicial : Int = 0,
    onElementoSeleccionado: (Int) -> Unit // Mes seleccionado
) {
    var indiceElementoSeleccionado by rememberSaveable { mutableStateOf(indiceInicial) }

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp)
    ) {
        LazyRow (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            items(listaElementos.size) { index ->
                val elemento = listaElementos[index]
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            indiceElementoSeleccionado = index
                            onElementoSeleccionado(index) //Llamar al callback
                        },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = elemento,
                        color = if (indiceElementoSeleccionado == index)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleSmall,
                        modifier = modifier
                    )
                    HorizontalDivider(
                        color =
                        if (indiceElementoSeleccionado == index)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.surface,
                        thickness = 2.dp,
                        modifier = Modifier
                            .width(60.dp)
                            .padding(top = 4.dp, bottom = 0.dp)
                    )
                }

            }
        }
        HorizontalDivider(
            color = MaterialTheme.colorScheme.outlineVariant,
            thickness = 1.dp,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 0.dp, bottom = 8.dp)
        )
    }

}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun ScrollBardSecundarioLightPreview() {
    LogisticSTheme(darkTheme = false) {
        ScrollBarSecundario(
            onElementoSeleccionado = { index ->}
        ) }
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun ScrollBardSecundarioDarkPreview() {
    LogisticSTheme(darkTheme = true) {
        ScrollBarSecundario(
            onElementoSeleccionado = { index ->}
        ) }
}