package com.proyecto.app_electoral.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.proyecto.app_electoral.data.repository.CandidatoRepository
import com.proyecto.app_electoral.di.Injector

// ViewModelFactory para instanciar ViewModels que requieren dependencias (como el Repository)
class ViewModelFactory(private val repository: CandidatoRepository) : ViewModelProvider.Factory {

    // Función estática para obtener una instancia con el inyector
    companion object {
        fun create(): ViewModelFactory {
            val repository = Injector.provideCandidatoRepository()
            return ViewModelFactory(repository)
        }
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CandidatosViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CandidatosViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}