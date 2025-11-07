package com.proyecto.app_electoral.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.proyecto.app_electoral.di.Injector
import com.proyecto.app_electoral.ui.components.compare.CandidateComparisonContent
import com.proyecto.app_electoral.ui.components.compare.CandidateSelectionDialog
import com.proyecto.app_electoral.ui.components.compare.ComparisonHeader
import com.proyecto.app_electoral.ui.components.compare.ComparisonTab
import com.proyecto.app_electoral.ui.components.compare.ComparisonTabs
import com.proyecto.app_electoral.ui.theme.BackgroundCustom
import com.proyecto.app_electoral.ui.viewmodel.CompareSlot
import com.proyecto.app_electoral.ui.viewmodel.CompareViewModel
import com.proyecto.app_electoral.ui.viewmodel.CompareViewModelFactory

@Composable
fun CompareScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: CompareViewModel = viewModel(factory = CompareViewModelFactory(Injector.provideCandidatoRepository()))
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedTab by remember { mutableStateOf(ComparisonTab.Experience) }
    var activeDialogSlot by remember { mutableStateOf<CompareSlot?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundCustom)
    ) {
        ComparisonHeader(
            leftProfile = uiState.leftProfile,
            rightProfile = uiState.rightProfile,
            isLeftLoading = uiState.slotLoading == CompareSlot.LEFT,
            isRightLoading = uiState.slotLoading == CompareSlot.RIGHT,
            onBackClick = { navController.popBackStack() },
            onSelectCandidateLeft = {
                if (!uiState.isLoadingList) activeDialogSlot = CompareSlot.LEFT
            },
            onSelectCandidateRight = {
                if (!uiState.isLoadingList) activeDialogSlot = CompareSlot.RIGHT
            }
        )

        if (uiState.isLoadingList) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                color = MaterialTheme.colorScheme.primary
            )
        } else {
            Spacer(modifier = Modifier.size(8.dp))
        }

        uiState.error?.let { error ->
            AssistChip(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                onClick = { viewModel.dismissError() },
                label = { Text(text = error) },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp),
                    labelColor = MaterialTheme.colorScheme.error
                )
            )
        }

        ComparisonTabs(
            selectedTab = selectedTab,
            onTabSelected = { selectedTab = it }
        )

        CandidateComparisonContent(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            selectedTab = selectedTab,
            leftProfile = uiState.leftProfile,
            rightProfile = uiState.rightProfile
        )
    }

    val slot = activeDialogSlot
    if (slot != null) {
        CandidateSelectionDialog(
            candidatos = uiState.candidates,
            onDismiss = { activeDialogSlot = null },
            onSelect = { candidate ->
                viewModel.selectCandidate(slot, candidate)
                activeDialogSlot = null
            }
        )
    }
}
