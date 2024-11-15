package com.example.logistics.data

data class EmpleadoDto(
    val idempleado: String,
    val nombre: String,
    val apellido: String,
    val telefono: String,
    val dni: String,
    val direccion: String,
    val fechaNacimiento: String,  // Ajusta si es necesario
    val genero: String,
    val cargo: String
)
