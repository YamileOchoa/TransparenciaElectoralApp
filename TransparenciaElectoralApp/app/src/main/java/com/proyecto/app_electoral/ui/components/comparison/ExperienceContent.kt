package com.proyecto.app_electoral.ui.components.comparison

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Work
import androidx.compose.material.icons.filled.School // <<-- ICONO PARA EDUCACIÓN
import androidx.compose.material.icons.filled.Lightbulb // <<-- ICONO PARA PROPUESTAS
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector // Tipo para iconos de Compose
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.proyecto.app_electoral.data.repository.CandidateProfileData

/**
 * Contenido de la pestaña Experiencia, Educación y Propuestas Clave.
 * Recibe los perfiles reales para la comparación.
 */
@Composable
fun ExperienceContent(
    // 1. ACEPTA LOS DATOS REALES DE LOS ENDPOINTS
    profile1: CandidateProfileData? = null,
    profile2: CandidateProfileData? = null
) {
    // Función helper: obtiene el cargo más reciente para la tabla
    fun getRecentExperience(profile: CandidateProfileData?): String {
        // Usa la lista real de historialCargos
        return profile?.historialCargos
            ?.firstOrNull()
            ?.let { "${it.cargo} (${it.fecha_inicio} - ${it.fecha_fin})" }
            ?: "Sin cargo reciente registrado"
    }

    // Función helper: obtiene la propuesta principal (la primera)
    fun getRelevantProposal(profile: CandidateProfileData?): String {
        // Usa la lista real de propuestas
        return profile?.propuestas
            ?.firstOrNull()
            ?.titulo
            ?: "Sin propuesta clave registrada"
    }

    // Nota: La Educación aún no tiene un endpoint, se mantiene la lógica mockeada
    fun getMockEducation(profile: CandidateProfileData?): String {
        val name = profile?.candidato?.nombre ?: ""
        return when {
            name.contains("Ana", ignoreCase = true) -> "Maestría en Economía"
            name.contains("Juan", ignoreCase = true) -> "Licenciatura en Derecho"
            profile != null -> "Grado Universitario"
            else -> "Información no disponible"
        }
    }


    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // --- 1. EXPERIENCIA LABORAL/POLÍTICA ---
        item { SectionTitle(Icons.Default.Work, "Experiencia y Cargos") }
        item {
            ComparisonTable(
                leftTitle = "Cargo más reciente",
                // 2. USA EL DATO REAL DEL ENDPOINT
                leftValue = getRecentExperience(profile1),
                rightTitle = "Cargo más reciente",
                // 2. USA EL DATO REAL DEL ENDPOINT
                rightValue = getRecentExperience(profile2)
            )
            // Se elimina la segunda ComparisonTable estática
        }

        // --- 2. EDUCACIÓN ---
        item { Spacer(modifier = Modifier.height(16.dp)) }
        item {
            // 3. USA EL ICONO DE COMPOSE
            SectionTitle(Icons.Default.School, "Educación")
        }
        item {
            ComparisonTable(
                leftTitle = "Nivel de Estudios",
                // 2. USA EL DATO MOCKEADO (temporal)
                leftValue = getMockEducation(profile1),
                rightTitle = "Nivel de Estudios",
                // 2. USA EL DATO MOCKEADO (temporal)
                rightValue = getMockEducation(profile2)
            )
        }

        // --- 3. PROPUESTAS CLAVE (El resumen del perfil) ---
        item { Spacer(modifier = Modifier.height(16.dp)) }
        item {
            // 3. USA EL ICONO DE COMPOSE
            SectionTitle(Icons.Default.Lightbulb, "Propuesta Clave")
        }
        item {
            ComparisonTable(
                // 2. USA EL DATO REAL DEL ENDPOINT
                leftTitle = profile1?.propuestas?.firstOrNull()?.categoria ?: "General",
                leftValue = getRelevantProposal(profile1),
                // 2. USA EL DATO REAL DEL ENDPOINT
                rightTitle = profile2?.propuestas?.firstOrNull()?.categoria ?: "General",
                rightValue = getRelevantProposal(profile2)
            )
            // Se elimina la segunda ComparisonTable estática
        }
    }
}

/**
 * Componente reutilizable para los títulos de sección.
 * Se remueve el tipo 'Any' y se usa 'ImageVector' para forzar el uso de Material Icons.
 */
@Composable
// 3. RECIBE ImageVector DIRECTAMENTE
fun SectionTitle(icon: ImageVector, title: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        val iconModifier = Modifier.size(24.dp)
        val iconTint = MaterialTheme.colorScheme.primary

        // Ahora solo usa ImageVector
        Icon(imageVector = icon, contentDescription = title, tint = iconTint, modifier = iconModifier)

        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}