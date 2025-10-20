package com.proyecto.app_electoral.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.proyecto.app_electoral.ui.components.search.BottomNavigationBar
import com.proyecto.app_electoral.ui.components.search.CandidateListSection
import com.proyecto.app_electoral.ui.components.search.FilterChipsSection
import com.proyecto.app_electoral.ui.components.search.HeaderSection
import com.proyecto.app_electoral.ui.components.search.MostSearchedSection
import com.proyecto.app_electoral.ui.components.search.SearchBarSection
import com.proyecto.app_electoral.ui.components.search.StatsPanel

@Composable
fun SearchScreen(navController: NavHostController, onCandidateClick: (Int) -> Unit) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: "busqueda"

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
            item { StatsPanel() }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { SearchBarSection() }
            item { FilterChipsSection() }
            item { MostSearchedSection() }
            item { CandidateListSection(onCandidateClick) }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Vista de Búsqueda"
)
@Composable
fun PreviewSearchScreen() {
    // Crea un NavController temporal para previsualizar
    val navController = androidx.navigation.compose.rememberNavController()

    SearchScreen(
        navController = navController,
        onCandidateClick = {}
    )
}
