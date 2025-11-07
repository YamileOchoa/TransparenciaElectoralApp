package com.proyecto.app_electoral.data.model

data class Candidato(
    // ID Primario de Django
    val id: Int,

    val nombre: String,
    val partido: String,
    val biografia: String,
    val visitas: Int, // PositiveIntegerField es Int en Kotlin

    // URLField(null=True, blank=True) -> String?
    val foto_url: String?,
    val dni: String?,
    val region: String,

    // DateField(null=True, blank=True) -> String? (formato "YYYY-MM-DD")
    val nacimiento: String?,
    val estado: String,
    val profesion: String?, // TextField(null=True, blank=True)
    val fuente_url: String?
)