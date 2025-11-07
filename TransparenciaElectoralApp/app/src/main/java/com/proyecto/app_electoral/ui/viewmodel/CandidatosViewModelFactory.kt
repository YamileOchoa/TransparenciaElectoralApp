package com.proyecto.app_electoral.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.proyecto.app_electoral.data.repository.CandidatoRepository

/**
 * Factoría para crear una instancia de CandidatosViewModel.
 * Permite inyectar el Repositorio en el constructor del ViewModel.
 */
class CandidatosViewModelFactory(
    private val repository: CandidatoRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CandidatosViewModel::class.java)) {
            return CandidatosViewModel(repository) as T
        }
        throw IllegalArgumentException("Clase de ViewModel desconocida")
    }
}