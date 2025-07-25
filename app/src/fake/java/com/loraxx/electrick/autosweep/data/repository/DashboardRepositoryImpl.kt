package com.loraxx.electrick.autosweep.data.repository

import com.loraxx.electrick.autosweep.domain.model.BalanceDetails
import com.loraxx.electrick.autosweep.domain.model.NewsItem
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

    override suspend fun getNews(): List<NewsItem> {
        delay(3500)
        return listOf(
            NewsItem(
                newsId = 1,
                title = "New DOTr Secretary suspends ‘No RFID, No entry’ on expressways",
                imageUrl = "https://picsum.photos/id/88/360/240",
            ),
            NewsItem(
                newsId = 2,
                title = "Why isn’t my RFID sticker being read properly",
                imageUrl = "https://picsum.photos/id/133/360/240",
            ),
            NewsItem(
                newsId = 3,
                title = "Incoming toll increase by the end of year",
                imageUrl = "https://picsum.photos/id/182/360/240",
            ),
        )
    }
}
