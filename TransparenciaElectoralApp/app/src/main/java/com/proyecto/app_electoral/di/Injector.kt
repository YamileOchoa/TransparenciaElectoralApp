package com.proyecto.app_electoral.di

import com.proyecto.app_electoral.data.network.ApiService
import com.proyecto.app_electoral.data.network.BASE_URL
import com.proyecto.app_electoral.data.repository.CandidatoRepository
import com.proyecto.app_electoral.data.repository.StatsRepository // [NUEVO] Importamos el repositorio de estadísticas

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Injector {

    // 1. Configuración de Retrofit
    private val retrofit by lazy {
        Retrofit.Builder()
            // Usamos la BASE_URL ajustada para el emulador (10.0.2.2)
            .baseUrl(BASE_URL)
            // Usamos Gson para el parseo de JSON
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // 2. Creación del servicio de la API
    private val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    // 3. Proveedores de Repositorios (Lógica de Negocio y Datos)

    // Proveedor del Repositorio de Candidatos
    fun provideCandidatoRepository(): CandidatoRepository {
        // Inyecta el servicio de la API al Repositorio
        return CandidatoRepository(apiService)
    }

    // [NUEVO] Proveedor del Repositorio de Estadísticas
    fun provideStatsRepository(): StatsRepository {
        // Inyecta el servicio de la API al nuevo Repositorio de Estadísticas
        return StatsRepository(apiService)
    }
}