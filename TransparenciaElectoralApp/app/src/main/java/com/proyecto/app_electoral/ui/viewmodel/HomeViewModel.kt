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

/**
 * Representa el estado actual de la pantalla de inicio (Home).
 */
data class HomeUiState(
    val candidates: List<Candidato> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

/**
 * ViewModel para gestionar los datos de la Home Screen.
 * Utiliza StateFlow para exponer el estado reactivamente a Compose.
 */
class HomeViewModel(
    private val repository: CandidatoRepository = Injector.provideCandidatoRepository()
) : ViewModel() {

    // MutableStateFlow interno para actualizar el estado.
    private val _uiState = MutableStateFlow(HomeUiState(isLoading = true))

    // StateFlow público e inmutable que la UI consume.
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        // Al iniciar el ViewModel, carga los candidatos.
        loadCandidates()
    }

    /**
     * Función que realiza la llamada asíncrona a la API a través del repositorio.
     */
    fun loadCandidates() {
        // Indica que la carga está en curso.
        _uiState.value = _uiState.value.copy(isLoading = true, error = null)

        viewModelScope.launch {
            try {
                val candidates = repository.getCandidatos()
                // Éxito: Actualiza la lista de candidatos y desactiva la carga.
                _uiState.value = _uiState.value.copy(
                    candidates = candidates,
                    isLoading = false
                )
            } catch (e: Exception) {
                // Error: Captura la excepción y muestra un mensaje de error.
                _uiState.value = _uiState.value.copy(
                    error = "Error al cargar candidatos: ${e.message}",
                    isLoading = false
                )
                e.printStackTrace()
            }
        }
    }
}

/**
 * Factory para crear instancias del HomeViewModel.
 * Esto es necesario cuando el ViewModel tiene dependencias (como el Repositorio).
 *
 * NOTA: Para un proyecto con Hilt/Koin, este patrón sería reemplazado por la inyección.
 * Por ahora, lo hacemos manual.
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