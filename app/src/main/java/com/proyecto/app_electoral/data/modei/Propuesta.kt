package com.proyecto.app_electoral.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "propuestas")
data class Propuesta(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val candidatoId: Int,
    val descripcion: String
)
