package com.proyecto.app_electoral.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.proyecto.app_electoral.data.model.*

@Database(
    entities = [
        Propuesta::class,
        Proyecto::class,
        Denuncia::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
}
