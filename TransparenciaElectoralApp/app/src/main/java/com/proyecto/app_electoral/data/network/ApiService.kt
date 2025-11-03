package com.proyecto.app_electoral.data.network

import com.proyecto.app_electoral.data.model.Candidato
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.POST

interface ApiService {
    @GET("candidatos/")
    suspend fun getCandidatos(): List<Candidato>

    @GET("candidatos/{id}/")
    suspend fun getCandidato(@Path("id") id: Int): Candidato

    @GET("candidatos/mas-buscados/")
    suspend fun getMasBuscados(): List<Candidato>

    @POST("candidatos/{id}/incrementar-visita/")
    suspend fun incrementarVisita(@Path("id") id: Int): Response<Void>
}
