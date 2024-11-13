package com.example.logistics.model

import com.google.gson.annotations.SerializedName

data class ProductRequest(
    val code: String = "",
    val category: String = "",
    val type: String = "",
    val name: String = "",
    val price: Double = 0.0,
    @SerializedName("concentracion") val concentration: String = "",
    val presentation: String = "",
    val description: String = "",
    @SerializedName("lots") val batches: List<BatchRequest>
)
