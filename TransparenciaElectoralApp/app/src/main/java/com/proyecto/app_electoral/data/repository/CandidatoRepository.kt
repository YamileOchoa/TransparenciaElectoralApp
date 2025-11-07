package com.proyecto.app_electoral.data.repository

import com.proyecto.app_electoral.data.model.Candidato
import com.proyecto.app_electoral.data.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CandidatoRepository(private val apiService: ApiService) {

    /**
     * Obtiene la lista de candidatos desde el endpoint de Django.
     */
    suspend fun getCandidatos(): List<Candidato> = withContext(Dispatchers.IO) {
        // Llama a la función suspend de Retrofit para obtener la lista
        return@withContext apiService.getCandidatos()
    }

    /*
    // Opcional: Si quieres centralizar otros datos de Candidato aquí:
    suspend fun getDenuncias(): List<Denuncia> = withContext(Dispatchers.IO) {
        return@withContext apiService.getDenuncias()
    }
    // ...
    */
}