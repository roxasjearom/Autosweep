package com.loraxx.electrick.autosweep.data.repository

import com.loraxx.electrick.autosweep.data.repository.FakeData.fakeTollAccountDetails
import com.loraxx.electrick.autosweep.domain.model.BalanceResult
import com.loraxx.electrick.autosweep.domain.repository.BalanceRepository
import javax.inject.Inject

class BalanceRepositoryImpl @Inject constructor(): BalanceRepository {

    override suspend fun checkBalance(accountNumber: String): BalanceResult {
        return when {
            fakeTollAccountDetails.none { it.accountNumber == accountNumber } -> BalanceResult.AccountNumberNotFound
            else -> BalanceResult.Success(fakeTollAccountDetails.first { it.accountNumber == accountNumber }.balance)
        }
    }
}
