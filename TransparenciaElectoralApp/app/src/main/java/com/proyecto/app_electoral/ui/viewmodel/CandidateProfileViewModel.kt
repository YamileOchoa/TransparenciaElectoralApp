package com.proyecto.app_electoral.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyecto.app_electoral.data.repository.CandidateProfileData
import com.proyecto.app_electoral.data.repository.CandidatoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Define los posibles estados de la UI para la pantalla de perfil del candidato.
 */
sealed class CandidateProfileUiState {
    object Loading : CandidateProfileUiState()
    data class Success(val data: CandidateProfileData) : CandidateProfileUiState()
    data class Error(val message: String) : CandidateProfileUiState()
}

/**
 * ViewModel para gestionar y exponer los datos del perfil de un candidato a la UI.
 *
 * Recibe el ID del candidato y el Repositorio inyectados.
 */
class CandidateProfileViewModel(
    private val candidatoId: Int,
    private val repository: CandidatoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<CandidateProfileUiState>(CandidateProfileUiState.Loading)
    val uiState: StateFlow<CandidateProfileUiState> = _uiState.asStateFlow()

    init {
        loadCandidateProfile()
    }

    private fun loadCandidateProfile() {
        viewModelScope.launch {
            try {
                // 1. Inicia el estado de carga
                _uiState.value = CandidateProfileUiState.Loading

                // 2. Obtiene los datos del Repositorio
                val data = repository.getCandidateProfile(candidatoId)

                // 3. Publica el éxito
                _uiState.value = CandidateProfileUiState.Success(data)

            } catch (e: NoSuchElementException) {
                // Captura el error si el candidato no existe (lanzado desde el Repositorio)
                _uiState.value = CandidateProfileUiState.Error("Candidato con ID $candidatoId no encontrado.")
            } catch (e: Exception) {
                // Captura otros errores (red, tiempo de espera, etc.)
                _uiState.value = CandidateProfileUiState.Error("Error al cargar el perfil: ${e.message}")
            }
        }
    }
}