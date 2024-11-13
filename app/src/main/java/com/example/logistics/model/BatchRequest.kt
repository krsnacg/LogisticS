package com.example.logistics.model

import com.google.gson.annotations.SerializedName

data class BatchRequest(
    val code: String,
    val operativeStatus: String,
    @SerializedName("disponibilityState") val availabilityState: String,
    val securityState: String,
    val stock: Int,
    val expiredDate: String
)
