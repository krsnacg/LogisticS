package com.example.logistics.model

data class ProductRequest(
    val code: String,
    val category: String,
    val type: String,
    val name: String,
    val price: Double,
    val concentracion: String,
    val presentation: String,
    val description: String,
    val stock: Int,
    val lots: List<LoteRequest>
)
