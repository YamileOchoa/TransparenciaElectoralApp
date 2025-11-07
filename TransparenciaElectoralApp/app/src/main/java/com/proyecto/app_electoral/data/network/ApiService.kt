package com.proyecto.app_electoral.data.network

import com.proyecto.app_electoral.data.model.Candidato
import com.proyecto.app_electoral.data.model.Denuncia
import com.proyecto.app_electoral.data.model.HistorialCargo
import com.proyecto.app_electoral.data.model.Propuesta
import com.proyecto.app_electoral.data.model.Proyecto
import retrofit2.http.GET

// **IMPORTANTE:** Usar 10.0.2.2 para acceder a localhost desde el emulador de Android.
const val BASE_URL = "http://10.0.2.2:8000/api/"

interface ApiService {

    /**
     * Obtiene la lista completa de candidatos.
     * Endpoint: /api/candidatos/
     */
    @GET("candidatos/")
    suspend fun getCandidatos(): List<Candidato>

    /**
     * Obtiene la lista de historial de cargos.
     * Endpoint: /api/historial_cargos/
     */
    @GET("historial_cargos/")
    suspend fun getHistorialCargos(): List<HistorialCargo>

    /**
     * Obtiene la lista de denuncias.
     * Endpoint: /api/denuncias/
     */
    @GET("denuncias/")
    suspend fun getDenuncias(): List<Denuncia>

    /**
     * Obtiene la lista de proyectos.
     * Endpoint: /api/proyectos/
     */
    @GET("proyectos/")
    suspend fun getProyectos(): List<Proyecto>

    /**
     * Obtiene la lista de propuestas.
     * Endpoint: /api/propuestas/
     */
    @GET("propuestas/")
    suspend fun getPropuestas(): List<Propuesta>

    // Nota: Todas las funciones son 'suspend' para ser llamadas desde Coroutines (ViewModel).
}