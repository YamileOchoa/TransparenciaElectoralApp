package com.proyecto.app_electoral.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "proyectos")
data class Proyecto(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val candidato_id: Int,
    val numero_proyecto: String,
    val titulo: String,
    val descripcion: String,
    val fecha_presentacion: String,
    val estado: String,
    val tema: String,
    val fuente_url: String
)

