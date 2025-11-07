package com.proyecto.app_electoral.ui.components.compare

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.proyecto.app_electoral.data.repository.CandidateProfileData
import com.proyecto.app_electoral.ui.components.compare.CandidateCard as CandidateSlotCard
import com.proyecto.app_electoral.ui.theme.DarkCustom
import com.proyecto.app_electoral.ui.theme.PrimaryCustom

@Composable
fun ComparisonHeader(
    leftProfile: CandidateProfileData?,
    rightProfile: CandidateProfileData?,
    isLeftLoading: Boolean,
    isRightLoading: Boolean,
    onBackClick: () -> Unit,
    onSelectCandidateLeft: () -> Unit,
    onSelectCandidateRight: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(Brush.horizontalGradient(listOf(DarkCustom, PrimaryCustom)))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Atrás",
                tint = Color.White,
                modifier = Modifier
                    .size(28.dp)
                    .padding(end = 12.dp)
                    .clickable(onClick = onBackClick)
            )
            Column {
                Text(
                    text = "Comparación de candidatos",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Selecciona hasta dos para ver su información",
                    color = Color.White.copy(alpha = 0.8f),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CandidateSlot(
                profile = leftProfile,
                isLoading = isLeftLoading,
                onClick = onSelectCandidateLeft
            )
            Spacer(modifier = Modifier.width(24.dp))
            CandidateSlot(
                profile = rightProfile,
                isLoading = isRightLoading,
                onClick = onSelectCandidateRight
            )
        }
    }
}

@Composable
private fun CandidateSlot(
    profile: CandidateProfileData?,
    isLoading: Boolean,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        CandidateSlotCard(
            name = profile?.candidato?.nombre ?: "Seleccionar",
            party = profile?.candidato?.partido ?: "Toca para elegir",
            fotoUrl = profile?.candidato?.foto_url,
            hasCandidate = profile != null,
            onButtonClick = onClick
        )

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.25f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(32.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
