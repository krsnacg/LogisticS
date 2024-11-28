package com.example.logistics.util.util_pedidos

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.logistics.R
import com.example.logistics.ui.theme.LogisticSTheme

@Composable
fun RegistroPedido(
    modifier: Modifier = Modifier,
    ){
    Scaffold (topBar = {
        TopBar(tituloPagina = stringResource(R.string.topbar_opcion2), modo = "Retroceder")},
        bottomBar = { NavigationBarRecepcionista(opcionSeleccionada = 2)}
    ){paddingValues ->
        LazyColumn (
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
            ){
            item {
                Text(
                    text = stringResource(id = R.string.topbar_opcion2),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = modifier.padding(start = 20.dp, end = 20.dp, bottom = 40.dp, top = 30.dp)
                )
            }
            item {
                OutlinedTextField(
                    value =  stringResource(id = R.string.ejemplo),
                    onValueChange = {},
                    label = {
                        Text(text = stringResource(id = R.string.campo_codigo_pedido))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun RegistroClienteLightPreview() {
    LogisticSTheme (darkTheme = false) { RegistroPedido() }
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun RegistroClienteDarkPreview() {
    LogisticSTheme (darkTheme = true) { RegistroPedido() }
}