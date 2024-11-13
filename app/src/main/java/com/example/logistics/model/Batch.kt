package com.example.logistics.model

import java.util.Date

data class Batch(
    val codigoLote: String = "",
    val estadoOperativo: String = "",
    val estadoDisponibilidad: String = "",
    val estadoSeguridad: String = "",
    val nombreProducto: String = "",
    val stock: String = "",
    val fechaVencimiento: String = ""
)

