package com.proyecto.app_electoral.ui.components.comparison

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons // <<-- IMPORTACIÓN NECESARIA
import androidx.compose.material.icons.filled.Lightbulb // <<-- ICONO PARA PROPUESTAS
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
// ELIMINADO: import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
// ELIMINADO: import com.proyecto.app_electoral.R
import com.proyecto.app_electoral.data.repository.CandidateProfileData
import com.proyecto.app_electoral.data.network.model.Propuesta


/**
 * Contenido detallado de la pestaña Propuestas.
 * Utiliza los perfiles completos para mostrar la lista de propuestas.
 */
@Composable
fun ProposalContent(
    profile1: CandidateProfileData?,
    profile2: CandidateProfileData?
) {
    // Si ambos candidatos están presentes, tomamos el mayor número de propuestas para iterar.
    val maxProposalsCount = maxOf(
        profile1?.propuestas?.size ?: 0,
        profile2?.propuestas?.size ?: 0
    )

    // Creamos una lista de índices para iterar y mostrar las propuestas
    val indices = (0 until maxProposalsCount).toList()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        item {
            // CORRECCIÓN: Usamos Icons.Filled.Lightbulb (Material Icon)
            SectionTitle(Icons.Filled.Lightbulb, "Propuestas Detalladas")
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Iteramos sobre el número máximo de propuestas
        items(indices) { index ->

            // Intenta obtener la propuesta del candidato 1 en el índice actual
            val prop1: Propuesta? = profile1?.propuestas?.getOrNull(index)
            // Intenta obtener la propuesta del candidato 2 en el índice actual
            val prop2: Propuesta? = profile2?.propuestas?.getOrNull(index)

            // Solo mostramos la tabla si al menos un candidato tiene una propuesta en este índice
            if (prop1 != null || prop2 != null) {
                ComparisonTable(
                    leftTitle = prop1?.categoria ?: "Sin Categoría",
                    leftValue = prop1?.titulo ?: "Sin propuesta registrada",
                    rightTitle = prop2?.categoria ?: "Sin Categoría",
                    rightValue = prop2?.titulo ?: "Sin propuesta registrada"
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}