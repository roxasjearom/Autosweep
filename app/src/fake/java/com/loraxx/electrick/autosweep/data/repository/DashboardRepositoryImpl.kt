package com.loraxx.electrick.autosweep.data.repository

import com.loraxx.electrick.autosweep.domain.model.BalanceDetails
import com.loraxx.electrick.autosweep.domain.model.TrafficAdvisory
import com.loraxx.electrick.autosweep.domain.repository.DashboardRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class DashboardRepositoryImpl @Inject constructor() : DashboardRepository {
    override suspend fun getBalanceDetails(accountNumber: String): BalanceDetails {
        delay(3000)
        return BalanceDetails(
            plateNumber = "JCR 0623",
            accountNumber = "123456789",
            accountBalance = 1200.0,
        )
    }

    override suspend fun getTrafficAdvisory(): TrafficAdvisory {
        delay(1000)
        return TrafficAdvisory(
            hasAdvisory = true,
            advisoryMessage = "TRAFFIC ADVISORY: Expect delays on TPLEX due to an ongoing incident. Drive safe!",
        )
    }
}
