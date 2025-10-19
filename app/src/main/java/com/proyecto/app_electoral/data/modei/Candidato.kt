package com.proyecto.app_electoral.data.modei
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "candidatos")
class Candidato (
    @PrimaryKey val id: Int,
    val nombre: String,
    val partido: String,
    val cargoActual: String,
    val region: String,
    val fotoUrl: String,
    val biografia: String,
    val email: String? = null,
    val telefono : String? = null,
    val facebook : String? = null,
    val twitter : String? = null,
    val instagram : String? = null,
){

}