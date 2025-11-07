package com.proyecto.app_electoral.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.proyecto.app_electoral.data.network.model.Candidato
import com.proyecto.app_electoral.data.network.model.Denuncia
import com.proyecto.app_electoral.data.repository.StatsRepository
import com.proyecto.app_electoral.di.Injector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// --- Data Models for Stats ---

data class DenunciaStats(
    val totalCandidates: Int = 0,
    val candidatesWithDenuncias: Int = 0,
    val percentageClean: Float = 0.0f
)

data class BusyParty(val partyName: String, val candidateCount: Int)

data class StatsUiState(
    val lastUpdated: String = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date()),
    val denunciaStats: DenunciaStats = DenunciaStats(),
    val busyParties: List<BusyParty> = emptyList(),
    val mostViewed: List<Pair<String, Float>> = emptyList(), // Name, Progress (0.0 to 1.0)
    val distribution: Map<String, Int> = emptyMap(), // Region, Count
    val isLoading: Boolean = false,
    val error: String? = null
)

// ------------------------------------------------------------------
// VIEW MODEL
// ------------------------------------------------------------------

class StatsViewModel(
    private val repository: StatsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(StatsUiState(isLoading = true))
    val uiState: StateFlow<StatsUiState> = _uiState.asStateFlow()

    init {
        loadStatistics()
    }

    private fun loadStatistics() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            try {
                // 1. Fetch data from API
                val allCandidatos = repository.getAllCandidatos()
                val allDenuncias = repository.getAllDenuncias()

                // 2. Perform Transformations

                val denunciaStats = calculateDenunciaStats(allCandidatos, allDenuncias)
                val busyParties = calculateBusyParties(allCandidatos)
                val mostViewed = calculateMostViewed(allCandidatos)
                val distribution = calculateDistribution(allCandidatos)

                // 3. Update State
                val formattedDate = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())

                _uiState.update {
                    it.copy(
                        lastUpdated = formattedDate,
                        denunciaStats = denunciaStats,
                        busyParties = busyParties,
                        mostViewed = mostViewed,
                        distribution = distribution,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Error loading statistics: ${e.message}"
                    )
                }
            }
        }
    }

    // --- Calculation Logic ---

    private fun calculateDenunciaStats(candidatos: List<Candidato>, denuncias: List<Denuncia>): DenunciaStats {
        // [CORREGIDO] Usa el campo 'candidato' de Denuncia
        val candidatesIdWithDenuncias = denuncias.map { it.candidato }.toSet()
        val totalCandidates = candidatos.size
        val candidatesWithDenuncias = candidatesIdWithDenuncias.size

        val candidatesWithoutDenuncias = totalCandidates - candidatesWithDenuncias
        val percentageClean = if (totalCandidates > 0) candidatesWithoutDenuncias.toFloat() / totalCandidates else 0.0f

        return DenunciaStats(totalCandidates, candidatesWithDenuncias, percentageClean)
    }

    private fun calculateBusyParties(candidatos: List<Candidato>): List<BusyParty> {
        return candidatos
            .groupBy { it.partido }
            .map { (party, list) -> BusyParty(party, list.size) }
            .sortedByDescending { it.candidateCount }
            .take(5) // Top 5
    }

    private fun calculateMostViewed(candidatos: List<Candidato>): List<Pair<String, Float>> {
        // Usa el campo 'visitas' de Candidato
        val topCandidates = candidatos
            .sortedByDescending { it.visitas }
            .take(4) // Top 4 for the ProgressBar

        val maxVisitas = topCandidates.firstOrNull()?.visitas ?: 1

        return topCandidates.map { cand ->
            val progress = if (maxVisitas > 0) cand.visitas.toFloat() / maxVisitas.toFloat() else 0f
            cand.nombre to progress
        }
    }

    private fun calculateDistribution(candidatos: List<Candidato>): Map<String, Int> {
        // Usa el campo 'region' de Candidato
        return candidatos.groupBy { it.region }.mapValues { it.value.size }
    }
}

// ------------------------------------------------------------------
// VIEW MODEL FACTORY
// ------------------------------------------------------------------

class StatsViewModelFactory(private val repository: StatsRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StatsViewModel::class.java)) {
            return StatsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}