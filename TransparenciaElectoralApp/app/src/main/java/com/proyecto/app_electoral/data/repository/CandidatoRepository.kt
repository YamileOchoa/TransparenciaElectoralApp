package com.proyecto.app_electoral.data.repository

import com.proyecto.app_electoral.data.network.ApiService
import com.proyecto.app_electoral.data.network.model.Candidato
import com.proyecto.app_electoral.data.network.model.Denuncia
import com.proyecto.app_electoral.data.network.model.HistorialCargo
import com.proyecto.app_electoral.data.network.model.Propuesta
import com.proyecto.app_electoral.data.network.model.Proyecto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repositorio para manejar la lógica de negocio y datos de los candidatos.
 * Llama al ApiService y realiza el filtrado de datos para el perfil individual.
 */
class CandidatoRepository(private val apiService: ApiService) {

    suspend fun getCandidatos(): List<Candidato> = withContext(Dispatchers.IO) {
        return@withContext apiService.getCandidatos()
    }

    /**
     * Obtiene el perfil completo de un candidato junto con toda su información relacionada.
     *
     * @param candidatoId El ID del candidato cuyo perfil se desea obtener.
     * @return Un objeto CandidateProfileData que contiene todos los datos filtrados.
     */
    suspend fun getCandidateProfile(candidatoId: Int): CandidateProfileData = withContext(Dispatchers.IO) {

        // 1. Obtener todos los datos de las listas
        val allCandidatos = apiService.getCandidatos()
        val allHistorial = apiService.getHistorialCargos()
        val allDenuncias = apiService.getDenuncias()
        val allProyectos = apiService.getProyectos()
        val allPropuestas = apiService.getPropuestas()

        // 2. Buscar el candidato principal
        val candidato = allCandidatos.find { it.id == candidatoId }
            ?: throw NoSuchElementException("Candidato con ID $candidatoId no encontrado.")

        // 3. Filtrar datos relacionados por el ID del candidato
        val historialFiltrado = allHistorial.filter { it.candidato == candidatoId }
        val denunciasFiltradas = allDenuncias.filter { it.candidato == candidatoId }
        val proyectosFiltrados = allProyectos.filter { it.candidato == candidatoId }
        val propuestasFiltradas = allPropuestas.filter { it.candidato == candidatoId }

        // 4. Devolver un objeto de datos consolidado
        return@withContext CandidateProfileData(
            candidato = candidato,
            historialCargos = historialFiltrado, // ⬅️ CORREGIDO el typo
            denuncias = denunciasFiltradas,
            proyectos = proyectosFiltrados,
            propuestas = propuestasFiltradas
        )
    }
}

data class CandidateProfileData(
    val candidato: Candidato,
    val historialCargos: List<HistorialCargo>,
    val denuncias: List<Denuncia>,
    val proyectos: List<Proyecto>,
    val propuestas: List<Propuesta>
)