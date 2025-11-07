package com.proyecto.app_electoral.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.proyecto.app_electoral.data.network.model.Candidato
import com.proyecto.app_electoral.data.repository.CandidatoRepository
import com.proyecto.app_electoral.di.Injector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// ------------------------------------------------------------------
// DATA CLASS DE ESTADO
// ------------------------------------------------------------------

/**
 * Representa el estado actual de la pantalla de inicio (Home).
 */
data class HomeUiState(
    val candidates: List<Candidato> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

// ------------------------------------------------------------------
// VIEW MODEL
// ------------------------------------------------------------------

/**
 * ViewModel para gestionar los datos de la Home Screen y proveer datos base para la Search Screen.
 */
class HomeViewModel(
    private val repository: CandidatoRepository = Injector.provideCandidatoRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState(isLoading = true))
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadCandidates()
    }

    /**
     * Función que realiza la llamada asíncrona a la API a través del repositorio.
     */
    fun loadCandidates() {
        _uiState.value = _uiState.value.copy(isLoading = true, error = null)

        viewModelScope.launch {
            try {
                val candidates = repository.getCandidatos()
                _uiState.value = _uiState.value.copy(
                    candidates = candidates,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Error al cargar candidatos: ${e.message}",
                    isLoading = false
                )
                e.printStackTrace()
            }
        }
    }

    // ------------------------------------------------------------------
    // LÓGICA DE FILTRADO PARA LA BÚSQUEDA (SearchScreen)
    // ------------------------------------------------------------------

    /**
     * 🔍 Filtra la lista completa de candidatos almacenada en el estado local.
     * La SearchScreen debe llamar a esta función cuando el texto de búsqueda cambie.
     *
     * @param query El texto ingresado por el usuario.
     * @return Una lista de Candidato filtrada por nombre, partido o región.
     */
    fun filterCandidates(query: String): List<Candidato> {
        val currentList = _uiState.value.candidates

        if (query.isBlank()) {
            return currentList // Devuelve la lista completa si la consulta está vacía.
        }

        val normalizedQuery = query.trim().lowercase()

        return currentList.filter { candidato ->
            // El filtro verifica si la consulta coincide en Nombre, Partido o Región.
            candidato.nombre.lowercase().contains(normalizedQuery) ||
                    candidato.partido.lowercase().contains(normalizedQuery) ||
                    candidato.region.lowercase().contains(normalizedQuery)
        }
    }
}

// ------------------------------------------------------------------
// VIEW MODEL FACTORY (Se mantiene sin cambios, es correcto)
// ------------------------------------------------------------------

/**
 * Factory para crear instancias del HomeViewModel.
 */
class HomeViewModelFactory(private val repository: CandidatoRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}