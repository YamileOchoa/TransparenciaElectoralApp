package com.proyecto.app_electoral.data.model

import com.google.gson.annotations.SerializedName

data class DatosElectorales(
    @SerializedName("Candidatos")
    val candidatos: List<Candidato>,

    @SerializedName("Historial Cargos")
    val historialCargos: List<HistorialCargo>,

    @SerializedName("Denuncias")
    val denuncias: List<Denuncia>,

    @SerializedName("Proyectos")
    val proyectos: List<Proyecto>,

    @SerializedName("Propuestas")
    val propuestas: List<Propuesta>
)
