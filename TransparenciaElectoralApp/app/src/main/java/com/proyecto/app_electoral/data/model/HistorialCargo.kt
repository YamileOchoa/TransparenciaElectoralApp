package com.proyecto.app_electoral.data.model

data class HistorialCargo(
    val id: Int, // ID Primario

    // ForeignKey a Candidato (se serializa como el ID del Candidato)
    val candidato: Int,

    val cargo: String,
    val institucion: String,

    // DateField
    val fecha_inicio: String,

    // DateField(null=True, blank=True) -> String?
    val fecha_fin: String?,

    val descripcion: String,
    val fuente_url: String?
)