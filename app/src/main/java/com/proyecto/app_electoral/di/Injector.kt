package com.proyecto.app_electoral.di

import com.proyecto.app_electoral.data.network.ApiService
import com.proyecto.app_electoral.data.network.BASE_URL
import com.proyecto.app_electoral.data.repository.CandidatoRepository
// Importar Repositorio si vas a manejar otros datos (ej: Historial, Denuncias)
// import com.proyecto.app_electoral.data.repository.HistorialRepository

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

    /*
    // Si decides separarlos, podrías agregar:
    fun provideHistorialRepository(): HistorialRepository {
        return HistorialRepository(apiService)
    }
    // ... y así para Denuncias, Proyectos, Propuestas
    */
}