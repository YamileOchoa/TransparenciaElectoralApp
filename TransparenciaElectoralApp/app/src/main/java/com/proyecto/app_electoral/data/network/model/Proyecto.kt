package com.proyecto.app_electoral.data.network.model

data class Proyecto(
    val id: Int, // ID Primario
    val candidato: Int, // ForeignKey a Candidato

    val titulo: String,
    val descripcion: String,
    val categoria: String,
    val fuente_url: String?, // URLField(null=True, blank=True)

    // El campo de opciones (TextChoices) se serializa como String (ej: "Sin iniciar")
    val estado: String
)