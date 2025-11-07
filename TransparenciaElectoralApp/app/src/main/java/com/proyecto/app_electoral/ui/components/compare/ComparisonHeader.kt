package com.proyecto.app_electoral.ui.components.compare

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import com.proyecto.app_electoral.ui.theme.PurpleSecondary
import com.proyecto.app_electoral.ui.theme.White

@Composable
fun ComparisonHeader(
    onBackClick: () -> Unit = {},
    onSelectCandidateLeft: () -> Unit = {},
    onSelectCandidateRight: () -> Unit = {}
) {
    val startColor = Color(0xFF2E155C)
    val endColor = Color(0xFF542F96)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Brush.horizontalGradient(listOf(startColor, endColor)))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Atrás",
                tint = Color.White,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onBackClick() }
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = "Comparación de Candidatos",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(110.dp)
        ) {
            CandidateCardPlaceholder(onClick = onSelectCandidateLeft)
            CandidateCardPlaceholder(onClick = onSelectCandidateRight)
        }
    }
}


@Composable
fun CandidateCardPlaceholder(
    onClick: (() -> Unit)? = null
) {
    Box(
        modifier = Modifier
            .size(100.dp)
            .background(PurpleSecondary, shape = RoundedCornerShape(8.dp))
            .clickable { onClick?.invoke() },
        contentAlignment = Alignment.Center
    ) {
        Text("+", color = White, fontSize = 24.sp)
    }
}

