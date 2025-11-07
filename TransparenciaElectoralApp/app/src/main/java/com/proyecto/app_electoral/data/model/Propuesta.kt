package com.proyecto.app_electoral.data.model

data class Propuesta(
    val id: Int, // ID Primario
    val candidato: Int, // ForeignKey a Candidato

    val titulo: String,
    val descripcion: String,
    val categoria: String,

    // El campo de opciones (TextChoices) se serializa como String (ej: "Media")
    val prioridad: String,

    val fuente_url: String? // URLField(null=True, blank=True)
)