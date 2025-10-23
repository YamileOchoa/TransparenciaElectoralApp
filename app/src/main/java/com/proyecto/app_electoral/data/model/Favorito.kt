package com.proyecto.app_electoral.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favoritos")
data class Favorito(
    @PrimaryKey val candidatoId: Int
)
