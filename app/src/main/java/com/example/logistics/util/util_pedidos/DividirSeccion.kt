package com.example.logistics.util.util_pedidos

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.logistics.ui.theme.LogisticSTheme

@Composable
fun DividerSection(
    modifier: Modifier = Modifier,
    nombreSeccion: String = "<NOMBRE DE LA SECCIÓN>"
) {
    Column (
        modifier = Modifier.fillMaxWidth()
    ) {
        HorizontalDivider(
            color = MaterialTheme.colorScheme.outlineVariant,
            thickness = 1.dp,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
        )

        Text(
            text = nombreSeccion,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
        )
    }
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun DividerSectionLightPreview() {
    LogisticSTheme(darkTheme = false) { DividerSection() }
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun DividerSectionDarkPreview() {
    LogisticSTheme(darkTheme = true) { DividerSection() }
}