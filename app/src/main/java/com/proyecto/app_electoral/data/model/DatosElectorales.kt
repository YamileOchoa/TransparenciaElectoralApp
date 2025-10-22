package com.proyecto.app_electoral.data.model

import com.google.gson.annotations.SerializedName

// Los nombres de los campos ahora coinciden con el JSON usando @SerializedName
data class DatosElectorales(
    @SerializedName("Candidatos") val candidatos: List<CandidatoDto>?,
    @SerializedName("Propuestas") val propuestas: List<PropuestaDto>?,
    @SerializedName("Denuncias") val denuncias: List<DenunciaDto>?,
    @SerializedName("Historial Cargos") val historialCargos: List<HistorialCargoDto>?
)

// DTOs para parsear el JSON. Contienen `candidatoId` para la relación.

data class CandidatoDto(
    val id: Int,
    val nombre: String,
    val partido: String,
    val cargo: String,
    val region: String,
    val foto_url: String,
    val dni: Long,
    val nacimiento: String,
    val biografia: String,
    val experiencia: Int,
    val estado: String
)

data class PropuestaDto(
    @SerializedName("candidato_id") val candidatoId: Int,
    val titulo: String,
    val descripcion: String,
    val categoria: String,
    val prioridad: String
)

data class DenunciaDto(
    @SerializedName("candidato_id") val candidatoId: Int,
    val titulo: String,
    val descripcion: String,
    val expediente: String,
    val delito: String,
    val fecha_denuncia: String,
    val estado: String,
    val fuente_url: String
)

data class HistorialCargoDto(
    @SerializedName("candidato_id") val candidatoId: Int,
    val cargo: String,
    val institucion: String,
    val fecha_inicio: String,
    val fecha_fin: String,
    val descripcion: String
)
