package com.proyecto.app_electoral.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "denuncias")
data class Denuncia(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val candidato_id: Int,
    val titulo: String,
    val descripcion: String,
    val expediente: String,
    val delito: String,
    val fecha_denuncia: String,
    val estado: String,
    val fuente_url: String
)

