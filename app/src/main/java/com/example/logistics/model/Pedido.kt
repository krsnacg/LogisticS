package com.example.logistics.model

import java.util.Date

data class Pedido(
    var idpedido: String  = "",
    var idcotizacion: String = "",
    var idcliente: String = "",
    var idmetodopago: Int? = 0,
    var idestadoenvio: Int? = 0,
    var idempleado: String = "",
    var fechaentrega: Date?,
    var fechaemision: Date
)
