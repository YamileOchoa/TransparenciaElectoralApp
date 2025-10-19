package com.proyecto.app_electoral.data.modei

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cargos_historicos")
data class CargoHistorico(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val candidatoId: Int,
    val titulo: String,   // Ej: “Miembro del consejo municipal”
    val periodo: String   // Ej: “2018 - 2022”
)