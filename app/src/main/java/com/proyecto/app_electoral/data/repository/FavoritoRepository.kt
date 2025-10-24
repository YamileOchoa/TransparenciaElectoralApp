package com.proyecto.app_electoral.data.repository

import com.proyecto.app_electoral.data.dao.FavoritoDao
import com.proyecto.app_electoral.data.model.Favorito
import kotlinx.coroutines.flow.Flow

class FavoritoRepository(private val favoritoDao: FavoritoDao) {

    fun obtenerFavoritos(): Flow<List<Favorito>> {
        return favoritoDao.obtenerFavoritos()
    }

    suspend fun agregarFavorito(favorito: Favorito) {
        favoritoDao.agregarFavorito(favorito)
    }

    suspend fun eliminarFavorito(favorito: Favorito) {
        favoritoDao.eliminarFavorito(favorito)
    }
}
