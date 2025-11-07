package com.proyecto.app_electoral.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.proyecto.app_electoral.ui.components.BottomNavigationBar
import com.proyecto.app_electoral.ui.components.search.CandidateProgressBar
import com.proyecto.app_electoral.ui.components.search.SearchHeader
import com.proyecto.app_electoral.ui.theme.*
import androidx.compose.material.icons.filled.KeyboardArrowRight

@Composable
fun SearchScreen() {
    var selectedFilter by remember { mutableStateOf("Nombre") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 56.dp)
        ) {
            SearchHeader()

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .background(White, RoundedCornerShape(12.dp))
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Buscar",
                        tint = TextGray
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Buscar por nombre, partido o región...",
                        color = TextGray,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("Nombre", "Partido", "Región").forEach { filter ->
                    val isSelected = selectedFilter == filter
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(36.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .clickable { selectedFilter = filter },
                        contentAlignment = Alignment.Center
                    ) {
                        if (isSelected) {
                            Box(
                                modifier = Modifier
                                    .matchParentSize()
                                    .background(Brush.horizontalGradient(listOf(PurplePrimary, PurpleSecondary)), RoundedCornerShape(20.dp))
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .matchParentSize()
                                    .background(White, RoundedCornerShape(20.dp))
                            )
                        }

                        Text(
                            text = filter,
                            color = if (isSelected) White else Color(0xFF7E7E7E),
                            fontSize = 14.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                repeat(3) { index ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(White, RoundedCornerShape(12.dp))
                            .clickable { }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(48.dp)
                                        .clip(CircleShape)
                                        .background(PurpleSecondary)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text("Candidato ${'A' + index}", fontSize = 16.sp, color = Color.Black)
                                    Text("Partido XYZ", fontSize = 14.sp, color = TextGray)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    CandidateProgressBar(
                                        name = "Popularidad",
                                        progress = 0.6f + index * 0.1f
                                    )
                                }
                            }
                            Icon(
                                imageVector = Icons.Filled.KeyboardArrowRight,
                                contentDescription = "Ver detalle",
                                tint = Color(0xFF999999)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(80.dp))
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
        ) {
            BottomNavigationBar()
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 1150)
@Composable
fun SearchScreenPreview() {
    SearchScreen()
}
