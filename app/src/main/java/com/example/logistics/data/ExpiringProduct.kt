package com.example.logistics.data

import java.util.Date

data class ExpiringProduct(
    val productId: String,
    val nombre: String,
    val concentracion: String,
    val loteId: String,
    val fechaExpiracion: String
)
