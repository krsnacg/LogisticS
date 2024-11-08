package com.example.logistics.model

data class LoteRequest(
    val code: String,
    val operativeStatus: String,
    val disponibilityState: String,
    val securityState: String,
    val stock: Int,
    val expiredDate: String
)
