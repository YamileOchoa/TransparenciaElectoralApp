package com.proyecto.app_electoral.di

import android.content.Context
import com.proyecto.app_electoral.data.db.AppDatabase
import com.proyecto.app_electoral.data.repository.CandidatoRepository
import com.proyecto.app_electoral.data.repository.FavoritoRepository
import com.proyecto.app_electoral.ui.viewmodel.ViewModelFactory

object Injector {

    private fun provideDatabase(context: Context): AppDatabase {
        return AppDatabase.getInstance(context.applicationContext)
    }

    private fun provideCandidatoRepository(context: Context): CandidatoRepository {
        return CandidatoRepository(context, provideDatabase(context))
    }
    
    private fun provideFavoritoRepository(context: Context): FavoritoRepository {
        return FavoritoRepository(provideDatabase(context).favoritoDao())
    }

    fun provideViewModelFactory(context: Context): ViewModelFactory {
        return ViewModelFactory(
            candidatoRepository = provideCandidatoRepository(context),
            favoritoRepository = provideFavoritoRepository(context)
        )
    }
}
