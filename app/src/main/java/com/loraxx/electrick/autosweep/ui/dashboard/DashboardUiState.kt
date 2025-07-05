package com.loraxx.electrick.autosweep.ui.dashboard

import com.loraxx.electrick.autosweep.domain.model.BalanceDetails

data class DashboardUiState(
    val balanceDetails: BalanceDetails = BalanceDetails(),
    val isLoading: Boolean = false,
)
