package com.proyecto.app_electoral.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "candidatos")
data class Candidato(
    @PrimaryKey val id: Int,
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
    val historial: List<HistorialCargo> = emptyList(),
    val propuestas: List<Propuesta> = emptyList(),
    val denuncias: List<Denuncia> = emptyList(),
    val visitas: Int = 0
)
