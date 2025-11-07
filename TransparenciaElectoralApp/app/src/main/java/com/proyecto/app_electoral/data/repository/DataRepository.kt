package com.proyecto.app_electoral.data.repository

import com.proyecto.app_electoral.data.model.*
import com.proyecto.app_electoral.data.network.ApiService
import com.proyecto.app_electoral.data.network.model.Candidato
import com.proyecto.app_electoral.data.network.model.Denuncia
import com.proyecto.app_electoral.data.network.model.HistorialCargo
import com.proyecto.app_electoral.data.network.model.Propuesta
import com.proyecto.app_electoral.data.network.model.Proyecto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// Este repositorio manejará la obtención de datos de todos los modelos
class DataRepository(private val apiService: ApiService) {

    // 1. Obtiene la lista de Candidatos
    suspend fun getCandidatos(): List<Candidato> = withContext(Dispatchers.IO) {
        // Llama a la función suspend de Retrofit para obtener la lista
        return@withContext apiService.getCandidatos()
    }

    // 2. Obtiene la lista de Historial de Cargos
    suspend fun getHistorialCargos(): List<HistorialCargo> = withContext(Dispatchers.IO) {
        return@withContext apiService.getHistorialCargos()
    }

    // 3. Obtiene la lista de Denuncias
    suspend fun getDenuncias(): List<Denuncia> = withContext(Dispatchers.IO) {
        return@withContext apiService.getDenuncias()
    }

    // 4. Obtiene la lista de Proyectos
    suspend fun getProyectos(): List<Proyecto> = withContext(Dispatchers.IO) {
        return@withContext apiService.getProyectos()
    }

    // 5. Obtiene la lista de Propuestas
    suspend fun getPropuestas(): List<Propuesta> = withContext(Dispatchers.IO) {
        return@withContext apiService.getPropuestas()
    }
}