package com.proyecto.app_electoral.data.model

data class Denuncia(
    val titulo: String,
    val descripcion: String,
    val expediente: String,
    val delito: String,
    val fecha_denuncia: String,
    val estado: String,
    val fuente_url: String
)
