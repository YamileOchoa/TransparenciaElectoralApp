package com.proyecto.app_electoral.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.proyecto.app_electoral.data.db.AppDatabase
import com.proyecto.app_electoral.data.repository.CandidatoRepository
import com.proyecto.app_electoral.ui.viewmodel.CandidatosViewModel

object Injector {

    private fun provideCandidatoRepository(context: Context): CandidatoRepository {
        val database = AppDatabase.getInstance(context.applicationContext)
        // El repositorio ahora necesita el contexto para leer el JSON
        return CandidatoRepository(context.applicationContext, database)
    }

    fun provideViewModelFactory(context: Context): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return when {
                    modelClass.isAssignableFrom(CandidatosViewModel::class.java) -> {
                        CandidatosViewModel(provideCandidatoRepository(context)) as T
                    }
                    else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
                }
            }
        }
    }
}
