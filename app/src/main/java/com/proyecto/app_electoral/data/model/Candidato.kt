package com.proyecto.app_electoral.data.model
import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "candidatos")
data class Candidato(
    val id: Int,
    val nombre: String,
    val partido: String,
    val cargo: String,
    val region: String,
    @SerializedName("foto_url")val fotoUrl: String,
    val dni: Long,
    val nacimiento: String,
    val biografia: String,
    val experiencia: Int,
    val estado: String
)
{

}