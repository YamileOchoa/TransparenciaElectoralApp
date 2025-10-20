package com.proyecto.app_electoral.ui.components.compare

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.VerticalDivider

@Composable
fun DenunciasSection() {
    Column {
        Text(
            text = "⚠️ Denuncias",
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
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                // Candidato 1
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF4CAF50))
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Sin denuncias registradas",
                            fontSize = 12.sp,
                            color = Color(0xFF4CAF50),
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = { /* TODO */ },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFE8F5E9)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            "✓ Registro limpio",
                            fontSize = 11.sp,
                            color = Color(0xFF4CAF50)
                        )
                    }
                }

                VerticalDivider(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .height(100.dp)
                        .width(1.dp),
                    color = Color(0xFFE0E0E0)
                )


                //candidato 2
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp)
                        .heightIn(min = 100.dp),
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
                            text = "para ver sus denuncias",
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
