package com.proyecto.app_electoral.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyecto.app_electoral.data.model.Candidato
import com.proyecto.app_electoral.data.model.Favorito
import com.proyecto.app_electoral.data.repository.CandidatoRepository
import com.proyecto.app_electoral.data.repository.FavoritoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CandidatosViewModel(
    private val candidatoRepository: CandidatoRepository,
    private val favoritoRepository: FavoritoRepository
) : ViewModel() {

    private val TAG = "CandidatosViewModel"

    private val _candidatos = MutableStateFlow<List<Candidato>>(emptyList())

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _selectedFilter = MutableStateFlow("Todos")
    val selectedFilter = _selectedFilter.asStateFlow()

    private val _selectedCandidato = MutableStateFlow<Candidato?>(null)
    val selectedCandidato = _selectedCandidato.asStateFlow()

    private val _totalCandidatos = MutableStateFlow(0)
    val totalCandidatos = _totalCandidatos.asStateFlow()

    private val _totalPropuestas = MutableStateFlow(0)
    val totalPropuestas = _totalPropuestas.asStateFlow()

    private val _masBuscados = MutableStateFlow<List<Candidato>>(emptyList())
    val masBuscados = _masBuscados.asStateFlow()

    private val _favoritos = MutableStateFlow<List<Int>>(emptyList())
    val favoritos = _favoritos.asStateFlow()

    val filteredCandidatos = combine(
        _searchQuery, _selectedFilter, _candidatos
    ) { query, filter, candidatos ->
        var resultado = candidatos
        if (query.isNotBlank()) {
            resultado = resultado.filter {
                it.nombre.contains(query, ignoreCase = true) ||
                        it.partido.contains(query, ignoreCase = true) ||
                        it.region.contains(query, ignoreCase = true)
            }
        }
        when (filter) {
            "Con denuncias" -> resultado.filter { (it.denuncias?.size ?: 0) > 0 }
            "Sin denuncias" -> resultado.filter { (it.denuncias?.size ?: 0) == 0 }
            "Por partido" -> resultado.sortedBy { it.partido }
            "Por región" -> resultado.sortedBy { it.region }
            else -> resultado
        }
    }

    val candidatosFavoritos = combine(_candidatos, _favoritos) { candidatos, favoritos ->
        candidatos.filter { it.id in favoritos }
    }

    init {
        viewModelScope.launch {
            // 1. Ejecutar y esperar a que la siembra termine en un hilo de IO.
            withContext(Dispatchers.IO) {
                candidatoRepository.ensureSeeded()
            }

            // 2. Una vez terminada la siembra, lanzar la recolección de datos.
            launch {
                candidatoRepository.getCandidatos().collect { candidatos ->
                    _candidatos.value = candidatos
                    _totalCandidatos.value = candidatos.size
                    _totalPropuestas.value = candidatos.sumOf { it.propuestas?.size ?: 0 }
                }
            }

            launch {
                favoritoRepository.obtenerFavoritos().map { list -> list.map { it.candidatoId } }.collect {
                    _favoritos.value = it
                }
            }

            cargarMasBuscados()
        }
    }

    fun onSearchQueryChange(query: String) { _searchQuery.value = query }
    fun onFilterChange(filter: String) { _selectedFilter.value = filter }

    fun getCandidatoById(id: Int) {
        viewModelScope.launch {
            candidatoRepository.getCandidato(id).collect { candidato ->
                _selectedCandidato.value = candidato
                candidato?.let {
                    candidatoRepository.incrementarVisitas(it.id)
                    cargarMasBuscados()
                }
            }
        }
    }

    fun toggleFavorito(candidatoId: Int) {
        viewModelScope.launch {
            if (candidatoId in _favoritos.value) {
                favoritoRepository.eliminarFavorito(Favorito(candidatoId))
            } else {
                favoritoRepository.agregarFavorito(Favorito(candidatoId))
            }
        }
    }

    fun cargarMasBuscados() {
        viewModelScope.launch {
            candidatoRepository.getMasBuscados().collect { _masBuscados.value = it }
        }
    }

    fun registrarVisita(id: Int) {
        viewModelScope.launch {
            candidatoRepository.incrementarVisitas(id)
            cargarMasBuscados()
        }
    }
}
