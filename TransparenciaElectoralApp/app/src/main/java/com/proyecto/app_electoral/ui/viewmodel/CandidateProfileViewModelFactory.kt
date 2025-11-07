package com.proyecto.app_electoral.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.proyecto.app_electoral.data.repository.CandidatoRepository

/**
 * Factoría para crear una instancia de CandidateProfileViewModel.
 * Permite inyectar el ID del candidato y el Repositorio en el constructor del ViewModel.
 */
class CandidateProfileViewModelFactory(
    private val candidatoId: Int,
    private val repository: CandidatoRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CandidateProfileViewModel::class.java)) {
            return CandidateProfileViewModel(candidatoId, repository) as T
        }
        throw IllegalArgumentException("Clase de ViewModel desconocida")
    }
}