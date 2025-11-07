package com.proyecto.app_electoral.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyecto.app_electoral.data.network.model.Candidato
import com.proyecto.app_electoral.data.repository.CandidatoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class CandidatoUiState {
    object Loading : CandidatoUiState()
    data class Success(val candidatos: List<Candidato>) : CandidatoUiState()
    data class Error(val message: String) : CandidatoUiState()
}

class CandidatosViewModel(private val repository: CandidatoRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<CandidatoUiState>(CandidatoUiState.Loading)
    val uiState: StateFlow<CandidatoUiState> = _uiState.asStateFlow()

    init {
        loadCandidatos()
    }

    fun loadCandidatos() {
        _uiState.value = CandidatoUiState.Loading
        viewModelScope.launch {
            try {
                val candidatos = repository.getCandidatos()
                _uiState.value = CandidatoUiState.Success(candidatos)
            } catch (e: Exception) {
                _uiState.value = CandidatoUiState.Error("Error al cargar candidatos: ${e.localizedMessage ?: "Error de red/servidor"}")
            }
        }
    }
}