package com.proyecto.app_electoral.ui.components.compare

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ComparisonTabs(
    selectedTab: ComparisonTab,
    onTabSelected: (ComparisonTab) -> Unit
) {
    TabRow(
        selectedTabIndex = selectedTab.ordinal,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        ComparisonTab.values().forEach { tab ->
            Tab(
                selected = tab == selectedTab,
                onClick = { onTabSelected(tab) },
                text = { Text(text = tab.title) }
            )
        }
    }
}

enum class ComparisonTab(val title: String) {
    Experience("Experiencia"),
    Proposals("Propuestas"),
    Popularity("Popularidad")
}
