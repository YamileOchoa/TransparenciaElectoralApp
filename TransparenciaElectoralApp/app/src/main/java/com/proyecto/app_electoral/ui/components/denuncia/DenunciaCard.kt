package com.proyecto.app_electoral.ui.components.denuncia

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DenunciaCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Acusación por malversación de fondos públicos",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                DenunciaTag(text = "Penal", backgroundColor = Color(0xFFB39DDB))
                DenunciaTag(text = "En investigación", backgroundColor = Color(0xFFFFCC80))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Fecha de inicio: 15 de Enero, 2023", fontSize = 14.sp)
            Text("Jurisdicción: Corte Superior de Justicia de Lima", fontSize = 14.sp)

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Esta sección presenta una descripción completa del caso, brindando contexto sobre los hechos, las partes implicadas y el estado actual del proceso judicial. Su propósito es ofrecer al ciudadano una comprensión clara, objetiva y concisa de las acusaciones formuladas contra el candidato. En este caso, se alega el desvío de fondos destinados a programas sociales durante su gestión anterior, lo que habría generado una investigación por presunto mal uso de recursos públicos. Además, se incluyen los antecedentes relevantes, las fechas clave y las fuentes oficiales que respaldan la información, garantizando transparencia y acceso a datos verificables.",
                fontSize = 14.sp,
                lineHeight = 20.sp
            )
        }
    }
}