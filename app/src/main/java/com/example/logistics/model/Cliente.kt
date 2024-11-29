package com.example.logistics.model

data class Cliente(
    var id_cliente: String = "",
    var empresa: String = "",
    var representante: String = "",
    var dni: String = "",
    var email: String = "",
    var telefono: String = "",
    var direccion: String = "",
    var nombre_departamento: String = ""
)
