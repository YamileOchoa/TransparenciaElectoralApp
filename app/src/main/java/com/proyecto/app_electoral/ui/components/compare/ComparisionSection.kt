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
import androidx.compose.material3.VerticalDivider


@Composable
fun ComparisonSection(
    title: String,
    candidate1Items: List<String>,
    candidate1Button: String,
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
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp)
                ) {
                    candidate1Items.forEachIndexed { index, item ->
                        Text(
                            text = "${index + 1}. $item",
                            fontSize = 12.sp,
                            color = Color(0xFF666666)
                        )
                        if (index < candidate1Items.size - 1)
                            Spacer(modifier = Modifier.height(8.dp))
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedButton(
                        onClick = { /* TODO */ },
                        modifier = Modifier.fillMaxWidth(),
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            candidate1Button,
                            fontSize = 11.sp,
                            color = Color(0xFF3b5998)
                        )
                    }
                }

                VerticalDivider(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .height(130.dp)
                        .width(1.dp),
                    color = Color(0xFFE0E0E0)
                )

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp)
                        .heightIn(min = 120.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (!hasCandidate2) {
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
