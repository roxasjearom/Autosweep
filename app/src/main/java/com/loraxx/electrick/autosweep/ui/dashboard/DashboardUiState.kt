package com.loraxx.electrick.autosweep.ui.dashboard

import com.loraxx.electrick.autosweep.domain.model.AccountDetails
import com.loraxx.electrick.autosweep.domain.model.NewsItem
import com.loraxx.electrick.autosweep.domain.model.TrafficAdvisory

data class DashboardUiState(
    val accountDetailsList: List<AccountDetails> = emptyList(),
    val isLoading: Boolean = false,
    val trafficAdvisory: TrafficAdvisory = TrafficAdvisory(),
    val newsItems: List<NewsItem> = emptyList(),
)
