package com.proyecto.app_electoral.ui.components.compare

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ComparisonSection(
    title: String,
    candidate1Items: List<String>,
    candidate1Button: String,
    candidate2Items: List<String> = emptyList(),
    candidate2Button: String = "",
    hasCandidate2: Boolean
) {
    Column {
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF333333),
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                // Columna para el Candidato 1
                CompareItemColumn(
                    modifier = Modifier.weight(1f),
                    items = candidate1Items,
                    buttonText = candidate1Button
                )

                VerticalDivider(
                    modifier = Modifier
                        .heightIn(min = 130.dp)
                        .width(1.dp),
                    color = Color(0xFFE0E0E0)
                )

                // Columna para el Candidato 2
                if (hasCandidate2) {
                    CompareItemColumn(
                        modifier = Modifier.weight(1f),
                        items = candidate2Items,
                        buttonText = candidate2Button
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(16.dp)
                            .heightIn(min = 120.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Selecciona un candidato",
                                fontSize = 12.sp,
                                color = Color(0xFF999999),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "para ver sus ${title.split(" ").last().lowercase()}",
                                fontSize = 12.sp,
                                color = Color(0xFF999999),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CompareItemColumn(
    modifier: Modifier = Modifier,
    items: List<String>,
    buttonText: String
) {
    Column(modifier = modifier.padding(16.dp)) {
        items.take(3).forEachIndexed { index, item ->
            Text(
                text = "• $item",
                fontSize = 12.sp,
                color = Color(0xFF666666),
                maxLines = 1
            )
            if (index < items.size - 1) Spacer(modifier = Modifier.height(8.dp))
        }

        Spacer(modifier = Modifier.height(12.dp))
        OutlinedButton(
            onClick = { /* TODO */ },
            modifier = Modifier.fillMaxWidth(),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
        ) {
            Text(
                buttonText,
                fontSize = 11.sp,
                color = Color(0xFF3b5998)
            )
        }
    }
}
