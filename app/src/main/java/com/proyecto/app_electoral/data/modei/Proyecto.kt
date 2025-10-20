package com.proyecto.app_electoral.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "proyectos")
data class Proyecto(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val candidatoId: Int,
    val titulo: String,
    val descripcion: String
)
