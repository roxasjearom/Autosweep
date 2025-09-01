package com.loraxx.electrick.autosweep.domain.repository

import com.loraxx.electrick.autosweep.domain.model.AccountDetails
import com.loraxx.electrick.autosweep.domain.model.NewsItem
import com.loraxx.electrick.autosweep.domain.model.TrafficAdvisory

interface DashboardRepository {
    suspend fun getAccountDetails(): AccountDetails

    suspend fun geAccountDetailsList(): List<AccountDetails>

    suspend fun getTrafficAdvisory(): TrafficAdvisory

    suspend fun getNews(): List<NewsItem>
}
