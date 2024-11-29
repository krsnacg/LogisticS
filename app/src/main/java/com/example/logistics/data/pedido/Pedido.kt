package com.example.logistics.data.pedido

import java.time.LocalDate

data class Pedido (
    val idpedido: String,
    val idcotizacion: String,
    val idcliente: String,
    val idmetodopago: Int,
    val idestadoenvio: Int,
    val edempleado: String,
    val fechaentrega: String,//LocalDate,
    val fechaemision: String//LocalDate
)