package com.proyecto.app_electoral.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.proyecto.app_electoral.data.model.Candidato
import kotlinx.coroutines.flow.Flow

@Dao
interface CandidatoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(candidatos: List<Candidato>)

    @Query("SELECT * FROM candidatos")
    fun getAllCandidatos(): Flow<List<Candidato>>

    @Query("SELECT * FROM candidatos WHERE id = :id")
    fun getCandidatoById(id: Int): Flow<Candidato?>
}
