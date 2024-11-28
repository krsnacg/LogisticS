package com.example.logistics.model

data class DetalleCotizacion(
    var idcotizacion: String = "",
    var producto: String = "",
    var cantidad: Int = 0,
    var total: Float = 0f,
    var concentracion: String = "",
)