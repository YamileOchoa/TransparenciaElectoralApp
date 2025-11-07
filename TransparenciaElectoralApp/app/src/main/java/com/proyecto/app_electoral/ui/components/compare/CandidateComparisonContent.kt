package com.proyecto.app_electoral.ui.components.compare

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.proyecto.app_electoral.data.repository.CandidateProfileData

@Composable
fun CandidateComparisonContent(
    modifier: Modifier = Modifier,
    selectedTab: ComparisonTab,
    leftProfile: CandidateProfileData?,
    rightProfile: CandidateProfileData?
) {
    if (leftProfile == null && rightProfile == null) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Selecciona al menos un candidato para iniciar la comparación.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        return
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        when (selectedTab) {
            ComparisonTab.Experience -> ExperienceSection(leftProfile, rightProfile)
            ComparisonTab.Proposals -> ProposalSection(leftProfile, rightProfile)
            ComparisonTab.Popularity -> PopularitySection(leftProfile, rightProfile)
        }
    }
}

@Composable
private fun ExperienceSection(
    leftProfile: CandidateProfileData?,
    rightProfile: CandidateProfileData?
) {
    ComparisonSection(
        title = "Historial de cargos",
        candidate1Items = buildExperienceItems(leftProfile),
        candidate1Button = "",
        candidate2Items = buildExperienceItems(rightProfile),
        candidate2Button = "",
        hasCandidate2 = rightProfile != null
    )

    ComparisonSection(
        title = "Perfil profesional",
        candidate1Items = buildProfileItems(leftProfile),
        candidate1Button = "",
        candidate2Items = buildProfileItems(rightProfile),
        candidate2Button = "",
        hasCandidate2 = rightProfile != null
    )
}

@Composable
private fun ProposalSection(
    leftProfile: CandidateProfileData?,
    rightProfile: CandidateProfileData?
) {
    ComparisonSection(
        title = "Principales propuestas",
        candidate1Items = buildProposalItems(leftProfile),
        candidate1Button = "",
        candidate2Items = buildProposalItems(rightProfile),
        candidate2Button = "",
        hasCandidate2 = rightProfile != null
    )

    ComparisonSection(
        title = "Proyectos impulsados",
        candidate1Items = buildProjectItems(leftProfile),
        candidate1Button = "",
        candidate2Items = buildProjectItems(rightProfile),
        candidate2Button = "",
        hasCandidate2 = rightProfile != null
    )
}

@Composable
private fun PopularitySection(
    leftProfile: CandidateProfileData?,
    rightProfile: CandidateProfileData?
) {
    ComparisonSection(
        title = "Actividad y popularidad",
        candidate1Items = buildPopularityItems(leftProfile),
        candidate1Button = "",
        candidate2Items = buildPopularityItems(rightProfile),
        candidate2Button = "",
        hasCandidate2 = rightProfile != null
    )
}

private fun buildExperienceItems(profile: CandidateProfileData?): List<String> {
    if (profile == null) return emptyList()
    if (profile.historialCargos.isEmpty()) return listOf("Sin registros disponibles")

    return profile.historialCargos.take(3).map { cargo ->
        val periodo = cargo.fecha_fin ?: "Actualidad"
        "${cargo.cargo} - ${cargo.institucion} (${cargo.fecha_inicio} • $periodo)"
    }
}

private fun buildProfileItems(profile: CandidateProfileData?): List<String> {
    val candidato = profile?.candidato ?: return emptyList()
    val items = mutableListOf<String>()

    candidato.profesion?.takeIf { it.isNotBlank() }?.let {
        items.add("Profesión: $it")
    }
    candidato.region.takeIf { it.isNotBlank() }?.let {
        items.add("Región: $it")
    }
    candidato.estado.takeIf { it.isNotBlank() }?.let {
        items.add("Estado: $it")
    }

    return items.ifEmpty { listOf("Sin información disponible") }
}

private fun buildProposalItems(profile: CandidateProfileData?): List<String> {
    if (profile == null) return emptyList()
    if (profile.propuestas.isEmpty()) return listOf("No se registran propuestas")

    return profile.propuestas.take(4).map { propuesta ->
        "${propuesta.titulo} • ${propuesta.categoria} (${propuesta.prioridad})"
    }
}

private fun buildProjectItems(profile: CandidateProfileData?): List<String> {
    if (profile == null) return emptyList()
    if (profile.proyectos.isEmpty()) return listOf("Sin proyectos asociados")

    return profile.proyectos.take(4).map { proyecto ->
        "${proyecto.titulo} • ${proyecto.estado} (${proyecto.categoria})"
    }
}

private fun buildPopularityItems(profile: CandidateProfileData?): List<String> {
    val candidato = profile?.candidato ?: return emptyList()

    return listOf(
        "Visitas al perfil: ${candidato.visitas}",
        "Propuestas publicadas: ${profile.propuestas.size}",
        "Proyectos registrados: ${profile.proyectos.size}",
        "Denuncias activas: ${profile.denuncias.size}"
    )
}
