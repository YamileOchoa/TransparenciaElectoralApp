package com.proyecto.app_electoral.data.repository

import android.content.Context
import android.util.Log
import com.proyecto.app_electoral.data.db.AppDatabase
import com.proyecto.app_electoral.data.model.Candidato
import com.proyecto.app_electoral.data.network.ApiService
import kotlinx.coroutines.flow.Flow

class CandidatoRepository(
    private val context: Context,
    private val db: AppDatabase,
    private val apiService: ApiService
) {

    suspend fun refreshCandidatos() {
        try {
            val candidatosFromApi = apiService.getCandidatos()
            db.candidatoDao().insertAll(candidatosFromApi)
        } catch (e: Exception) {
            Log.e("CandidatoRepository", "Error al refrescar desde la API", e)
        }
    }

    fun getCandidatos(): Flow<List<Candidato>> = db.candidatoDao().getAllCandidatos()

    fun getCandidato(id: Int): Flow<Candidato?> = db.candidatoDao().getCandidatoById(id)

    suspend fun getMasBuscados(): List<Candidato> {
        return try {
            apiService.getMasBuscados()
        } catch (e: Exception) {
            Log.e("CandidatoRepository", "Error al obtener los más buscados", e)
            emptyList()
        }
    }

    suspend fun incrementarVisitas(id: Int) {
        try {
            apiService.incrementarVisita(id)
        } catch (e: Exception) {
            Log.e("CandidatoRepository", "Error al incrementar visita", e)
        }
    }
}
