package com.proyecto.app_electoral.data.model

import androidx.room.Entity

@Entity(tableName = "cargos_historicos")
data class HistorialCargo(
    val id: Int,
    val candidato_id: Int,
    val cargo: String,
    val institucion: String,
    val fecha_inicio: String,
    val fecha_fin: String,
    val descripcion: String
)
