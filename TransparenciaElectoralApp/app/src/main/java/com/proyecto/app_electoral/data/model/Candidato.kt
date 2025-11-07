package com.proyecto.app_electoral.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "candidatos")
data class Candidato(
    @PrimaryKey val id: Int,
    val nombre: String,
    val partido: String,
    val biografia: String,
    val experiencia: Int,
    val visitas: Int = 0,
    @SerializedName("foto_url") val fotoUrl: String?,
    val region: String?,
    val estado: String?,
    val dni: String?,
    val nacimiento: String?,
    val historial: List<HistorialCargo>? = emptyList(),
    val propuestas: List<Propuesta>? = emptyList(),
    val denuncias: List<Denuncia>? = emptyList()
)
