package com.proyecto.app_electoral.ui.components.comparison

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.proyecto.app_electoral.ui.screens.ComparisonTab
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
/**
 * Fila de pestañas (Tabs) de Experiencia, Propuestas y Popularidad.
 */
@Composable
fun ComparisonTabs(
    selectedTab: ComparisonTab,
    onTabSelected: (ComparisonTab) -> Unit
) {
    val tabs = ComparisonTab.entries.toList()

    TabRow(
        selectedTabIndex = tabs.indexOf(selectedTab),
        modifier = Modifier.padding(horizontal = 16.dp),
        containerColor = MaterialTheme.colorScheme.background,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier
                    .tabIndicatorOffset(tabPositions[tabs.indexOf(selectedTab)])
                    .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)),
                color = MaterialTheme.colorScheme.primary,
                height = 3.dp
            )
        }
    ) {
        tabs.forEach { tab ->
            val isSelected = tab == selectedTab
            Tab(
                selected = isSelected,
                onClick = { onTabSelected(tab) },
                selectedContentColor = MaterialTheme.colorScheme.primary,
                unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant
            ) {
                Text(
                    text = when (tab) {
                        ComparisonTab.EXPERIENCE -> "Experiencia"
                        ComparisonTab.PROPOSALS -> "Propuestas"
                        ComparisonTab.POPULARITY -> "Popularidad"
                    },
                    modifier = Modifier.padding(vertical = 10.dp),
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    fontSize = 14.sp
                )
            }
        }
    }
}