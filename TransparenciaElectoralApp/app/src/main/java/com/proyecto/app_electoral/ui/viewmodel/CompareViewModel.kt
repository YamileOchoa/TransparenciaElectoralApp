package com.proyecto.app_electoral.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.proyecto.app_electoral.data.network.model.Candidato
import com.proyecto.app_electoral.data.repository.CandidateProfileData
import com.proyecto.app_electoral.data.repository.CandidatoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class CompareSlot { LEFT, RIGHT }

data class CompareUiState(
    val candidates: List<Candidato> = emptyList(),
    val leftProfile: CandidateProfileData? = null,
    val rightProfile: CandidateProfileData? = null,
    val isLoadingList: Boolean = true,
    val slotLoading: CompareSlot? = null,
    val error: String? = null
)

class CompareViewModel(
    private val repository: CandidatoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CompareUiState())
    val uiState: StateFlow<CompareUiState> = _uiState.asStateFlow()

    init {
        loadCandidates()
    }

    private fun loadCandidates() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingList = true, error = null) }
            try {
                val candidatos = repository.getCandidatos()
                _uiState.update {
                    it.copy(
                        candidates = candidatos.sortedBy { cand -> cand.nombre },
                        isLoadingList = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoadingList = false,
                        error = "No se pudieron cargar los candidatos: ${e.localizedMessage ?: "Error desconocido"}"
                    )
                }
            }
        }
    }

    fun selectCandidate(slot: CompareSlot, candidato: Candidato) {
        viewModelScope.launch {
            _uiState.update { it.copy(slotLoading = slot, error = null) }
            try {
                val profileData = repository.getCandidateProfile(candidato.id)
                _uiState.update { current ->
                    when (slot) {
                        CompareSlot.LEFT -> current.copy(leftProfile = profileData, slotLoading = null)
                        CompareSlot.RIGHT -> current.copy(rightProfile = profileData, slotLoading = null)
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        slotLoading = null,
                        error = "No se pudo cargar el perfil: ${e.localizedMessage ?: "Error desconocido"}"
                    )
                }
            }
        }
    }

    fun dismissError() {
        _uiState.update { it.copy(error = null) }
    }
}

class CompareViewModelFactory(
    private val repository: CandidatoRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CompareViewModel::class.java)) {
            return CompareViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
