package com.proyecto.app_electoral.data.repository

import com.proyecto.app_electoral.data.network.ApiService
import com.proyecto.app_electoral.data.network.model.Candidato
import com.proyecto.app_electoral.data.network.model.Denuncia
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository for fetching raw data required for statistical calculations.
 */
class StatsRepository(private val apiService: ApiService) {

    suspend fun getAllCandidatos(): List<Candidato> = withContext(Dispatchers.IO) {
        return@withContext apiService.getCandidatos()
    }

    suspend fun getAllDenuncias(): List<Denuncia> = withContext(Dispatchers.IO) {
        return@withContext apiService.getDenuncias()
    }
}