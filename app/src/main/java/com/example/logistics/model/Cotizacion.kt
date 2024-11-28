package com.example.logistics.model

data class Cotizacion(
    var idcotizacion: String = "",
    var idempleado: String = "",
    var estado: String = "",
    var nombrecliente: String = "",
    var montoproducto: Float = 0f,
    var fechaemision: String = "",
    var email: String = "",
    var montoimpuesto: Float = 0f,
    var montototal: Float = 0f,
    var departamento: String = "",
    var detalles: List<DetalleCotizacion> = emptyList(),
    var dni: String = ""
)
