package com.proyecto.app_electoral.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.proyecto.app_electoral.ui.components.BottomNavigationBar
import com.proyecto.app_electoral.ui.components.compare.*
import com.proyecto.app_electoral.ui.theme.Background

@Composable
fun CompareScreen(
    onSelectCandidateClick: () -> Unit
) {
    var selectedTab by remember { mutableStateOf(ComparisonTab.Experience) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        ComparisonHeader(
            onBackClick = { /* placeholder, si quieres puedes manejar el "atrás" */ },
            onSelectCandidateLeft = onSelectCandidateClick,
            onSelectCandidateRight = onSelectCandidateClick
        )

        ComparisonTabs(selectedTab = selectedTab, onTabSelected = { selectedTab = it })

        CandidateComparisonContent(selectedTab = selectedTab)

        Spacer(modifier = Modifier.weight(1f))

        BottomNavigationBar()
    }
}

// ==================== Preview ====================

@Preview(showBackground = true)
@Composable
fun CompareScreenPreview() {
        CompareScreen(
            onSelectCandidateClick = { /* placeholder */ }
        )
    }
