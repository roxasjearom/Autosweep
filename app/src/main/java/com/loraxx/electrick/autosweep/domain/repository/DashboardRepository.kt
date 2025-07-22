package com.loraxx.electrick.autosweep.domain.repository

import com.loraxx.electrick.autosweep.domain.model.BalanceDetails
import com.loraxx.electrick.autosweep.domain.model.TrafficAdvisory

interface DashboardRepository {
    suspend fun getBalanceDetails(accountNumber: String): BalanceDetails

    suspend fun getTrafficAdvisory(): TrafficAdvisory
}
