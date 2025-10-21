package com.proyecto.app_electoral.data.model

data class Candidato(
    val id: Int,
    val nombre: String,
    val partido: String,
    val cargo: String,
    val region: String,
    val foto_url: String,
    val dni: Long,
    val nacimiento: String,
    val biografia: String,
    val experiencia: Int,
    val estado: String,
    // Relaciones con otras tablas (opcionalmente cargadas)
    val historial: List<HistorialCargo> = emptyList(),
    val propuestas: List<Propuesta> = emptyList(),
    val denuncias: List<Denuncia> = emptyList()
)
