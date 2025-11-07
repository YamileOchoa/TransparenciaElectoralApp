package com.proyecto.app_electoral.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.proyecto.app_electoral.data.repository.CandidateProfileData
import com.proyecto.app_electoral.data.repository.CandidatoRepository
import com.proyecto.app_electoral.di.Injector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.proyecto.app_electoral.data.network.model.Candidato

// ------------------------------------------------------------------
// DATA CLASS DE ESTADO
// ------------------------------------------------------------------

/**
 * Representa el estado actual de la pantalla de Comparación.
 */
data class ComparisonUiState(
    // Nullable para manejar el caso donde aún no se ha seleccionado o cargado
    val candidate1Profile: CandidateProfileData? = null,
    val candidate2Profile: CandidateProfileData? = null,
    val isLoading: Boolean = false,
    val error: String? = null
) {
    // Helper para obtener el Candidato base (para la tarjeta superior)
    val candidate1: Candidato?
        get() = candidate1Profile?.candidato

    val candidate2: Candidato?
        get() = candidate2Profile?.candidato
}

// ------------------------------------------------------------------
// VIEW MODEL
// ------------------------------------------------------------------

/**
 * ViewModel para gestionar la carga y el estado de la Comparación de dos candidatos.
 */
class ComparisonViewModel(
    private val repository: CandidatoRepository = Injector.provideCandidatoRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(ComparisonUiState(isLoading = false))
    val uiState: StateFlow<ComparisonUiState> = _uiState.asStateFlow()

    /**
     * Carga los perfiles completos de los candidatos.
     * Si un ID es null, solo carga el otro (ej. cuando solo se selecciona uno inicialmente).
     *
     * @param id1 ID del primer candidato (puede ser nulo si no está seleccionado).
     * @param id2 ID del segundo candidato (puede ser nulo si no está seleccionado).
     */
    fun loadComparisonData(id1: Int?, id2: Int?) {
        if (id1 == null && id2 == null) return

        _uiState.value = _uiState.value.copy(isLoading = true, error = null)

        viewModelScope.launch {
            try {
                // Carga del Candidato 1
                val profile1 = id1?.let { repository.getCandidateProfile(it) }

                // Carga del Candidato 2
                val profile2 = id2?.let { repository.getCandidateProfile(it) }

                _uiState.value = _uiState.value.copy(
                    candidate1Profile = profile1,
                    candidate2Profile = profile2,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Error al cargar perfiles: ${e.message}",
                    isLoading = false
                )
                e.printStackTrace()
            }
        }
    }
}

// ------------------------------------------------------------------
// VIEW MODEL FACTORY
// ------------------------------------------------------------------

/**
 * Factory para crear instancias del ComparisonViewModel.
 */
class ComparisonViewModelFactory(private val repository: CandidatoRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ComparisonViewModel::class.java)) {
            return ComparisonViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}