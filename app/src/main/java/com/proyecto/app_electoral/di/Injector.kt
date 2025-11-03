package com.proyecto.app_electoral.di

import android.content.Context
import com.proyecto.app_electoral.data.db.AppDatabase
import com.proyecto.app_electoral.data.repository.CandidatoRepository
import com.proyecto.app_electoral.data.repository.FavoritoRepository
import com.proyecto.app_electoral.ui.viewmodel.ViewModelFactory

import com.proyecto.app_electoral.data.network.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Injector {

    private fun provideApiService(): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8000/api/") // URL local para emulador Android
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ApiService::class.java)
    }

    private fun provideDatabase(context: Context): AppDatabase {
        return AppDatabase.getInstance(context.applicationContext)
    }

    private fun provideCandidatoRepository(context: Context, apiService: ApiService): CandidatoRepository {
        return CandidatoRepository(context, provideDatabase(context), apiService)
    }
    
    private fun provideFavoritoRepository(context: Context): FavoritoRepository {
        return FavoritoRepository(provideDatabase(context).favoritoDao())
    }

    fun provideViewModelFactory(context: Context): ViewModelFactory {
        return ViewModelFactory(
            candidatoRepository = provideCandidatoRepository(context, provideApiService()),
            favoritoRepository = provideFavoritoRepository(context)
        )
    }
}
