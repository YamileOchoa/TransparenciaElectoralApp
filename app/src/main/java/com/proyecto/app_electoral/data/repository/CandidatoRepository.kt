package com.proyecto.app_electoral.data.repository

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.proyecto.app_electoral.R
import com.proyecto.app_electoral.data.db.AppDatabase
import com.proyecto.app_electoral.data.model.Candidato
import com.proyecto.app_electoral.data.model.DatosElectorales
import com.proyecto.app_electoral.data.model.Denuncia
import com.proyecto.app_electoral.data.model.HistorialCargo
import com.proyecto.app_electoral.data.model.Propuesta
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.text.Normalizer

class CandidatoRepository(private val context: Context, private val db: AppDatabase) {

    private val TAG = "CandidatoRepository"

    /**
     * Normaliza un String para que sea un nombre de recurso de Android válido.
     * Ej: "José-Pérez Foto" → "jose_perez_foto"
     */
    private fun normalizeImageName(name: String): String {
        val normalized = Normalizer.normalize(name, Normalizer.Form.NFD)
        val withoutAccents = Regex("\\p{InCombiningDiacriticalMarks}+").replace(normalized, "")
        return withoutAccents.trim().lowercase().replace(" ", "_").replace("-", "_")
    }

    private suspend fun processCandidatoImage(candidato: Candidato): Candidato = withContext(Dispatchers.IO) {
        // Eliminamos la extensión ANTES de normalizar el nombre
        val nameWithoutExtension = candidato.foto_url.substringBeforeLast('.')
        val normalizedName = normalizeImageName(nameWithoutExtension)
        val resId = context.resources.getIdentifier(normalizedName, "drawable", context.packageName)
        candidato.fotoResId = if (resId != 0) resId else R.drawable.ic_profile_placeholder
        candidato
    }

    fun getCandidatos(): Flow<List<Candidato>> {
        return db.candidatoDao().getAllCandidatos().map { list ->
            list.map { processCandidatoImage(it) }
        }
    }

    fun getCandidato(id: Int): Flow<Candidato?> {
        return db.candidatoDao().getCandidatoById(id).map { candidato ->
            candidato?.let { processCandidatoImage(it) }
        }
    }

    fun getMasBuscados(): Flow<List<Candidato>> {
        return db.candidatoDao().getMasBuscados().map { list ->
            list.map { processCandidatoImage(it) }
        }
    }

    suspend fun incrementarVisitas(id: Int) {
        db.candidatoDao().incrementarVisitas(id)
    }

    suspend fun ensureSeeded() {
        try {
            if (db.candidatoDao().getAllCandidatos().first().isEmpty()) {
                Log.d(TAG, "Base de datos vacía, iniciando siembra desde JSON.")
                seedDatabase()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error en ensureSeeden: ", e)
        }
    }

    private suspend fun seedDatabase() {
        try {
            val jsonString = context.assets.open("app-electoral-datos.json").bufferedReader().use { it.readText() }
            val datosElectorales = Gson().fromJson(jsonString, DatosElectorales::class.java)

            val candidatos = (datosElectorales.candidatos ?: emptyList()).map { candidatoDto ->
                Candidato(
                    id = candidatoDto.id,
                    nombre = candidatoDto.nombre,
                    partido = candidatoDto.partido,
                    cargo = candidatoDto.cargo,
                    region = candidatoDto.region,
                    foto_url = candidatoDto.foto_url.trim(), // Guardamos el nombre original, la normalización se hace al leer
                    dni = candidatoDto.dni,
                    nacimiento = candidatoDto.nacimiento,
                    biografia = candidatoDto.biografia,
                    experiencia = candidatoDto.experiencia,
                    estado = candidatoDto.estado,
                    propuestas = (datosElectorales.propuestas ?: emptyList())
                        .filter { it.candidatoId == candidatoDto.id }
                        .map { Propuesta(it.titulo, it.descripcion, it.categoria, it.prioridad) },
                    denuncias = (datosElectorales.denuncias ?: emptyList())
                        .filter { it.candidatoId == candidatoDto.id }
                        .map { Denuncia(it.titulo, it.descripcion, it.expediente, it.delito, it.fecha_denuncia, it.estado, it.fuente_url) },
                    historial = (datosElectorales.historialCargos ?: emptyList())
                        .filter { it.candidatoId == candidatoDto.id }
                        .map { HistorialCargo(it.cargo, it.institucion, it.fecha_inicio, it.fecha_fin, it.descripcion) }
                )
            }
            candidatos.chunked(100).forEach { chunk ->
                db.candidatoDao().insertAll(chunk)
            }

            Log.d(TAG, "✅ Base de datos poblada. Total de candidatos: ${candidatos.size}")
        } catch (e: Exception) {
            Log.e(TAG, "❌ Error al leer o parsear el JSON", e)
        }
    }
}
