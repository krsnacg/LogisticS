package com.example.logistics.ui.Pedido

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.NavigateNext
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.logistics.R
import com.example.logistics.ui.theme.LogisticSTheme
import com.example.logistics.util.util_pedidos.NavigationBarRecepcionista
import com.example.logistics.util.util_pedidos.ScrollBarSecundario
import com.example.logistics.util.util_pedidos.TopBar

@Composable
fun RegistroPedido(
    modifier: Modifier = Modifier,
    ){
    val expandedEstadoEnvio = rememberSaveable { mutableSetOf(false)}
    val expandedMetodoPago = rememberSaveable { mutableSetOf(false) }
    var opcionSeleccionada = 0//rememberSaveable { mutableSetOf(1)}
    val listaPedidos = listOf(
        "Registrar Pedido",
        "Actualizar Pedido"
    )
    Scaffold (topBar = {
        TopBar(tituloPagina = stringResource(R.string.topbar_opcion2), modo = "Retroceder")
    },
        bottomBar = { NavigationBarRecepcionista(opcionSeleccionada = 2) }
    ){paddingValues ->
        LazyColumn (
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
            ){
            item { ScrollBarSecundario (
                listaElementos = listaPedidos,
                indiceInicial = opcionSeleccionada,
                onElementoSeleccionado = { opcionSeleccionada = it }
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
            item {
                OutlinedTextField(
                    value =  stringResource(id = R.string.ejemplo),
                    onValueChange = {},
                    label = {
                        Text(text = stringResource(id = R.string.campo_codigo_cotizacion))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
                )
            }
            item {
                OutlinedTextField(
                    value =  stringResource(id = R.string.ejemplo),
                    onValueChange = {},
                    label = {
                        Text(text = stringResource(id = R.string.campo_codigo_cliente))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
                )
            }
            //Estado de envío
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
                ){
                    ListItem(
                        headlineContent ={
                            Text(
                                text = stringResource(id = R.string.desplegable_estado_envio),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        },
                        trailingContent = {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        },
                        modifier = Modifier
                        //.clickable { expandedEstadoEnvio.value = true }
                    )
                    DropdownMenu(
                        expanded = false,//expandedEstadoEnvio.value,
                        onDismissRequest = { false },//expandedEstadoEnvio.value = false },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = stringResource(R.string.desplegable_estado_envio1),
                                ) },
                            onClick = {
                                //expandedEstadoEnvio.value = false
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = stringResource(R.string.desplegable_estado_envio2),
                                ) },
                            onClick = {
                                //expandedEstadoEnvio.value = false
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = stringResource(R.string.desplegable_estado_envio3),
                                ) },
                            onClick = {
                                //expandedEstadoEnvio.value = false
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = stringResource(R.string.desplegable_estado_envio4),
                                ) },
                            onClick = {
                                //expandedEstadoEnvio.value = false
                            }
                        )
                    }
                }
            }

            //Metodo de pago
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
                ){
                    ListItem(
                        headlineContent ={
                            Text(
                                text = stringResource(id = R.string.desplegable_metodo_pago),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        },
                        trailingContent = {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        },
                        modifier = Modifier
                        //.clickable { expandedMetodoPago.value = true }
                    )
                    DropdownMenu(
                        expanded = false,//expandedMetodoPago.value,
                        onDismissRequest = { false },//expandedMetodoPago.value = false },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = stringResource(R.string.desplegable_metodo_pago1),
                                ) },
                            onClick = {
                                //expandedMetodoPago.value = false
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = stringResource(R.string.desplegable_metodo_pago2),
                                ) },
                            onClick = {
                                //expandedMetodoPago.value = false
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = stringResource(R.string.desplegable_metodo_pago3),
                                ) },
                            onClick = {
                                //expandedMetodoPago.value = false
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = stringResource(R.string.desplegable_metodo_pago4),
                                ) },
                            onClick = {
                                //expandedMetodoPago.value = false
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = stringResource(R.string.desplegable_metodo_pago5),
                                ) },
                            onClick = {
                                //expandedMetodoPago.value = false
                            }
                        )
                    }
                }
            }
            
            item {
                OutlinedTextField(
                    value =  stringResource(id = R.string.ejemplo),
                    onValueChange = {},
                    label = {
                        Text(text = stringResource(id = R.string.campo_fecha_entrega))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
                )
            }
            item {
                OutlinedTextField(
                    value =  stringResource(id = R.string.ejemplo),
                    onValueChange = {},
                    label = {
                        Text(text = stringResource(id = R.string.campo_fecha_emision))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
                )
            }
            //Boton
            item {

                Spacer(modifier = Modifier.padding(5.dp))

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Button(
                        onClick = { },
                        modifier = Modifier.padding(end = 15.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AddShoppingCart,
                            contentDescription = null
                        )
                        Text(text = stringResource(id = R.string.boton_registrar_pedido))
                    }
                }
            }



            
        }
    }
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun RegistroPedidoLightPreview() {
    LogisticSTheme (darkTheme = false) { RegistroPedido() }
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun RegistroPedidoDarkPreview() {
    LogisticSTheme (darkTheme = true) { RegistroPedido() }
}