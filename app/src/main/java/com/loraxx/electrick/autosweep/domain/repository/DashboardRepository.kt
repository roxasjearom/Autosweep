package com.loraxx.electrick.autosweep.domain.repository

import com.loraxx.electrick.autosweep.domain.model.BalanceDetails

interface DashboardRepository {
    suspend fun getBalanceDetails(accountNumber: String): BalanceDetails
}
