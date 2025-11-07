package com.proyecto.app_electoral.ui.components.compare

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import com.proyecto.app_electoral.ui.theme.TextGray
import com.proyecto.app_electoral.ui.theme.White

@Composable
fun CandidateComparisonContent(
    selectedTab: ComparisonTab
) {
    when (selectedTab) {
        ComparisonTab.Experience -> ExperienceSection()
        ComparisonTab.Proposals -> ProposalSection()
        ComparisonTab.Popularity -> PopularitySection()
    }
}

@Composable
fun ExperienceSection() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Experiencia", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(modifier = Modifier.weight(1f)) {
                ExperienceCardPlaceholder()
            }
            Box(modifier = Modifier.weight(1f)) {
                ExperienceCardPlaceholder()
            }
        }
    }
}

@Composable
fun ExperienceCardPlaceholder() {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .background(White, shape = RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        InfoRowPlaceholder("Laboral")
        InfoRowPlaceholder("Carrera Política")
        InfoRowPlaceholder("Educación")
    }
}

@Composable
fun InfoRowPlaceholder(label: String) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(label, fontWeight = FontWeight.SemiBold, fontSize = 12.sp, color = TextGray)
        Box(
            modifier = Modifier
                .height(16.dp)
                .fillMaxWidth()
                .background(TextGray.copy(alpha = 0.2f), shape = RoundedCornerShape(4.dp))
        )
    }
}

@Composable
fun ProposalSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(16.dp)
            .background(White, shape = RoundedCornerShape(8.dp)),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Text("Sección de Propuestas (placeholder)")
    }
}

@Composable
fun PopularitySection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(16.dp)
            .background(White, shape = RoundedCornerShape(8.dp)),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Text("Sección de Popularidad (placeholder)")
    }
}
