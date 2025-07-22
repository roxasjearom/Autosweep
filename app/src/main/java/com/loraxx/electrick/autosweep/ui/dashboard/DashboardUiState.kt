package com.loraxx.electrick.autosweep.ui.dashboard

import com.loraxx.electrick.autosweep.domain.model.BalanceDetails
import com.loraxx.electrick.autosweep.domain.model.TrafficAdvisory

data class DashboardUiState(
    val balanceDetails: BalanceDetails = BalanceDetails(),
    val isLoading: Boolean = false,
    val trafficAdvisory: TrafficAdvisory = TrafficAdvisory(),
)
