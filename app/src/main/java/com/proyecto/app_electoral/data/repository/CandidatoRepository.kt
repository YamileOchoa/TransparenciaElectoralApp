package com.proyecto.app_electoral.data.repository

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.proyecto.app_electoral.data.db.AppDatabase
import com.proyecto.app_electoral.data.model.Candidato
import com.proyecto.app_electoral.data.model.DatosElectorales
import com.proyecto.app_electoral.data.model.Denuncia
import com.proyecto.app_electoral.data.model.HistorialCargo
import com.proyecto.app_electoral.data.model.Propuesta
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class CandidatoRepository(private val context: Context, private val db: AppDatabase) {

    private val TAG = "CandidatoRepository"

    private val candidatoDao = db.candidatoDao()

    fun getCandidatos(): Flow<List<Candidato>> {
        return candidatoDao.getAllCandidatos()
    }

    fun getCandidato(id: Int): Flow<Candidato?> {
        return candidatoDao.getCandidatoById(id)
    }

    fun getMasBuscados(): Flow<List<Candidato>> = candidatoDao.getMasBuscados()

    suspend fun incrementarVisitas(id: Int) {
        candidatoDao.incrementarVisitas(id)
    }

    suspend fun ensureSeeded() {
        try {
            if (candidatoDao.getAllCandidatos().first().isEmpty()) {
                Log.d(TAG, "Base de datos vacía, iniciando siembra desde JSON.")
                seedDatabase()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error en ensureSeeded: ", e)
        }
    }

    private suspend fun seedDatabase() {
        try {
            val jsonString = context.assets.open("app-electoral-datos.json")
                .bufferedReader()
                .use { it.readText() }

            val datosElectorales = Gson().fromJson(jsonString, DatosElectorales::class.java)

            Log.d(
                TAG, "JSON: ${datosElectorales.candidatos?.size ?: 0} candidatos, " +
                        "${datosElectorales.propuestas?.size ?: 0} propuestas, " +
                        "${datosElectorales.denuncias?.size ?: 0} denuncias, " +
                        "${datosElectorales.historialCargos?.size ?: 0} historial."
            )

            val candidatos = (datosElectorales.candidatos ?: emptyList()).map { candidatoDto ->
                Candidato(
                    id = candidatoDto.id,
                    nombre = candidatoDto.nombre,
                    partido = candidatoDto.partido,
                    cargo = candidatoDto.cargo,
                    region = candidatoDto.region,
                    foto_url = candidatoDto.foto_url,
                    dni = candidatoDto.dni,
                    nacimiento = candidatoDto.nacimiento,
                    biografia = candidatoDto.biografia,
                    experiencia = candidatoDto.experiencia,
                    estado = candidatoDto.estado,
                    propuestas = (datosElectorales.propuestas ?: emptyList())
                        .filter { it.candidatoId == candidatoDto.id }
                        .map {
                            Propuesta(
                                it.titulo,
                                it.descripcion,
                                it.categoria,
                                it.prioridad
                            )
                        },
                    denuncias = (datosElectorales.denuncias ?: emptyList())
                        .filter { it.candidatoId == candidatoDto.id }
                        .map {
                            Denuncia(
                                it.titulo,
                                it.descripcion,
                                it.expediente,
                                it.delito,
                                it.fecha_denuncia,
                                it.estado,
                                it.fuente_url
                            )
                        },
                    historial = (datosElectorales.historialCargos ?: emptyList())
                        .filter { it.candidatoId == candidatoDto.id }
                        .map {
                            HistorialCargo(
                                it.cargo,
                                it.institucion,
                                it.fecha_inicio,
                                it.fecha_fin,
                                it.descripcion
                            )
                        },
                    visitas = 0
                )
            }

            candidatoDao.insertAll(candidatos)
            Log.d(TAG, "✅ Insertados ${candidatos.size} candidatos correctamente.")
        } catch (e: Exception) {
            Log.e(TAG, "❌ Error al leer o parsear el JSON", e)
        }
    }
}
