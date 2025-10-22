package com.proyecto.app_electoral.ui.components.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.proyecto.app_electoral.ui.viewmodel.CandidatosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterChipsSection(
    viewModel: CandidatosViewModel
) {
    val filters = listOf("Todos", "Con denuncias", "Por región", "Por partido")

    val selectedFilter by viewModel.selectedFilter.collectAsState()

    LazyRow(
        modifier = Modifier.padding(vertical = 8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(filters) { filter ->
            FilterChip(
                selected = filter == selectedFilter,
                onClick = {
                    viewModel.onFilterChange(filter)
                },
                label = { Text(filter) },
                leadingIcon = if (filter == selectedFilter) {
                    {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Selected"
                        )
                    }
                } else null
            )
        }
    }
}