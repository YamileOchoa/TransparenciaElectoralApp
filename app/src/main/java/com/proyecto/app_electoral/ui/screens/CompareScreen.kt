package com.proyecto.app_electoral.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.proyecto.app_electoral.ui.components.compare.CandidateSelectionSection
import com.proyecto.app_electoral.ui.components.compare.ComparisonSection
import com.proyecto.app_electoral.ui.components.compare.DenunciasSection
import com.proyecto.app_electoral.ui.components.BottomNavigationBar
import com.proyecto.app_electoral.ui.components.search.HeaderSection

@Composable
fun CompareScreen(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: "comparar"

    Scaffold(
        bottomBar = { BottomNavigationBar(navController, currentRoute) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxWidth()
                .background(Color(0xFFF5F6FA)),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            item { HeaderSection() }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { CandidateSelectionSection() }
            item { Spacer(modifier = Modifier.height(24.dp)) }
            item {
                ComparisonSection(
                    title = "📋 Propuestas",
                    candidate1Items = listOf(
                        "Primera propuesta",
                        "Segunda propuesta",
                        "Tercera propuesta"
                    ),
                    candidate1Button = "Ver todas (12)",
                    hasCandidate2 = false
                )
            }
            item { Spacer(modifier = Modifier.height(24.dp)) }
            item {
                ComparisonSection(
                    title = "🏗️ Proyectos",
                    candidate1Items = listOf(
                        "Primer proyecto",
                        "Segundo proyecto",
                        "Tercer proyecto"
                    ),
                    candidate1Button = "Ver todos (8)",
                    hasCandidate2 = false
                )
            }
            item { Spacer(modifier = Modifier.height(24.dp)) }
            item { DenunciasSection() }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Vista de Comparación")
@Composable
fun PreviewCompareScreen() {
    val navController = rememberNavController()
    CompareScreen(navController = navController)
}
