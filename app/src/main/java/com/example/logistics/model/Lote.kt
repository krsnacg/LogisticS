package com.example.logistics.model

data class Lote(
    val codigoLote: String = "",
    val estadoOperativo: String = "",
    val estadoDisponibilidad: String = "",
    val estadoSeguridad: String = "",
    val nombreProducto: String = "",
    val stock: String = "",
    val fechaVencimiento: String = ""
)

