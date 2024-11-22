package com.example.logistics.data

import com.example.logistics.model.Product
import com.example.logistics.model.ProductResponse

fun ProductResponse.toProduct(): Product {
    return Product(
        codigo = code,
        nombreProducto = name ?: "Sin Nombre",
        categoria = category.name,
        tipo = formaFarmaceutica.forma,
        precio = price.toString() ?: "0",
        concentracion = concentracion ?: "Sin Concentración",
        presentacion = presentation ?: "",
        descripcion = description ?: "",
        cantidad = ""
    )
}