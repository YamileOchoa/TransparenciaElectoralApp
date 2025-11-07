package com.proyecto.app_electoral.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyecto.app_electoral.data.model.*
import com.proyecto.app_electoral.data.repository.DataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Clase de estado para consolidar toda la data de los 5 endpoints
data class DebugUiState(
    val candidatos: List<Candidato> = emptyList(),
    val historialCargos: List<HistorialCargo> = emptyList(),
    val denuncias: List<Denuncia> = emptyList(),
    val proyectos: List<Proyecto> = emptyList(),
    val propuestas: List<Propuesta> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class DebugViewModel(private val repository: DataRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(DebugUiState())
    val uiState: StateFlow<DebugUiState> = _uiState.asStateFlow()

    init {
        loadAllData()
    }

    fun loadAllData() {
        _uiState.value = _uiState.value.copy(isLoading = true, error = null)
        viewModelScope.launch {
            try {
                val candidatos = repository.getCandidatos()
                val historialCargos = repository.getHistorialCargos()
                val denuncias = repository.getDenuncias()
                val proyectos = repository.getProyectos()
                val propuestas = repository.getPropuestas()

                // Actualizar el estado con los datos cargados
                _uiState.value = _uiState.value.copy(
                    candidatos = candidatos,
                    historialCargos = historialCargos,
                    denuncias = denuncias,
                    proyectos = proyectos,
                    propuestas = propuestas,
                    isLoading = false
                )

            } catch (e: Exception) {
                // Manejo de errores (ej: servidor caído, error de conexión, deserialización)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Error al cargar datos: ¿El servidor de Django está corriendo en http://10.0.2.2:8000/api/? Mensaje: ${e.localizedMessage}"
                )
                e.printStackTrace()
            }
        }
    }
}