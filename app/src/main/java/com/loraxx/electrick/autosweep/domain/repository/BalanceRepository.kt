package com.loraxx.electrick.autosweep.domain.repository

import com.loraxx.electrick.autosweep.domain.model.BalanceResult

interface BalanceRepository {
    suspend fun checkBalance(accountNumber: String): BalanceResult
}
