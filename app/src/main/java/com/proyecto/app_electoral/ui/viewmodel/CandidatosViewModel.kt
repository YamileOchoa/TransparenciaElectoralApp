package com.proyecto.app_electoral.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyecto.app_electoral.data.model.Candidato
import com.proyecto.app_electoral.data.repository.CandidatoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class CandidatosViewModel(private val repository: CandidatoRepository) : ViewModel() {

    private val TAG = "CandidatosViewModel"

    private val _candidatos = MutableStateFlow<List<Candidato>>(emptyList())

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _selectedFilter = MutableStateFlow("Todos")
    val selectedFilter = _selectedFilter.asStateFlow()

    private val _selectedCandidato = MutableStateFlow<Candidato?>(null)
    val selectedCandidato = _selectedCandidato.asStateFlow()

    // 👇 NUEVOS CAMPOS para los totales
    private val _totalCandidatos = MutableStateFlow(0)
    val totalCandidatos = _totalCandidatos.asStateFlow()

    private val _totalPropuestas = MutableStateFlow(0)
    val totalPropuestas = _totalPropuestas.asStateFlow()

    private val _masBuscados = MutableStateFlow<List<Candidato>>(emptyList())
    val masBuscados = _masBuscados.asStateFlow()

    val filteredCandidatos = combine(
        _searchQuery,
        _selectedFilter,
        _candidatos
    ) { query, filter, candidatos ->

        var resultado = candidatos

        if (query.isNotBlank()) {
            resultado = resultado.filter {
                it.nombre.contains(query, ignoreCase = true) ||
                        it.partido.contains(query, ignoreCase = true) ||
                        it.region.contains(query, ignoreCase = true) // 👈 ahora también filtra por región
            }
        }

        resultado = when (filter) {
            "Con denuncias" -> resultado.filter { it.denuncias.isNotEmpty() }
            "Sin denuncias" -> resultado.filter { it.denuncias.isEmpty() }
            "Por partido" -> resultado.sortedBy { it.partido }
            "Por región" -> resultado.sortedBy { it.region }
            else -> resultado // "Todos"
        }

        resultado
    }

    init {
        viewModelScope.launch {
            repository.ensureSeeded()
            repository.getCandidatos().collect { candidatos ->
                Log.d(TAG, "Candidatos recibidos del repositorio: ${candidatos.size}")
                _candidatos.value = candidatos

                _totalCandidatos.value = candidatos.size
                _totalPropuestas.value = candidatos.sumOf { it.propuestas.size }
            }
        }

        cargarMasBuscados()
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun onFilterChange(filter: String) {
        _selectedFilter.value = filter
    }

    fun getCandidatoById(id: Int) {
        viewModelScope.launch {
            repository.getCandidato(id).collect { candidato ->
                _selectedCandidato.value = candidato

                candidato?.let {
                    repository.incrementarVisitas(it.id)
                    Log.d(TAG, "Visita registrada para: ${it.nombre}")
                    // 👇 Actualizamos la lista de más buscados en tiempo real
                    cargarMasBuscados()
                }
            }
        }
    }

    fun cargarMasBuscados() {
        viewModelScope.launch {
            repository.getMasBuscados().collect { lista ->
                _masBuscados.value = lista
                Log.d(TAG, "Más buscados cargados: ${lista.size}")
            }
        }
    }

    fun registrarVisita(id: Int) {
        viewModelScope.launch {
            repository.incrementarVisitas(id)
            Log.d(TAG, "Visita manual registrada para ID: $id")
            cargarMasBuscados()
        }
    }
}
