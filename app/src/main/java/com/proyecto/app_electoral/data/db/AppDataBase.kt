package com.proyecto.app_electoral.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.proyecto.app_electoral.data.dao.CandidatoDao
import com.proyecto.app_electoral.data.model.Candidato

@Database(entities = [Candidato::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun candidatoDao(): CandidatoDao

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
                    // 2. Eliminamos la migración manual para evitar conflictos de hash
                    // 3. Dejamos que fallbackToDestructiveMigration sea la única estrategia
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}