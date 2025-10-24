package com.proyecto.app_electoral.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.proyecto.app_electoral.data.dao.CandidatoDao
import com.proyecto.app_electoral.data.dao.FavoritoDao
import com.proyecto.app_electoral.data.model.Candidato
import com.proyecto.app_electoral.data.model.Favorito

// 1. Añadimos la nueva entidad y subimos la versión de la DB a 4
@Database(entities = [Candidato::class, Favorito::class], version = 4, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun candidatoDao(): CandidatoDao
    // 2. Exponemos el nuevo DAO
    abstract fun favoritoDao(): FavoritoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_electoral.db"
                )
                    // fallbackToDestructiveMigration se encargará de recrear la DB
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
