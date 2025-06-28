package com.loraxx.electrick.autosweep.data.repository

import com.loraxx.electrick.autosweep.domain.model.BalanceResult
import com.loraxx.electrick.autosweep.domain.repository.BalanceRepository
import javax.inject.Inject

class BalanceRepositoryImpl @Inject constructor() : BalanceRepository {
    //TODO Update this once we have a valid API
    override suspend fun checkBalance(accountNumber: String): BalanceResult {
        TODO("Not yet implemented")
    }
}
