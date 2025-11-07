package com.proyecto.app_electoral.ui.components.comparison

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.proyecto.app_electoral.data.network.model.Candidato

/**
 * Sección Superior con el título y las tarjetas de los candidatos.
 *
 * @param onSelectCandidate Función lambda que se ejecuta al seleccionar un placeholder.
 * Recibe la posición a llenar (1 o 2).
 */
@Composable
fun ComparisonHeader(
    navController: NavController,
    candidato1: Candidato?, // Se cambia de data: ComparisonData a parámetros individuales
    candidato2: Candidato?,
    onSelectCandidate: (position: Int) -> Unit // <<-- NUEVO: Callback de selección
) {
    val headerColor = Color(0xFF4A148C)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(headerColor)
            .padding(top = 8.dp, bottom = 24.dp)
    ) {
        // Barra Superior (Atrás y Título)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Atrás",
                tint = Color.White,
                modifier = Modifier
                    .clickable { navController.popBackStack() }
                    .padding(end = 16.dp)
            )
            Text(
                text = "Comparación de Candidatos",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Tarjetas de Candidatos (2 columnas)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Candidato 1
            CandidateCard(
                candidato = candidato1,
                isSelected = true,
                onCardClick = { onSelectCandidate(1) }, // Acción para llenar posición 1
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            // Candidato 2
            CandidateCard(
                candidato = candidato2,
                isSelected = false,
                onCardClick = { onSelectCandidate(2) }, // Acción para llenar posición 2
                modifier = Modifier.weight(1f)
            )
        }
    }
}