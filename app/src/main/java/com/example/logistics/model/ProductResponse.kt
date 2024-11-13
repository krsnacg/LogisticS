package com.example.logistics.model

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("idProducto") val code: String,
    @SerializedName("idCategoria") val category: String?,
    @SerializedName("idFormaFarmaceutica") val formaFarmaceutica: FormaFarmaceutica,
    @SerializedName("idAlmacen") val almacen: String,
    @SerializedName("nombre") val name: String,
    @SerializedName("precio") val price: Float,
    @SerializedName("concentracion") val concentracion: String,
    @SerializedName("presentacion") val presentation: String,
    @SerializedName("descripcion") val description: String
)

data class FormaFarmaceutica(
    @SerializedName("id_forma_farmaceutica") val id: String,
    @SerializedName("forma") val forma: String
)