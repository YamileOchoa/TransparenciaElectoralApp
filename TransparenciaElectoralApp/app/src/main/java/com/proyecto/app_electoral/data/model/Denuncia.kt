package com.proyecto.app_electoral.data.model

data class Denuncia(
    val id: Int, // ID Primario
    val candidato: Int, // ForeignKey a Candidato

    val titulo: String,
    val descripcion: String,
    val expediente: String,
    val delito: String,

    // DateField
    val fecha_denuncia: String,

    val estado: String,

    // URLField (no es nullable en Django)
    val fuente_url: String
)