package com.proyecto.app_electoral.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.proyecto.app_electoral.data.repository.CandidatoRepository
import com.proyecto.app_electoral.data.repository.FavoritoRepository

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val candidatoRepository: CandidatoRepository,
    private val favoritoRepository: FavoritoRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CandidatosViewModel::class.java)) {
            return CandidatosViewModel(candidatoRepository, favoritoRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
